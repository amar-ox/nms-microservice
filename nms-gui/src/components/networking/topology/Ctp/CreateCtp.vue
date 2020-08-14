<template>
  <va-modal
    v-model="showModal"
    size="large"
    title="Create new ctp"
    hideDefaultActions
    noOutsideDismiss
    noEscDismiss
  >
    <div class="modal-create-ctp">
      <div class="row">
        <va-notification color="danger" v-if="error != ''">
          {{ error }}
        </va-notification>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Name</label>
          <va-input disabled v-model="nCtp.name"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Label</label>
          <va-input v-model="nCtp.label"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Description</label>
          <va-input v-model="nCtp.description"/>
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
  name: 'CreateCtp',
  props: ['show', 'ltpId', 'name'],

  data: function () {
    return {
      showModal: false,
      error: '',
      infoArray: [['', '']],
      nCtp: {
        vltpId: this.ltpId,
        name: this.ltpName + ':',
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
          this.initModal()
        } else {
          this.showModal = false
        }
      },
      deep: true,
    },
    /* 'nPrefix.node': function (newVal, oldVal) {
      this.nPrefix.interface = ''
      this.getInterfaces(newVal)
    }, */
  },
  methods: {
    initModal () {
      console.log('init create ctp modal')
      this.nCtp = {
        vltpId: this.ltpId,
        name: this.name,
        label: '',
        description: '',
        info: {},
        busy: false,
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
    submit () {
      if (this.nCtp.name === '') {
        this.error = 'Name is required'
        return
      }
      for (var i = 0, len = this.infoArray.length; i < len; i++) {
        const item = this.infoArray[i]
        if (item[0] !== '' && item[1] !== '') {
          // TODO: support boolean
          if (isNaN(item[1])) {
            this.nCtp.info[item[0]] = item[1]
          } else {
            this.nCtp.info[item[0]] = Number(item[1])
          }
        }
      }
      this.$emit('onOk', this.nCtp)
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
.modal-create-ctp {
  width: 500px;
  max-width: 500px;
}
</style>
