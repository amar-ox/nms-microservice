
<template>
  <va-modal
    v-model="showModal"
    size="large"
    :title="title"
    @cancel="close"
    @ok="close"
  >
    <div>
      <p> Measurement infomation </p>
      <va-badge v-if="receipt.schema" color="info">
        Id:
        {{
          receipt.schema
        }}
      </va-badge>
      <va-badge v-if="receipt.agentId" color="warning">
        Agent:
        {{
          receipt.agentId
        }}
      </va-badge>
      <va-badge v-if="receipt.name" color="danger">
        Type:
        {{
          receipt.name
        }}
      </va-badge>

      <va-badge v-if="receipt.timestamp" color="dark">
        Started at:
        {{
          receipt.timestamp
        }}
      </va-badge>

      <va-separator/>

      <p> Available results </p>
      <va-button v-for="(res, index) in receipt.results" :key="index"
        small
        @click="setTrackedResult(index)"
        color="gray"
        class="mr-0"
      >
        {{ res.split(".")[0] }}
      </va-button>

      <va-separator/>
      <va-chart v-if="chart == 1" class="chartt" :data="chartData" type="line"/>
      <va-badge v-if="chart == 2" color="info">
        {{ label }}
      </va-badge>
    </div>
  </va-modal>
</template>

<script>
export default {
  name: 'results',
  props: ['receipt'],
  components: {
  },
  data () {
    return {
      showModal: false,
      chart: 0,
      labels: [],
      label: '',
      data: [],
      title: 'Live stream results',
      trackedResultIndex: 0,
    }
  },
  computed: {
    chartData: function () {
      return {
        labels: this.labels,
        datasets: [
          {
            label: this.label,
            backgroundColor: 'transparent',
            borderColor: 'success',
            data: this.data,
          },
        ],
      }
    },
  },
  created () {
  },
  watch: {
    receipt: {
      handler: function () {
        this.chart = 0
        this.trackedResultIndex = 0
        this.labels = []
        this.data = []
        this.showModal = true
      },
      deep: true,
    },
  },
  methods: {
    setTrackedResult (index) {
      this.chart = 0
      this.labels = []
      this.data = []
      this.trackedResultIndex = index
    },
    close () {
      this.chart = 0
      this.trackedResultIndex = 0
      this.labels = []
      this.data = []
      this.showModal = false
    },
    getUnitLabel (unit) {
      switch (unit) {
        case 'n':
          return '(#)'
        case 'pc':
          return '(%)'
        case 'b':
          return '(Bytes)'
        default:
          return ''
      }
    },
  },
  eventbus: {
    lifecycleHooks: {
      created (context, eventbus) {
        eventbus.registerHandler('nms.info.dss', function (err, msg) {
          if (err) {
            console.log('VertxEventBus error: ', err)
            return
          }
          const infoMsg = msg.body
          if (infoMsg.service === 'nms.dss') {
            const result = infoMsg.content
            if (result.schema === context.receipt.schema) {
              const resultLabel = (result.results[context.trackedResultIndex]).split('.')

              if (resultLabel[1]) {
                context.chart = 1
                const label = resultLabel[0]
                const unit = resultLabel[1]
                context.labels.push(result.timestamp.split('.')[0])
                context.data.push(Number(result.resultValues[0][context.trackedResultIndex]))
                context.label = label + ' ' + context.getUnitLabel(unit)
              } else {
                context.chart = 2
                context.label = resultLabel[0] + ': ' + result.resultValues[0][context.trackedResultIndex]
              }
            }
          }
        })
      },
    },
  },
}
</script>
<style scoped lang="scss">
.chartt {
  height: 300px;
}

.grid {
  &__container {
    min-height: 3rem;
    color: $white;
    border-radius: 0.5rem;
  }
}

</style>
