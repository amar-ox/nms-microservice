<template>
  <div v-if="rawResults.length">
    <va-button-toggle
      outline
      v-model="model"
      :options="options"
      color="info"
    />

    <table v-if="model == 'table'" class="va-table va-table--striped va-table--hoverable">
      <thead>
        <tr>
          <th v-for="(header, index) in table.resultColumns" :key="index">{{ header }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(values, index) in table.resultValues" :key="index">
          <td v-for="(rowValue, index) in Object.values(values).slice(1)" :key="index">
            {{ rowValue }}
          </td>
          <td>
            <va-button small color="info" v-on:click="detailRes(index)" >
              Details
            </va-button>
            <va-button small color="danger" v-on:click="deleteRes(index)" >
              Delete
            </va-button>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="chartt">
      <va-chart v-if="model == 'graph'" :data="chartData" type="line"/>
    </div>
  </div>
</template>

<script>

export default {
  name: 'resultsTable',
  props: ['rawResults'],
  components: {
  },
  data () {
    return {
      options: [
        { label: 'Table', value: 'table' },
        { label: 'Graph', value: 'graph' },
      ],
      model: 'table',

      colors: ['primary', 'secondary', 'danger', 'warning', 'dark'],

      table: {
        resultColumns: [],
        resultValues: [],
      },
      labels: [],
      label: '',
      data: [],
    }
  },
  computed: {
    chartData: function () {
      return {
        labels: this.labels,
        datasets: this.data,
      }
    },
  },
  created () {
  },
  mounted () {
  },
  watch: {
    rawResults: {
      handler: function () {
        if (this.rawResults.length) {
          this.processResults()
        } else {
          this.resetData()
        }
      },
      deep: true,
    },
  },
  methods: {
    detailRes (index) {
      const id = this.table.resultValues[index][0]
      this.$emit('expand-res', id)
    },
    deleteRes (index) {
      const id = this.table.resultValues[index][0]
      this.$emit('delete-res', id)
    },
    processResults () {
      // process table
      const values = []
      for (var i = 0; i < this.rawResults.length; i++) {
        let row = [this.rawResults[i]._id, this.rawResults[i].timestamp]
        row = row.concat(this.rawResults[i].resultValues[0])
        values.push(row)
      }
      let results = ['timestamp']
      results = results.concat(this.rawResults[0].results)
      results = results.concat(['Action'])
      this.table.resultColumns = results
      this.table.resultValues = values

      // process graph
      this.data = []
      this.labels = []
      for (i = 0; i < this.rawResults.length; i++) {
        this.labels.push(this.rawResults[i].timestamp.split('.')[0])
      }
      for (i = 0; i < this.rawResults[0].results.length; i++) {
        const resultLabel = (this.rawResults[0].results[i]).split('.')
        if (resultLabel[1]) {
          const oneResultData = []
          const oneResultLabel = resultLabel[0] + ' ' + this.getUnitLabel(resultLabel[1])
          for (var j = 0; j < this.rawResults.length; j++) {
            oneResultData.push(Number(this.rawResults[j].resultValues[0][i]))
          }
          const oneResultPlot = {
            label: oneResultLabel,
            backgroundColor: 'transparent',
            borderColor: this.colors[i],
            data: oneResultData,
          }
          this.data.push(oneResultPlot)
        }
      }
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
    resetData () {
      this.table.resultColumns = []
      this.table.resultValues = []
      this.labels = []
      this.label = ''
      this.data = []
    },
  },
}
</script>

<style lang="scss">
.chartt {
  height: 500px;
}

.grid {
  &__container {
    min-height: 3rem;
    color: $white;
    border-radius: 0.5rem;
  }
}
</style>
