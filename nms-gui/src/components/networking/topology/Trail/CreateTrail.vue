<template>
  <va-modal
    v-model="showModal"
    size="large"
    title="Create new trail"
    hideDefaultActions
    noOutsideDismiss
    noEscDismiss
  >
    <div class="modal-create-trail">
      <div class="row">
        <va-notification color="danger" v-if="error != ''">
          {{ error }}
        </va-notification>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Name</label>
          <va-input disabled v-model="nTrail.name"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Label</label>
          <va-input v-model="nTrail.label"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Description</label>
          <va-input v-model="nTrail.description"/>
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
          <va-button  small color="danger" @click="cancel"> Cancel </va-button>
          <va-button  small  @click="submit"> Submit </va-button>
        </div>
      </div>
    </div>
  </va-modal>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CreateTrail',
  props: ['show'],

  data: function () {
    return {
      showModal: false,
      error: '',
      infoArray: [['', '']],
      nTrail: {
        name: '',
        label: '',
        description: '',
        info: {},
        srcVctpId: 0,
        destVctpId: 0,
      },
      srcVctpName: '',
      destVctpName: '',
      ctpsNameToId: new Map(),
    }
  },

  created () {
  },
  watch: {
    show: {
      handler: function () {
        if (this.show === true) {
          this.initCreateTrail()
        } else {
          this.showModal = false
        }
      },
      deep: true,
    },
    srcVctpName: function (newVal, oldVal) {
      this.nTrail.name = newVal + '#' + this.destVctpName
    },
    destVctpName: function (newVal, oldVal) {
      this.nTrail.name = this.srcVctpName + '#' + newVal
    },
  },
  methods: {
    initCreateTrail () {
      this.getCtps()
      console.log('init create trail modal')
      this.nTrail = {
        name: '',
        label: '',
        description: '',
        info: {},
        srcVctpId: 0,
        destVctpId: 0,
      }
      this.infoArray = [['', '']]
      this.srcVctpName = ''
      this.destVctpName = ''
      this.error = ''
      this.showModal = true
    },
    getCtps () {
      const ctpsApi = 'https://localhost:8787/api/topology/ctps'
      axios.get(ctpsApi)
        .then(response => {
          // this.ltps = response.data
          this.ctpsNameToId = new Map()
          response.data.forEach(ctp => {
            this.ctpsNameToId.set(ctp.name, ctp.id)
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
      const srcNode = this.srcVctpName.split(':')[1]
      const destNode = this.destVctpName.split(':')[1]
      if (srcNode === destNode) {
        this.error = 'Source and destination nodes must be different'
        return
      }
      if (this.nTrail.name === '') {
        this.error = 'Name is required'
        return
      }
      this.nTrail.srcVctpId = this.ctpsNameToId.get(this.srcVctpName)
      this.nTrail.destVctpId = this.ctpsNameToId.get(this.destVctpName)

      for (var i = 0, len = this.infoArray.length; i < len; i++) {
        const item = this.infoArray[i]
        if (item[0] !== '' && item[1] !== '') {
          // TODO: support boolean
          if (isNaN(item[1])) {
            this.nTrail.info[item[0]] = item[1]
          } else {
            this.nTrail.info[item[0]] = Number(item[1])
          }
        }
      }

      console.log('nTrail: ', JSON.stringify(this.nTrail))
      this.$emit('onOk', this.nTrail)
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
.modal-create-trail {
  width: 500px;
  max-width: 500px;
}
</style>
