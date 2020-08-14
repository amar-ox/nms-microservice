<template>
  <va-modal
    v-model="showModal"
    size="large"
    title="Announce new prefix"
    hideDefaultActions
    noOutsideDismiss
    noEscDismiss
  >
    <div class="modal-create-pa">
      <div class="row">
        <va-notification color="danger" v-if="error != ''">
          {{ error }}
        </va-notification>
      </div>

      <div v-if="selectOrigin" class="row">
        <div class="flex xs12">
          <label class="label">Origin</label>
          <va-select
            v-model="originName"
            textBy="source"
            :options="Array.from(nodesNameToId.keys())"
          />
        </div>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Name</label>
          <va-input v-model="nPA.name"/>
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
  name: 'CreatePA',
  props: ['show', 'subnetId', 'originId'],

  data: function () {
    return {
      showModal: false,
      error: '',
      nPA: {
        name: '/',
        originId: 0,
        available: false,
      },
      nodesNameToId: new Map(),
      originName: '',
      selectOrigin: false,
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
  },
  methods: {
    initModal () {
      if (this.originId > 0) {
        this.nPA.originId = this.originId
        this.selectOrigin = false
      } else {
        this.getNodes()
        this.nPA.originId = 0
        this.selectOrigin = true
      }
      this.error = ''
      this.originName = ''
      this.showModal = true
    },
    submit () {
      if (this.nPA.name === '' || this.nPA.name === '/') {
        this.error = 'Name is required'
        return
      }
      if (this.nPA.originId === 0) {
        if (this.originName === '') {
          this.error = 'Origin is required'
          return
        }
        this.nPA.originId = this.nodesNameToId.get(this.originName)
      }
      this.$emit('onOk', this.nPA)
    },
    cancel () {
      this.$emit('onCancel')
      this.showModal = false
    },
    getNodes () {
      let nodesApi = 'https://localhost:8787/api/topology'
      if (this.subnetId !== 0) {
        nodesApi += '/subnet/' + this.subnetId.toString()
      }
      nodesApi += '/nodes'
      axios.get(nodesApi)
        .then(response => {
          this.nodesNameToId = new Map()
          response.data.forEach(node => {
            this.nodesNameToId.set(node.name.split(':')[1], node.id)
          })
        })
        .catch(e => {
          console.log(e)
        })
    },
  },
}
</script>

<style lang="scss">
.modal-create-pa {
  width: 500px;
  max-width: 500px;
}
</style>
