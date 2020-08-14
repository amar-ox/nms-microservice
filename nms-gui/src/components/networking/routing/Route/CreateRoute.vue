<template>
  <va-modal
    v-model="showModal"
    size="large"
    title="Create new route"
    hideDefaultActions
    noOutsideDismiss
    noEscDismiss
  >
    <div class="modal-create-route">
      <div class="row">
        <va-notification color="danger" v-if="error != ''">
          {{ error }}
        </va-notification>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Prefix</label>
          <va-input v-model="nRoute.prefix"/>
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
  name: 'CreatePA',
  props: ['show', 'paId'],

  data: function () {
    return {
      showModal: false,
      error: '',
      nRoute: {
        paId: 0,
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
  },
  methods: {
    initModal () {
      console.log('init create PA modal')
      // TODO: if no origin, get list of nodes...
      this.nRoute = {
        paId: this.paId,
      }
      this.error = ''
      this.showModal = true
    },
    submit () {
      this.$emit('onOk', this.nRoute)
    },
    cancel () {
      this.$emit('onCancel')
      this.showModal = false
    },
  },
}
</script>

<style lang="scss">
.modal-create-route {
  width: 500px;
  max-width: 500px;
}
</style>
