<template>
  <div>
    <va-card :title="title">
      <table class="va-table va-table--striped va-table--hoverable">
        <thead>
          <tr>
            <th v-for="(col, index) in headers" :key="index">{{ col }}</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(capability, index) in capabilities" :key="index">
            <td>
              <va-badge :color="getTypeColor(capability.data.capability)">
                {{ capability.data.capability }}
              </va-badge>
              <va-badge color="primary">
                {{ capability.data.name }}
              </va-badge>
            </td>
            <td>
              <p>
                {{ capability.data.endpoint+'/'+capability.data.schema.slice(0,10) }}
              </p>

            </td>

            <td> {{ capability.data.label }}
              <br/>
              <va-badge v-for="(res, index) in capability.data.results" :key="index" color="gray">
                {{ res.split(".")[0] }}
              </va-badge>
            </td>

            <td>
              <va-badge :color="getStatusColor(capability.status)">
                {{capability.status}}
              </va-badge>
            </td>

            <td>
              <va-button small color="info" v-on:click="createSpecification(capability.data, index)" >
                Send specification
              </va-button>
            </td>

          </tr>
        </tbody>
      </table>
    </va-card>

    <va-modal
      v-model="showModal"
      size="large"
      :title="modalTitle"
      @cancel="cancel"
      @ok="ok"
    >
      <div class="row">
        <div class="flex md6 sm12 xs12">
          <label class="label">Temporal scope</label>
          <va-input
            :label="$t('forms.selects.multi')"
            v-model="specification.when.period"
          />

          <va-date-picker
            :label="$t('forms.selects.multi')"
            :config="{mode: 'range', inline: true, enableTime: true}"
            v-model="specification.when.range"
          />
        </div>

        <div class="flex md6 sm12 xs12">
          <div class="row">
            <div class="flex md12 sm12 xs12">
              <label class="label">Results to request</label>
              <va-select
                :label="$t('forms.selects.multi')"
                v-model="specification.selectedResults"
                textBy="description"
                multiple
                :options="selectedCapability.results"
              />
            </div>
          </div>
          <div class="flex md12 sm12 xs12">
            <label class="label">Specification parameters</label>
            <div v-for="(vParam, nParam) in selectedCapability.parameters" :key="nParam">
              <va-input
                :label="nParam"
                v-model="specification.parameters[nParam]"
              />
            </div>
          </div>
        </div>
      </div>
    </va-modal>

  </div>
</template>

<script>

export default {
  name: 'capability',
  props: ['capabilities'],
  components: {
  },
  data () {
    return {
      headers: ['Capability', 'Name', 'Available results', 'Status', 'Action'],
      title: 'Available capabilities',

      showModal: false,
      modalTitle: '',
      selectedCapability: {},
      specification: {
        selectedResults: [],
        parameters: {},
        when: {
          range: '',
          period: 10000,
        },
      },
    }
  },
  created () {
  },
  methods: {
    createSpecification (cap, index) {
      this.selectedCapability = cap
      this.modalTitle = 'Create ' + cap.name + ' specification'
      this.specification.parameters = {}
      this.specification.selectedResults = []
      const now = new Date()
      const date = now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate()
      const time = now.getHours() + ':' + now.getMinutes()
      const nextTime = (now.getHours() + 1) + ':' + now.getMinutes()
      const startTime = date + ' ' + time
      const stopTime = date + ' ' + nextTime
      this.specification.when.range = startTime + ' to ' + stopTime

      this.showModal = true
    },
    cancel () {
      this.showModal = false
    },
    ok () {
      const range = this.specification.when.range.split(' to ')
      // const dStart = new Date(range[0])
      const dStop = new Date(range[1])

      const sStart = 'now'
      /* if (dStart instanceof Date && !isNaN(dStart)) {
        sStart = String(dStart.getTime())
      } else {
        sStart = 'now'
      } */

      let sStop = ''
      if (dStop instanceof Date && !isNaN(dStop)) {
        sStop = String(dStop.getTime())
      } else {
        sStop = String(new Date().getTime() + 60000)
      }

      const sPeriod = String(this.specification.when.period)
      const sWhen = sStart + ' ... ' + sStop + ' / ' + sPeriod

      this.selectedCapability.when = sWhen
      this.selectedCapability.parameters = this.specification.parameters
      if (this.specification.selectedResults.length > 0) {
        this.selectedCapability.results = this.specification.selectedResults
      }

      this.$emit('send-spec', this.selectedCapability)
    },
    getStatusColor (name) {
      if (name === 'active') {
        return 'success'
      }
      return 'danger'
    },
    getTypeColor (t) {
      if (t === 'measure') {
        return 'info'
      }
      return 'warning'
    },
  },
}
</script>

<style lang="scss">
  .markup-tables {
    .va-table {
      width: 100%;
    }

    .va-card {
      max-width: 1200px;
    }
  }
</style>
