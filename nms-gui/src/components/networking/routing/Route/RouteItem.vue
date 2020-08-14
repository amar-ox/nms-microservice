<template>
  <div class="ctp-details mt-3">
    <div class="row">
      <div class="lg12">
        <div class="text-center">
          <p class="display-5">Route Details</p>
        </div>
        <div class="mt-3">
          <va-item>
            <va-item-section side>
              <b>Node:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ route.nodeId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Prefix:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ route.prefix }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Next Hop:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ route.nextHopId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Face:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ route.faceId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Origin:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ route.origin }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Created:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ route.created }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Updated:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ route.updated }}</va-item-label>
            </va-item-section>
          </va-item>
        </div>

        <div class="text-center mt-3">
          <va-button small color="danger" @click="onDelete(route.id)"> Delete </va-button>
          <va-button small color="danger" @click="onEdit(route)"> Edit </va-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import { getStatusColor, getStatusClass, getBusyColor } from '../../../../assets/icons/colors.js'
export default {
  name: 'RouteItem',
  props: ['route', 'onDelete', 'onEdit'],
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
