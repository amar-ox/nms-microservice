<template>
  <div class="link-details mt-3">
    <div class="row">
      <div class="lg4">
        <div class="text-center">
          <p class="display-5">Link Details</p>
        </div>
        <div class="mt-3">
          <va-item>
            <va-item-section side>
              <b>Name:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ link.name }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Label:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ link.label }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Description:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ link.description }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Type:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ link.type }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Source LTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ link.srcVltpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Destination LTP:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ link.destVltpId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Status:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label> <va-badge small :color="getStatusColor(link.status)" > {{ link.status }} </va-badge> </va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Info:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ link.info }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Created:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ link.created }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Updated:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ link.updated }}</va-item-label>
            </va-item-section>
          </va-item>
        </div>

        <div class="text-center mt-3">
          <va-button small color="danger" @click="onDelete(link)"> Delete </va-button>
          <va-button small color="info" @click="onEdit(link)"> Edit </va-button>
        </div>
      </div>

      <div class="lg8">
        <div class="text-center">
          <p class="display-5">Link Connections</p>
          <div class="mt-3">
            <link-conn-table :lcs="link.vlinkConns" :onSelected="getLc" />
            <va-button small color="warning" @click="initAddLc()">
              <i class="fa fa-plus-circle" aria-hidden="true"></i>
              Add LinkConn </va-button>
          </div>
        </div>
      </div>

    </div>
    <create-link-conn @onOk="postLc" @onCancel="showAddLc = false" :show="showAddLc" :linkId="link.id" :linkName="link.name"/>
    <va-modal
      v-model="showItem"
      size="large"
      hideDefaultActions
    >
      <link-conn-item :linkConn="selectedLc" :onDelete="deleteLc" :onEdit="initEditLc" />
    </va-modal>

  </div>
</template>

<script>
import { getStatusColor } from '../../../../assets/icons/colors.js'
import axios from 'axios'
import LinkConnTable from '../LinkConn/LinkConnTable.vue'
import LinkConnItem from '../LinkConn/LinkConnItem.vue'
import CreateLinkConn from '../LinkConn/CreateLinkConn.vue'

export default {
  name: 'LinkItem',
  props: ['link', 'onEdit', 'onDelete'],
  components: {
    LinkConnTable,
    LinkConnItem,
    CreateLinkConn,
  },
  data: function () {
    return {
      getStatusColor,
      showItem: false,
      selectedLc: {},
      showAddLc: false,
    }
  },
  created () {
  },
  watch: {
  },
  methods: {
    // CRUD LinkConn (get/edit/delete only)
    getLc (id) {
      this.showItem = false
      const lcApi = 'https://localhost:8787/api/topology/linkConn/' + id.toString()
      axios.get(lcApi)
        .then(response => {
          this.selectedLc = response.data
          this.showItem = true
        })
        .catch(e => {
          console.log(e)
        })
    },

    initAddLc () {
      this.showAddLc = true
    },
    postLc (lc) {
      axios.post('https://localhost:8787/api/topology/linkConns', lc, {
        headers: {},
      })
        .then(response => {
          this.showToast('LinkConn ' + lc.name + ' created', {
            icon: 'fa-check',
            position: 'top-right',
            duration: 3000,
          })
          this.$emit('refresh', 'topology.face')
          this.getLcsByLink()
        })
        .catch(e => {
          console.log(e)
        })
      this.showAddLc = false
    },

    initEditLc (lc) {},
    patchLc (lc) {},
    deleteLc (lc) {
      axios.delete('https://localhost:8787/api/topology/linkConn/' + lc.id.toString())
        .then(response => {
          this.showItem = false
          this.$emit('refresh', 'topology.lc')
          this.getLcsByLink()
        })
        .catch(e => {
          console.log(e)
        })
    },

    getLcsByLink () {
      const lcsApi = 'https://localhost:8787/api/topology/link/' + this.link.id.toString() + '/linkConns'
      axios.get(lcsApi)
        .then(response => {
          this.link.vlinkConns = response.data
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
