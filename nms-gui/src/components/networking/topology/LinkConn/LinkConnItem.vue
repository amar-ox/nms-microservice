<template>
  <div class="lc-details mt-3">
    <div class="row">
      <div class="lg12">
        <div class="text-center">
          <p class="display-5">LinkConn Details</p>
        </div>
        <div class="mt-3">
          <va-item>
            <va-item-section side>
              <b>Name:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ linkConn.name }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Label:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ linkConn.label }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Description:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ linkConn.description }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Source CTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ linkConn.srcVctpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Destination CTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ linkConn.destVctpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Status:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label> <va-badge small :color="getStatusColor(linkConn.status)" > {{ linkConn.status }} </va-badge> </va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Info:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ linkConn.info }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Created:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ linkConn.created }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Updated:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ linkConn.updated }}</va-item-label>
            </va-item-section>
          </va-item>
        </div>

        <div class="text-center mt-3">
          <div class="flex xs12">
            <va-button class="ml-2" small color="danger" @click="onDelete(linkConn)"> Delete </va-button>
            <va-button small color="info" @click="onEdit(linkConn)"> Edit </va-button>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { getStatusColor } from '../../../../assets/icons/colors.js'
export default {
  name: 'LinkConnItem',
  props: ['linkConn', 'onEdit', 'onDelete'],

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
