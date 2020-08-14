<template>
  <div class="measurements">

    <div class="row row-equal">
      <div class="flex xs12">
        <va-button small color="warning" v-on:click="refresh()">
          Refresh
        </va-button>

        <va-button-toggle
          outline
          v-model="multiple"
          :options="modeOptions"
          color="info"
        />
      </div>
    </div>

    <div class="row row-equal">
      <div class="flex xs12">
        <MeasurementsTable :measurements="measurements" @expand-msr="expandMsr"  @delete-msr="deleteMsr" />
      </div>
    </div>

    <va-separator/>

    <div class="row row-equal">
      <div class="flex xs12">
        <ResultsTable v-if="multiple === 0" :rawResults="rawResults" @expand-res="expandRes" @delete-res="deleteRes" />
      </div>
    </div>

    <div class="row row-equal">
      <div class="flex xs12">
        <VisualizeMultiple v-if="multiple === 1" ref="visMultiple" />
      </div>
    </div>

    <va-modal
      v-model="showResultDetails"
      size="large"
      :title="resultDetailsTitle"
    >
      <pre>
    {{ resultDetails }}
  </pre>
    </va-modal>
  </div>
</template>

<script>
import MeasurementsTable from './MeasurementsTable'
import ResultsTable from './ResultsTable'
import VisualizeMultiple from './VisualizeMultiple'
// import { EventBus } from '../../../app/main'

export default {
  name: 'measurements',
  components: {
    MeasurementsTable,
    ResultsTable,
    VisualizeMultiple,
  },
  data () {
    return {
      title: 'Stored measurements',
      measurements: [],
      rawResults: [],
      lastMeasurement: '',

      modeOptions: [
        { label: 'Single', value: 0 },
        { label: 'Multiple', value: 1 },
      ],
      multiple: 0,

      resultDetails: {},
      resultDetailsTitle: 'Result details',
      showResultDetails: false,
    }
  },
  computed: {
  },
  created () {
  },
  mounted () {
  },
  methods: {
    // handle measurements grouped by operation type
    refresh () {
      this.measurements = []
      this.rawResults = []
      this.resultDetails = {}
      this.showResultDetails = false
      this.getAllMeasurements()
      if (this.lastMeasurement !== '') {
        this.getResultsByOperation(this.lastMeasurement)
      }
    },
    getAllMeasurements () {
      this.measurements = []
      const ebMsg = {
        action: 'get_all_opt',
        params: { result_type: 'measure' },
      }
      const context = this
      this.$eventBus.send('nms.storage', ebMsg, {}, function (err, reply) {
        if (err) {
          console.error('Failed to get measurements', err)
          return
        }
        const repBody = reply.body
        if (repBody.error) {
          console.log('failed to get measurements: ', repBody.error)
          return
        }
        const jsonResults = repBody.content.docs
        for (var i = 0, len = jsonResults.length; i < len; i++) {
          context.measurements.push(jsonResults[i])
        }
      })
    },
    deleteMsr (id) {
      const ebMsg = {
        action: 'del_res_by_opt',
        params: { operation_id: id },
      }
      const context = this
      this.$eventBus.send('nms.storage', ebMsg, {}, function (err, reply) {
        if (err) {
          console.error('Failed to delete measurement page', err)
          return
        }
        const repBody = reply.body
        if (repBody.error) {
          console.log('failed to delete measurement: ', repBody.error)
          // return
        }
        context.refresh()
        context.showToast('Measurement deleted: ' + id, {
          icon: 'fa-check',
          position: 'bottom-right',
          duration: 1500,
        })
      })
    },
    expandMsr (id) {
      this.lastMeasurement = id
      this.getResultsByOperation(id)
    },

    getResultsByOperation (id) {
      const context = this
      const ebMsg = {
        action: 'get_res_by_opt',
        params: { operation_id: id },
      }
      this.$eventBus.send('nms.storage', ebMsg, {}, function (err, reply) {
        if (err) {
          console.log('Error in getting results')
          return
        }
        const repBody = reply.body
        if (repBody.error) {
          console.log('Failed to get results: ', repBody.error)
          return
        }

        if (context.multiple === 0) {
          context.rawResults = repBody.content.docs
        } else {
          context.$refs.visMultiple.addResult(repBody.content.docs)
        }
      })
    },

    // handle measurements for selected operation
    expandRes (id) {
      const ebMsg = {
        action: 'get_result',
        params: { _id: id },
      }
      const context = this
      this.$eventBus.send('nms.storage', ebMsg, {}, function (err, reply) {
        if (err) {
          console.error('Failed to get result details', err)
          return
        }
        const repBody = reply.body
        if (repBody.error) {
          console.log('failed to get result details: ', repBody.error)
          return
        }
        const result = repBody.content
        context.resultDetails = JSON.stringify(result, null, 4)
        context.showResultDetails = true
      })
    },
    deleteRes (id) {
      const ebMsg = {
        action: 'del_result',
        params: { _id: id },
      }
      const context = this
      this.$eventBus.send('nms.storage', ebMsg, {}, function (err, reply) {
        if (err) {
          console.error('Failed to save page', err)
          return
        }
        const repBody = reply.body
        if (repBody.error) {
          console.log('failed to delete result: ', repBody.error)
          return
        }
        context.refresh()
        context.showToast('Result deleted', {
          icon: 'fa-check',
          position: 'bottom-right',
          duration: 1500,
        })
      })
    },
  },
  eventbus: {
    lifecycleHooks: {
      created (context, eventbus) {
        context.getAllMeasurements()
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

  .measurements {
    .va-card {
      margin-bottom: 0 !important;
    }
  }
</style>
