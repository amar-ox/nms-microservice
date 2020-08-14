<template>
  <div class="xc-details mt-3">
    <div class="row">
      <div class="flex lg12">
        <div class="text-center">
          <p class="display-5">Cross Connection Details</p>
        </div>
        <div class="mt-3">
          <va-item>
            <va-item-section side>
              <b>Name:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ xc.name }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Label:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ xc.label }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Description:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ xc.description }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>In Node:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ xc.vnodeId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Source CTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ xc.srcVctpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Destination CTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ xc.destVctpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Drop CTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ xc.dropVctpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Status:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label> <va-badge small :color="getStatusColor(xc.status)" > {{ xc.status }} </va-badge> </va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Info:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ xc.info }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Created:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ xc.created }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Updated:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ xc.updated }}</va-item-label>
            </va-item-section>
          </va-item>
        </div>

        <div class="text-center mt-3">
          <va-button small color="danger" @click="onDelete(xc.id)"> Delete </va-button>
          <va-button small color="info" @click="onEdit(xc)"> Edit </va-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getStatusColor } from '../../../../assets/icons/colors.js'
export default {
  name: 'XcItem',
  props: ['xc', 'onDelete', 'onEdit'],
  components: {
  },
  data: function () {
    return {
      getStatusColor,
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
