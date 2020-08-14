<template>
  <va-modal
    v-model="showModal"
    size="large"
    title="Create new Face"
    hideDefaultActions
    noOutsideDismiss
    noEscDismiss
  >
    <div class="modal-create-face">
      <div class="row">
        <va-notification color="danger" v-if="error != ''">
          {{ error }}
        </va-notification>
      </div>

      <div class="row">
        <div class="flex xs12">
          <label class="label">Label</label>
          <va-input v-model="nFace.label"/>
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
  name: 'CreateFace',
  props: ['show', 'ctpId', 'lcId'],

  data: function () {
    return {
      showModal: false,
      error: '',
      nFace: {
        vctpId: this.ctpId,
        vlinkConnId: this.lcId,
        label: '',
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
      console.log('init create face modal')
      this.nFace = {
        vctpId: this.ctpId,
        vlinkConnId: this.lcId,
        label: '',
      }
      this.error = ''
      this.showModal = true
    },
    submit () {
      this.$emit('onOk', this.nFace)
    },
    cancel () {
      this.$emit('onCancel')
      this.showModal = false
    },
  },
}
</script>

<style lang="scss">
.modal-create-face {
  width: 500px;
  max-width: 500px;
}
</style>
