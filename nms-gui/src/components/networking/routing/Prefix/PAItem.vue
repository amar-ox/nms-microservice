<template>
  <div class="ctp-details mt-3">
    <div class="row">
      <div class="lg12">
        <div class="text-center">
          <p class="display-5">Prefix Announcement Details</p>
        </div>
        <div class="mt-3">
          <va-item>
            <va-item-section side>
              <b>Name:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ pa.name }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Origin:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ pa.origin }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Expiration time:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ pa.expiration }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Annoucement time:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ pa.created }}</va-item-label>
            </va-item-section>
          </va-item>
        </div>

        <div class="text-center mt-3">
          <va-button small color="danger" @click="onDelete(pa.id)"> Withdraw </va-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import { getStatusColor, getStatusClass, getBusyColor } from '../../../../assets/icons/colors.js'
export default {
  name: 'PAItem',
  props: ['pa', 'onDelete'],
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
