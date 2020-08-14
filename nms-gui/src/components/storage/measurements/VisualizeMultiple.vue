<template>
  <div>
    <div  v-for="(graph, index) in graphs" :key="index" class="chart-row row">
      <p class="display-4">
        [{{ operationInfo[index].name }}] data from [{{operationInfo[index].agent}}]
      </p>
      <va-button small color="danger" v-on:click="remove(index)" >
        Remove
      </va-button>
      <va-chart :data="graph" type="line" class="chartt"/>
    </div>
  </div>
</template>

<script>

export default {
  name: 'visualizeMultiple',
  components: {
  },
  data () {
    return {
      graphs: [],
      operationInfo: [],
      colors: ['primary', 'secondary', 'danger', 'warning', 'dark'],
    }
  },
  computed: {
  },
  created () {
  },
  mounted () {
  },
  methods: {
    addResult (nResult) {
      const operationData = []
      const operationLabels = []
      let plottable = false

      for (var i = 0; i < nResult.length; i++) {
        operationLabels.push(nResult[i].timestamp.split('.')[0])
      }
      for (i = 0; i < nResult[0].results.length; i++) {
        const resultLabel = (nResult[0].results[i]).split('.')
        if (resultLabel[1]) {
          plottable = true
          const oneResultData = []
          const oneResultLabel = resultLabel[0] + ' ' + this.getUnitLabel(resultLabel[1])
          for (var j = 0; j < nResult.length; j++) {
            oneResultData.push(Number(nResult[j].resultValues[0][i]))
          }
          const oneResultPlot = {
            label: oneResultLabel,
            backgroundColor: 'transparent',
            borderColor: this.colors[i],
            data: oneResultData,
          }
          operationData.push(oneResultPlot)
        }
      }
      if (plottable) {
        const operationGraph = {
          labels: operationLabels,
          datasets: operationData,
        }
        this.operationInfo.push({ id: nResult[0].schema, name: nResult[0].name, agent: nResult[0].agentId })
        this.graphs.push(operationGraph)
      }
    },
    removeResult (id) {
      let index = -1
      for (var i = 0; i < this.operationInfo.length; i++) {
        if (this.operationInfo[i].id === id) {
          index = i
          break
        }
      }
      if (i >= 0) {
        this.graphs.splice(index, 1)
        this.operationInfo.splice(index, 1)
      }
    },
    remove (index) {
      this.graphs.splice(index, 1)
      this.operationInfo.splice(index, 1)
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
}
</script>

<style lang="scss">
.chartt {
  padding: 5px;
}

.chart-row {
  height: 400px;
  padding: 10px;
}

.grid {
  &__container {
    min-height: 3rem;
    color: $white;
    border-radius: 0.5rem;
  }
}
</style>
