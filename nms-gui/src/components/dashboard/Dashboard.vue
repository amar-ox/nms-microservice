<template>
  <div class="dashboard">
    <div class="row">
      <div class="flex xs12 md4 xl4">
        <dashboard-service-list :data="serviceInfo"/>
      </div>
      <div class="flex xs12 md8 xl8 logging">
        <dashboard-service-logs :data="serviceLogging"/>
      </div>
    </div>
  </div>
</template>

<script>
// import DashboardInfoBlock from './DashboardInfoBlock'
// import DashboardCharts from './DashboardCharts'
import DashboardServiceList from './DashboardServiceList'
import DashboardServiceLogs from './DashboardServiceLogs'

export default {
  name: 'dashboard',
  components: {
    DashboardServiceList,
    DashboardServiceLogs,
  },
  data: function () {
    return {
      serviceInfo: {
        dss: {
          name: 'Data Streaming',
          status: 'stopped',
          activity: 0,
        },
        rest: {
          name: 'REST API',
          status: 'stopped',
          activity: 0,
        },
        storage: {
          name: 'Storage',
          status: 'stopped',
          activity: 0,
        },
        topology: {
          name: 'Topology',
          status: 'stopped',
          activity: 0,
        },
        routing: {
          name: 'Routing',
          status: 'stopped',
          activity: 0,
        },
      },
      serviceLogging: [],
    }
  },
  methods: {
    getServiceInfo (name) {
      const msgGetInfo = {
        action: 'get_service_info',
        params: {},
      }
      const context = this
      context.$eventBus.send('nms.' + name, msgGetInfo, {}, function (err, reply) {
        if (err) {
          console.log('Error in sending message', err)
        } else {
          const repBody = reply.body
          if (repBody.content) {
            context.serviceInfo[name].status = repBody.content.status
            context.serviceInfo[name].activity = repBody.content.messages
          }
        }
      })
    },
  },
  eventbus: {
    lifecycleHooks: {
      created (context, eventbus) {
        context.getServiceInfo('dss')
        context.getServiceInfo('rest')
        context.getServiceInfo('storage')
        context.getServiceInfo('topology')
        context.getServiceInfo('routing')

        /* eventbus.registerHandler('nms.status', function (err, msg) {
          if (err) {
            console.log('VertxEventBus error: ', err)
            return
          }
          const content = msg.body.content
          // ...
        }) */

        eventbus.registerHandler('nms.logging', function (err, msg) {
          if (err) {
            console.log('VertxEventBus error: ', err)
            return
          }
          const body = msg.body
          const logLine = '[' + body.content.timestamp + '] [' + body.service + '] "' + body.content.message + '"'
          if (context.serviceLogging.length === 20) {
            context.serviceLogging.shift()
          }
          context.serviceLogging.push(logLine)
        })
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

  .dashboard {
    .va-card {
      margin-bottom: 0 !important;
    }
  }

  .logging {
    height: 600px;
    max-height: 600px;
  }
</style>
