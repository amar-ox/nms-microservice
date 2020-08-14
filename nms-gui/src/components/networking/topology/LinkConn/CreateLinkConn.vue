<template>
  <va-modal
    v-model="showModal"
    size="large"
    title="Create new linkConn"
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

      <div v-if="selectLink" class="row">
        <div class="flex xs12">
          <label class="label">Link</label>
          <va-select
            v-model="selectedLinkName"
            textBy="source"
            :options="Array.from(linksNameToId.keys())"
          />
        </div>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Name</label>
          <va-input disabled v-model="nLc.name"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Label</label>
          <va-input v-model="nLc.label"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Description</label>
          <va-input v-model="nLc.description"/>
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
  name: 'CreateLinkConn',
  props: ['show', 'linkId', 'linkName'],

  data: function () {
    return {
      showModal: false,
      error: '',
      infoArray: [['', '']],
      nLc: {
        name: '',
        label: '',
        description: '',
        info: {},
        srcVctpId: 0,
        destVctpId: 0,
        vlinkId: 0,
      },
      selectedLinkName: '',
      selectLink: false,
      linksNameToId: new Map(),
    }
  },

  created () {
  },
  watch: {
    show: {
      handler: function () {
        if (this.show === true) {
          this.initCreateLc()
        } else {
          this.showModal = false
        }
      },
      deep: true,
    },
    selectedLinkName: function (newVal, oldVal) {
      this.nLc.name = this.setNextLcName(this.linksNameToId.get(newVal))
    },
  },
  methods: {
    initCreateLc () {
      if (this.linkId > 0) {
        this.selectedLinkName = this.linkName
        this.setNextLcName(this.linkId)
        this.selectLink = false
      } else {
        this.getLinks()
        this.selectedLinkName = ''
        this.selectLink = true
      }
      this.nLc = {
        name: '',
        label: '',
        description: '',
        info: {},
        srcVctpId: 0,
        destVctpId: 0,
        vlinkId: this.linkId,
      }
      this.infoArray = [['', '']]
      this.error = ''
      this.showModal = true
    },
    addInfoItem () {
      const lastItem = this.infoArray[this.infoArray.length - 1]
      if ((lastItem[0] !== '') && (lastItem[1] !== '')) {
        this.infoArray.push(['', ''])
      }
    },
    setNextLcName (id) {
      const lcsApi = 'https://localhost:8787/api/topology/link/' + id + '/linkConns'
      axios.get(lcsApi)
        .then(response => {
          const lcs = response.data
          if (lcs.length > 0) {
            const maxLcNo = lcs[lcs.length - 1].name.split('|')[1].substring(1)
            this.nLc.name = this.selectedLinkName + '|c' + (parseInt(maxLcNo, 10) + 1)
          } else {
            this.nLc.name = this.selectedLinkName + '|c0'
          }
        })
        .catch(e => {
          this.nLc.name = 'undefined'
          console.log(e)
        })
    },
    getLinks () {
      const linksApi = 'https://localhost:8787/api/topology/links'
      axios.get(linksApi)
        .then(response => {
          this.linksNameToId = new Map()
          response.data.forEach(link => {
            this.linksNameToId.set(link.name, link.id)
          })
        })
        .catch(e => {
          console.log(e)
        })
    },
    submit () {
      if (this.nLc.name === 'undefined') {
        this.error = 'Name is required'
        return
      }
      if (this.linkId === 0) {
        this.nLc.vlinkId = this.linksNameToId.get(this.selectedLinkName)
      }
      for (var i = 0, len = this.infoArray.length; i < len; i++) {
        const item = this.infoArray[i]
        if (item[0] !== '' && item[1] !== '') {
          // TODO: support boolean
          if (isNaN(item[1])) {
            this.nLc.info[item[0]] = item[1]
          } else {
            this.nLc.info[item[0]] = Number(item[1])
          }
        }
      }
      this.$emit('onOk', this.nLc)
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
