<template>
  <div class="console">
    <div class="row row-equal">
      <div class="flex xs12">
        <CapabilityTable :capabilities="capabilities" @send-spec="sendSpecification" />
      </div>
    </div>

    <div class="row row-equal">
      <div class="flex xs12">
        <ReceiptTable :receipts="receiptsArray" @stop-spec="sendInterrupt" @stream-live="streamLive" />
      </div>
    </div>

    <RealtimeResults :receipt="liveSpec"/>

  </div>
</template>

<script>
import CapabilityTable from './CapabilityTable'
import ReceiptTable from './ReceiptTable'
import RealtimeResults from './RealtimeResults'

export default {
  name: 'data-streaming',
  components: {
    CapabilityTable,
    ReceiptTable,
    RealtimeResults,
  },
  data () {
    return {
      capabilities: [],
      receiptsMap: new Map(),
      receiptsArray: [],
      liveSpec: {},
    }
  },
  created () {
  },
  methods: {
    async getCapabilities () {
      const ebMsg = {
        action: 'get_capabilities',
        params: {},
      }
      const context = this
      await context.$eventBus.send('nms.dss', ebMsg, {}, function (err, reply) {
        if (err) {
          console.error('Failed to get capabilities', err)
          return
        }
        const response = reply.body
        if (response.error) {
          console.log('failed to get capabilities: ', response.error)
          return
        }
        const caps = response.content.docs
        console.log(caps)
        for (var i = 0, len = caps.length; i < len; i++) {
          let lStatus = 'inactive'
          if (context.receiptsMap.has(caps[i].schema)) {
            lStatus = 'active'
          }
          const cap = { data: caps[i], status: lStatus }
          context.capabilities.push(cap)
        }
      })
    },
    async getReceipts () {
      const ebMsg = {
        action: 'get_receipts',
        params: {},
      }
      const context = this
      await context.$eventBus.send('nms.dss', ebMsg, {}, function (err, reply) {
        if (err) {
          console.error('Failed to get receipts', err)
          return
        }
        const response = reply.body
        if (response.error) {
          console.log('failed to get receipts: ', response.error)
          return
        }
        const rcts = response.content.docs
        for (var i = 0, len = rcts.length; i < len; i++) {
          context.receiptsMap.set(rcts[i].schema, rcts[i])
        }
        context.receiptsArray = rcts
      })
    },
    updateReceipts () {
      this.receiptsArray = []
      var values = this.receiptsMap.values()
      for (var ele of values) {
        this.receiptsArray.push(ele)
      }
    },
    sendSpecification (cap) {
      if (this.receiptsMap.has(cap.schema)) {
        console.log('spec is active')
        return
      }

      const ebMsg = {
        action: 'send_specification',
        params: { capability: cap },
      }
      const context = this
      this.$eventBus.send('nms.dss', ebMsg, {}, function (err, reply) {
        if (err) {
          console.error('Failed to send specification', err)
          return
        }
        const repBody = reply.body
        if (repBody.error) {
          console.log('Failed to send specification: ', repBody.error)
          context.showToast('Failed: cannot get Receipt', {
            icon: 'fa-check',
            position: 'bottom-right',
            duration: 3000,
          })
          return
        }

        const receipt = repBody.content.receipt
        if (receipt.errors.length === 0) {
          context.receiptsMap.set(receipt.schema, receipt)
          context.updateReceipts()
          for (var i = 0, len = context.capabilities.length; i < len; i++) {
            if (context.capabilities[i].data.schema === receipt.schema) {
              context.capabilities[i].status = 'active'
              break
            }
          }
        } else {
          context.showToast('Error: ' + receipt.errors[0], {
            icon: 'fa-check',
            position: 'bottom-right',
            duration: 1500,
          })
        }
      })
    },
    sendInterrupt (schema) {
      const ebMsg = {
        action: 'send_interrupt',
        params: { schema: schema },
      }
      const context = this
      this.$eventBus.send('nms.dss', ebMsg, {}, function (err, reply) {
        if (err) {
          console.error('Failed to send interrupt', err)
          return
        }
        const repBody = reply.body
        if (repBody.error) {
          console.log('error in interrupt processing ', repBody.error)
          context.showToast('error in interrupt processing', {
            icon: 'fa-check',
            position: 'bottom-right',
            duration: 3000,
          })
          return
        }

        const schema = repBody.content.schema
        context.receiptsMap.delete(schema)
        context.updateReceipts()
        for (var i = 0, len = context.capabilities.length; i < len; i++) {
          if (context.capabilities[i].data.schema === schema) {
            context.capabilities[i].status = 'inactive'
            break
          }
        }
        context.showToast('Specification stopped.', {
          icon: 'fa-check',
          position: 'bottom-right',
          duration: 3000,
        })
      })
    },
    streamLive (rct) {
      this.liveSpec = rct
    },
  },
  eventbus: {
    lifecycleHooks: {
      async created (context, eventbus) {
        await context.getReceipts()
        await context.getCapabilities()
      },
    },
  },
}
</script>

<style lang="scss">
  .row-equal .flex {
    .va-card {
      height: 100%;
    }
  }

  .console {
    .va-card {
      margin-bottom: 0 !important;
    }
  }
</style>
