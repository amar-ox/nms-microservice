<template>
  <va-modal
    v-model="showModal"
    size="large"
    title="Create new link"
    hideDefaultActions
    noOutsideDismiss
    noEscDismiss
  >
    <div class="modal-create-link">
      <div class="row">
        <va-notification color="danger" v-if="error != ''">
          {{ error }}
        </va-notification>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Name</label>
          <va-input disabled v-model="nLink.name"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Label</label>
          <va-input v-model="nLink.label"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Description</label>
          <va-input v-model="nLink.description"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Type</label>
          <va-select
            v-model="nLink.type"
            textBy="source"
            :options="['IN','EXT']"
          />
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Source LTP</label>
          <va-select
            v-model="srcVltpName"
            textBy="source"
            :options="Array.from(ltpsNameToId.keys())"
          />
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Destination LTP</label>
          <va-select
            v-model="destVltpName"
            textBy="source"
            :options="Array.from(ltpsNameToId.keys())"
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
          <va-button  small @click="submit"> Submit </va-button>
        </div>
      </div>
    </div>
  </va-modal>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CreateLink',
  props: ['show'],

  data: function () {
    return {
      showModal: false,
      error: '',
      infoArray: [['', '']],
      nLink: {
        name: '',
        label: '',
        description: '',
        info: {},
        srcVltpId: 0,
        destVltpId: 0,
        type: 'IN',
      },
      srcVltpName: '',
      destVltpName: '',
      ltpsNameToId: new Map(),
    }
  },

  created () {
  },
  watch: {
    show: {
      handler: function () {
        if (this.show === true) {
          this.initCreateLink()
        } else {
          this.showModal = false
        }
      },
      deep: true,
    },
    srcVltpName: function (newVal, oldVal) {
      this.nLink.name = newVal + '=' + this.destVltpName
    },
    destVltpName: function (newVal, oldVal) {
      this.nLink.name = this.srcVltpName + '=' + newVal
    },
  },
  methods: {
    initCreateLink () {
      this.getLtps()
      this.nLink = {
        name: '',
        label: '',
        description: '',
        info: {},
        srcVltpId: 0,
        destVltpId: 0,
        type: 'IN',
      }
      this.infoArray = [['', '']]
      this.srcVltpName = ''
      this.destVltpName = ''
      this.error = ''
      this.showModal = true
    },
    getLtps () {
      const ltpsApi = 'https://localhost:8787/api/topology/ltps'
      axios.get(ltpsApi)
        .then(response => {
          // this.ltps = response.data
          this.ltpsNameToId = new Map()
          response.data.forEach(ltp => {
            if (!ltp.busy) {
              this.ltpsNameToId.set(ltp.name, ltp.id)
            }
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
      const srcNode = this.srcVltpName.split(':')[1]
      const destNode = this.destVltpName.split(':')[1]
      if (srcNode === destNode) {
        this.error = 'Source and destination nodes must be different'
        return
      }
      if (this.nLink.name === '') {
        this.error = 'Name is required'
        return
      }
      this.nLink.srcVltpId = this.ltpsNameToId.get(this.srcVltpName)
      this.nLink.destVltpId = this.ltpsNameToId.get(this.destVltpName)
      for (var i = 0, len = this.infoArray.length; i < len; i++) {
        const item = this.infoArray[i]
        if (item[0] !== '' && item[1] !== '') {
          // TODO: support boolean
          if (isNaN(item[1])) {
            this.nLink.info[item[0]] = item[1]
          } else {
            this.nLink.info[item[0]] = Number(item[1])
          }
        }
      }
      this.$emit('onOk', this.nLink)
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
.modal-create-link {
  width: 500px;
  max-width: 500px;
}
</style>
