<template>
  <va-modal
    v-model="showModal"
    size="large"
    title="Create new node"
    hideDefaultActions
    noOutsideDismiss
    noEscDismiss
  >
    <div class="modal-create-node">
      <div class="row">
        <va-notification color="danger" v-if="error != ''">
          {{ error }}
        </va-notification>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Name</label>
          <va-input disabled v-model="nNode.name"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Label</label>
          <va-input v-model="nNode.label"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Description</label>
          <va-input v-model="nNode.description"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Type</label>
          <va-input v-model="nNode.type"/>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12">
          <label class="label">Location</label>
          <va-input v-model="nNode.location"/>
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
  name: 'CreateNode',
  props: ['show', 'subnetId', 'name'],

  data: function () {
    return {
      showModal: false,
      error: '',
      infoArray: [['', '']],
      nNode: {
        vsubnetId: this.subnetId,
        name: '',
        label: '',
        description: '',
        info: {},
        type: '',
        posx: 0,
        posy: 0,
        location: '',
      },
    }
  },

  created () {
  },
  watch: {
    show: {
      handler: function () {
        if (this.show === true) {
          this.initCreateNode()
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
    initCreateNode () {
      this.nNode = {
        vsubnetId: this.subnetId,
        name: this.name,
        label: '',
        description: '',
        info: {},
        type: '',
        posx: Math.floor(Math.random() * Math.floor(800)),
        posy: Math.floor(Math.random() * Math.floor(500)),
        location: '',
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
      if (this.nNode.name === '') {
        this.error = 'Name is required'
        return
      }
      for (var i = 0, len = this.infoArray.length; i < len; i++) {
        const item = this.infoArray[i]
        if (item[0] !== '' && item[1] !== '') {
          // TODO: support boolean
          if (isNaN(item[1])) {
            this.nNode.info[item[0]] = item[1]
          } else {
            this.nNode.info[item[0]] = Number(item[1])
          }
        }
      }
      this.$emit('onOk', this.nNode)
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
.modal-create-node {
  width: 500px;
  max-width: 500px;
}
</style>
