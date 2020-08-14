<template>
  <va-modal
    v-model="showModal"
    size="large"
    title="Create new ltp"
    hideDefaultActions
    noOutsideDismiss
    noEscDismiss
  >
    <div class="modal-create-ltp">
      <div class="row">
        <va-notification color="danger" v-if="error != ''">
          {{ error }}
        </va-notification>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Name</label>
          <va-input disabled v-model="nLtp.name"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Label</label>
          <va-input v-model="nLtp.label"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Description</label>
          <va-input v-model="nLtp.description"/>
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
export default {
  name: 'CreateLtp',
  props: ['show', 'nodeId', 'name'],

  data: function () {
    return {
      showModal: false,
      error: '',
      infoArray: [['', '']],
      nLtp: {
        vnodeId: this.nodeId,
        name: '',
        label: '',
        description: '',
        info: {},
        busy: false,
      },
    }
  },

  created () {
  },
  watch: {
    show: {
      handler: function () {
        if (this.show === true) {
          this.initCreateLtp()
        } else {
          this.showModal = false
        }
      },
      deep: true,
    },
  },
  methods: {
    initCreateLtp () {
      this.nLtp = {
        vnodeId: this.nodeId,
        name: this.name,
        label: '',
        description: '',
        info: {},
        busy: false,
      }
      this.infoArray = [['port', '']]
      this.error = ''
      this.showModal = true
    },
    addInfoItem () {
      const lastItem = this.infoArray[this.infoArray.length - 1]
      if ((lastItem[0] !== '') && (lastItem[1] !== '')) {
        this.infoArray.push(['', ''])
      }
    },
    submit () {
      const macRegex = new RegExp('^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$')
      const portValue = this.infoArray.find(x => x[0] === 'port')[1]
      if (!macRegex.test(portValue)) {
        this.error = 'port is required'
        return
      }
      for (var i = 0, len = this.infoArray.length; i < len; i++) {
        const item = this.infoArray[i]
        if (item[0] !== '' && item[1] !== '') {
          // TODO: support boolean
          if (isNaN(item[1])) {
            this.nLtp.info[item[0]] = item[1]
          } else {
            this.nLtp.info[item[0]] = Number(item[1])
          }
        }
      }
      this.$emit('onOk', this.nLtp)
    },
    cancel () {
      this.$emit('onCancel')
      this.showModal = false
    },
  },
}
</script>

<style lang="scss">
.modal-create-ltp {
  width: 500px;
  max-width: 500px;
}
</style>
