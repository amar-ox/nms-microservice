<template>
  <div class="ctp-details mt-3">
    <div class="row">
      <div class="lg12">
        <div class="text-center">
          <p class="display-5">Face Details</p>
        </div>
        <div class="mt-3">
          <va-item>
            <va-item-section side>
              <b>Name:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ face.name }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Label:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ face.label }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>In CTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ face.vltpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>LinkConn:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ face.vlinkId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Created:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ face.created }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Updated:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ face.updated }}</va-item-label>
            </va-item-section>
          </va-item>
        </div>

        <div class="text-center mt-3">
          <va-button small color="danger" @click="onDelete(face.id)"> Delete </va-button>
          <va-button small color="info" @click="onEdit(face)"> Edit </va-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import { getStatusColor, getStatusClass, getBusyColor } from '../../../../assets/icons/colors.js'
export default {
  name: 'FaceItem',
  props: ['face', 'onEdit', 'onDelete'],
  components: {
  },
  data: function () {
    return {
    }
  },
  created () {
  },
  watch: {
  },
  methods: {
  },
  computed: {},
}
</script>

<style lang="scss">
</style>
