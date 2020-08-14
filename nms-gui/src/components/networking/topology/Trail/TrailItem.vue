<template>
  <div class="trail-details mt-3">
    <div class="row">
      <div class="lg4">
        <div class="text-center">
          <p class="display-5">Trail Details</p>
        </div>
        <div class="mt-3">
          <va-item>
            <va-item-section side>
              <b>Name:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ trail.name }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Label:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ trail.label }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Description:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ trail.description }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Source CTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ trail.srcVctpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Destination CTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ trail.destVctpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Status:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label> <va-badge small :color="getStatusColor(trail.status)" > {{ trail.status }} </va-badge> </va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Info:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ trail.info }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Created:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ trail.created }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Updated:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ trail.updated }}</va-item-label>
            </va-item-section>
          </va-item>
        </div>

        <div class="text-center mt-3">
          <va-button small color="danger" @click="onDelete(trail.id)"> Delete </va-button>
          <va-button small color="info" @click="onEdit(trail)"> Edit </va-button>
        </div>
      </div>

      <div class="lg8">
        <div class="text-center">
          <p class="display-5">Cross Connections</p>
          <div class="mt-3">
            <xc-table :xcs="trail.vxcs" :onSelected="getXc" />
          </div>
        </div>
      </div>

    </div>

    <va-modal
      v-model="showItem"
      size="large"
      hideDefaultActions
    >
      <xc-item :xc="selectedXc" :onDelete="deleteXc" :onEdit="initEditXc" />
    </va-modal>

    <create-xc @onOk="postXc" @onCancel="showCreateXc = false" :show="showCreateXc" :trailId="trail.id" />
  </div>
</template>

<script>
import { getStatusColor } from '../../../../assets/icons/colors.js'
import axios from 'axios'
import XcTable from '../Xc/XcTable.vue'
import XcItem from '../Xc/XcItem.vue'
import CreateXc from '../Xc/CreateXc.vue'

export default {
  name: 'TrailItem',
  props: ['trail', 'onDelete', 'onEdit'],
  components: {
    XcTable,
    XcItem,
    CreateXc,
  },
  data: function () {
    return {
      getStatusColor,
      showItem: false,
      showCreateXc: false,
      selectedXc: {},
    }
  },
  created () {
  },
  watch: {
  },
  methods: {
    // CRUD XC
    getXc (id) {
      this.showItem = false
      const xcApi = 'https://localhost:8787/api/topology/xc/' + id.toString()
      axios.get(xcApi)
        .then(response => {
          this.selectedXc = response.data
          this.showItem = true
        })
        .catch(e => {
          console.log(e)
        })
    },
    initAddXc () {
      this.showCreateXc = true
    },
    postXc (xc) {
      this.showCreateXc = false
      // this.$emit('refresh', 'trail.xc.add')
    },

    initEditXc (xc) {},
    patchXc (xc) {},

    deleteXc (id) {
      axios.delete('https://localhost:8787/api/topology/xc/' + id.toString())
        .then(response => {
          console.log(response.data)
          // this.$emit('refresh', 'trail.xc.delete')
          this.showItem = false
          this.getXcsByTrail()
        })
        .catch(e => {
          console.log(e)
        })
    },

    getXcsByTrail () {
      const xcsApi = 'https://localhost:8787/api/topology/trail/' + this.trail.id.toString() + '/xcs'
      axios.get(xcsApi)
        .then(response => {
          this.trail.vxcs = response.data
        })
        .catch(e => {
          console.log(e)
        })
    },
  },
  computed: {},
}
</script>

<style lang="scss">
</style>
