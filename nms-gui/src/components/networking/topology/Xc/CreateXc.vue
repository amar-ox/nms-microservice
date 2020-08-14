<template>
  <va-modal
    v-model="showModal"
    size="large"
    title="Create new xc"
    hideDefaultActions
    noOutsideDismiss
    noEscDismiss
  >
    <div class="modal-create-xc">
      <div class="row">
        <va-notification color="danger" v-if="error != ''">
          {{ error }}
        </va-notification>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Trail</label>
          <va-select
            v-model="trailName"
            textBy="source"
            :options="Array.from(trailsNameToId.keys())"
          />
        </div>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Name</label>
          <va-input disabled v-model="nXc.name"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Label</label>
          <va-input v-model="nXc.label"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Description</label>
          <va-input v-model="nXc.description"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Type</label>
          <va-input v-model="nXc.type"/>
        </div>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Source CTP</label>
          <va-select
            v-model="srcVctpName"
            textBy="source"
            :options="Array.from(ctpsNameToId.keys())"
          />
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Destination CTP</label>
          <va-select
            v-model="destVctpName"
            textBy="source"
            :options="Array.from(ctpsNameToId.keys())"
          />
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Drop CTP</label>
          <va-select
            v-model="dropVctpName"
            textBy="source"
            :options="Array.from(ctpsNameToId.keys())"
          />
        </div>
      </div>

      <div>
        <label class="label">Info</label>
        <div v-for="(info, index) in infoArray" :key="index" class="row">
          <div class="flex xs5 offset--xs1">
            <va-input v-model="info[0]"/>
          </div>
          <div class="flex xs5 ml-1">
            <va-input v-model="info[1]"/>
          </div>
        </div>
        <div class="text-center">
          <va-button color="gray" @click="addInfoItem">
            <i class="fa fa-plus-circle" aria-hidden="true"></i>
          </va-button>
        </div>
      </div>

      <div class="row mt-5">
        <div class="flex xs6 offset--xs6">
          <va-button small color="danger" @click="cancel"> Cancel </va-button>
          <va-button small @click="submit"> Submit </va-button>
        </div>
      </div>
    </div>
  </va-modal>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CreateXc',
  props: ['show', 'nodeId', 'name'],

  data: function () {
    return {
      showModal: false,
      error: '',
      infoArray: [['', '']],
      nXc: {
        vnodeId: this.nodeId,
        vtrailId: 0,
        name: '',
        label: '',
        description: '',
        info: {},
        type: '',
        srcVctpId: 0,
        destVctpId: 0,
        dropVctpId: 0,
      },
      trailName: '',
      srcVctpName: '',
      destVctpName: '',
      dropVctpName: '',
      ctpsNameToId: new Map(),
      trailsNameToId: new Map(),
    }
  },

  created () {
  },
  watch: {
    show: {
      handler: function () {
        if (this.show === true) {
          this.initModal()
        } else {
          this.showModal = false
        }
      },
      deep: true,
    },
    /* 'srcVctpName': function (newVal, oldVal) {
      this.nLc.name = newVal + '#' + this.destVctpName
    },
    'destVctpName': function (newVal, oldVal) {
      this.nLc.name = this.srcVctpName + '#' + newVal
    }, */
  },
  methods: {
    initModal () {
      this.getCtpsByNode()
      this.getTrails()
      console.log('init create ctp modal')
      this.nXc = {
        vnodeId: this.nodeId,
        vtrailId: 0,
        name: this.name,
        label: '',
        description: '',
        info: {},
        type: '',
        srcVctpId: 0,
        destVctpId: 0,
        dropVctpId: 0,
      }
      this.infoArray = [['', '']]
      this.error = ''
      this.showModal = true
    },
    getCtpsByNode () {
      const ctpsApi = 'https://localhost:8787/api/topology/node/' + this.nodeId.toString() + '/ctps'
      axios.get(ctpsApi)
        .then(response => {
          this.ctpsNameToId = new Map()
          response.data.forEach(ctp => {
            this.ctpsNameToId.set(ctp.name, ctp.id)
          })
        })
        .catch(e => {
          console.log(e)
        })
    },
    getTrails () {
      const trailsApi = 'https://localhost:8787/api/topology/trails'
      axios.get(trailsApi)
        .then(response => {
          this.trailsNameToId = new Map()
          response.data.forEach(tr => {
            this.trailsNameToId.set(tr.name, tr.id)
          })
        })
        .catch(e => {
          console.log(e)
        })
    },
    addInfoItem () {
      const lastItem = this.infoArray[this.infoArray.length - 1]
      if ((lastItem[0] !== '') && (lastItem[1] !== '')) {
        this.infoArray.push(['', ''])
      }
    },
    submit () {
      if (this.nXc.name === '') {
        this.error = 'Name is required'
        return
      }
      this.nXc.srcVctpId = this.ctpsNameToId.get(this.srcVctpName)
      this.nXc.destVctpId = this.ctpsNameToId.get(this.destVctpName)
      this.nXc.vtrailId = this.trailsNameToId.get(this.trailName)
      // optional
      if (this.dropVctpName !== '') {
        this.nXc.dropVctpId = this.ctpsNameToId.get(this.dropVctpName)
      }
      for (var i = 0, len = this.infoArray.length; i < len; i++) {
        const item = this.infoArray[i]
        if (item[0] !== '' && item[1] !== '') {
          // TODO: support boolean
          if (isNaN(item[1])) {
            this.nXc.info[item[0]] = item[1]
          } else {
            this.nXc.info[item[0]] = Number(item[1])
          }
        }
      }
      this.$emit('onOk', this.nXc)
      // this.showModal = false
    },
    cancel () {
      this.$emit('onCancel')
      this.showModal = false
    },
  },
}
</script>

<style lang="scss">
.modal-create-xc {
  width: 500px;
  max-width: 500px;
}
</style>
