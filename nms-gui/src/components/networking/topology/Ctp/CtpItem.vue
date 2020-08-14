<template>
  <div class="ctp-details mt-3">
    <div class="row">
      <div class="lg12">
        <div class="text-center">
          <p class="display-5">CTP Details</p>
        </div>
        <div class="mt-3">
          <va-item>
            <va-item-section side>
              <b>Name:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ ctp.name }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Label:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ ctp.label }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Description:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ ctp.description }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>In LTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ ctp.vltpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Link:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ ctp.vlinkId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Info:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ ctp.info }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Created:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ ctp.created }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Updated:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ ctp.updated }}</va-item-label>
            </va-item-section>
          </va-item>
        </div>

        <div class="text-center mt-3">
          <va-button small color="danger" @click="onDelete(ctp.id)"> Delete </va-button>
          <va-button small color="info" @click="onEdit(ctp)"> Edit </va-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import { getStatusColor, getStatusClass, getBusyColor } from '../../../../assets/icons/colors.js'
export default {
  name: 'CtpItem',
  props: ['ctp', 'onEdit', 'onDelete'],
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
