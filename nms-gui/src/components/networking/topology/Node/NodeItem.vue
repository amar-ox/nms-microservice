<template>
  <div class="node-details mt-3">
    <div class="row">
      <div class="lg4">
        <div class="text-center">
          <p class="display-5">Node Details</p>
        </div>
        <div class="mt-3">
          <va-item>
            <va-item-section side>
              <b>ID:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ node.id }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Name:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ node.name }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Label:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ node.label }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Description:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ node.description }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>In Subnet:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ node.vsubnetId }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Type:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ node.type }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Location:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ node.location }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Position:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>[ {{ node.posx }} , {{ node.posy }} ]</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Status:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label> <va-badge small :color="getStatusColor(node.status)" > {{ node.status }} </va-badge> </va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Info:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ node.info }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Created:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ node.created }}</va-item-label>
            </va-item-section>
          </va-item>

          <va-item>
            <va-item-section side>
              <b>Updated:</b>
            </va-item-section>
            <va-item-section>
              <va-item-label>{{ node.updated }}</va-item-label>
            </va-item-section>
          </va-item>
        </div>

        <div class="text-center mt-3">
          <va-button small color="danger" @click="onDelete(node.id)"> Delete </va-button>
          <va-button small color="info" @click="onEdit(node)"> Edit </va-button>
        </div>
      </div>

      <div class="lg8">
        <div class="text-center">
          <va-tabs grow v-model="tabValue">
            <va-tab>
              <p class="display-5">LTPs</p>
            </va-tab>
            <va-tab>
              <p class="display-5">XCs</p>
            </va-tab>
            <va-tab>
              <p class="display-5">PAs</p>
            </va-tab>
          </va-tabs>

          <div class="mt-3" v-if="tabValue == 0">
            <ltp-table :ltps="node.vltps" :onSelected="getLtp" />
            <va-button small color="warning" @click="initAddLtp()">
              <i class="fa fa-plus-circle" aria-hidden="true"></i>
              Add LTP </va-button>
          </div>
          <div class="mt-3" v-if="tabValue == 1">
            <xc-table :xcs="node.vxcs" :onSelected="getXc" />
            <va-button small color="warning" @click="initAddXc()">
              <i class="fa fa-plus-circle" aria-hidden="true"></i>
              Add XC </va-button>
          </div>

          <div class="mt-3" v-if="tabValue == 2">
            <p-a-table :pas="node.pas" :nodes="nodes" :onDelete="deletePrefixAnn" />
            <va-button small color="warning" @click="initPrefixAnn()">
              <i class="fa fa-plus-circle" aria-hidden="true"></i>
              Announce Prefix </va-button>
          </div>
        </div>
      </div>
    </div>

    <va-modal
      v-model="showItem"
      size="large"
      hideDefaultActions
    >
      <ltp-item v-if="type === 1" :ltp="selectedLtp" :onDelete="deleteLtp" :onEdit="initEditLtp" @refresh="refresh" />
      <xc-item v-if="type === 2" :xc="selectedXc" :onDelete="deleteXc" :onEdit="initEditXc" />
    </va-modal>

    <create-ltp @onOk="postLtp" @onCancel="showCreateLtp = false" :show="showCreateLtp" :nodeId="node.id" :name="nextLtpName" />
    <create-xc @onOk="postXc" @onCancel="showCreateXc = false" :show="showCreateXc" :nodeId="node.id" :name="nextXcName" />
    <create-p-a @onOk="postPrefixAnn" @onCancel="showCreatePA = false" :show="showCreatePA" :originId="node.id" />
  </div>
</template>

<script>
import { getStatusColor } from '../../../../assets/icons/colors.js'
import axios from 'axios'
import LtpTable from '../Ltp/LtpTable.vue'
import LtpItem from '../Ltp/LtpItem.vue'
import CreateLtp from '../Ltp/CreateLtp.vue'
import XcTable from '../Xc/XcTable.vue'
import XcItem from '../Xc/XcItem.vue'
import CreateXc from '../Xc/CreateXc.vue'
import PATable from '../../routing/Prefix/PATable.vue'
import CreatePA from '../../routing/Prefix/CreatePA.vue'

export default {
  name: 'NodeItem',
  props: ['node', 'onEdit', 'onDelete'],
  components: {
    LtpTable,
    LtpItem,
    CreateLtp,
    XcTable,
    XcItem,
    CreateXc,
    PATable,
    CreatePA,
  },
  data: function () {
    return {
      getStatusColor,
      tabValue: 0,
      showItem: false,
      type: 0,
      selectedLtp: {},
      selectedXc: {},
      showCreateLtp: false,
      showCreateXc: false,
      showCreatePA: false,

      nextLtpName: '',
      nextXcName: '',

      nodes: [],
    }
  },

  created () {
    this.nodes.push({ id: this.node.id, name: this.node.name })
  },
  watch: {
  },
  methods: {
    // CRUD LTP
    initAddLtp () {
      this.getNextLtpName()
      this.showCreateLtp = true
    },
    postLtp (ltp) {
      axios.post('https://localhost:8787/api/topology/ltps', ltp, {
        headers: {},
      })
        .then(response => {
          this.showToast('LTP ' + ltp.name + ' created', {
            icon: 'fa-check',
            position: 'top-right',
            duration: 3000,
          })
          this.getLtpsByNode()
        })
        .catch(e => {
          console.log(e)
          this.showToast('LTP creation failed', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 3000,
          })
        })
      this.showCreateLtp = false
    },
    getLtp (id) {
      this.showItem = false
      const ltpApi = 'https://localhost:8787/api/topology/ltp/' + id.toString()
      axios.get(ltpApi)
        .then(response => {
          this.selectedLtp = response.data
          this.showItem = true
          this.type = 1
        })
        .catch(e => {
          console.log(e)
        })
    },
    initEditLtp (ltp) {},
    patchLtp (ltp) {},
    deleteLtp (id) {
      axios.delete('https://localhost:8787/api/topology/ltp/' + id.toString())
        .then(response => {
          this.$emit('refresh', 'topology.ltp')
          this.showItem = false
          this.getLtpsByNode()
        })
        .catch(e => {
          console.log(e)
        })
    },

    // CRUD XC
    initAddXc () {
      axios.get('https://localhost:8787/api/topology/node/' + this.node.id.toString() + '/ctps')
        .then(response => {
          if (response.data.length < 2) {
            this.showToast('Not enough CTPs ', {
              icon: 'fa-close',
              position: 'top-right',
              duration: 3000,
            })
          } else {
            this.getNextXcName()
            this.showCreateXc = true
          }
        })
        .catch(e => {
          this.showToast('Cannot get CTPs', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 3000,
          })
        })
    },
    postXc (xc) {
      axios.post('https://localhost:8787/api/topology/xcs', xc, {
        headers: {},
      })
        .then(response => {
          this.showToast('XC ' + xc.name + ' created', {
            icon: 'fa-check',
            position: 'top-right',
            duration: 3000,
          })
          this.getXcsByNode()
        })
        .catch(e => {
          console.log(e)
          this.showToast('XC creation failed', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 3000,
          })
        })
      this.showCreateXc = false
    },
    getXc (id) {
      this.showItem = false
      const xcApi = 'https://localhost:8787/api/topology/xc/' + id.toString()
      axios.get(xcApi)
        .then(response => {
          this.selectedXc = response.data
          this.showItem = true
          this.type = 2
        })
        .catch(e => {
          console.log(e)
        })
    },
    initEditXc (xc) {},
    patchXc (xc) {},
    deleteXc (id) {
      axios.delete('https://localhost:8787/api/topology/xc/' + id.toString())
        .then(response => {
          this.$emit('refresh', 'topology.xc')
          this.showItem = false
          this.getXcsByNode()
        })
        .catch(e => {
          console.log(e)
        })
    },

    // Prefix Announcements
    initPrefixAnn () {
      this.showCreatePA = true
    },
    postPrefixAnn (pa) {
      axios.post('https://localhost:8787/api/topology/prefixAnns', pa, {
        headers: {},
      })
        .then(response => {
          this.showToast('Prefix advertized', {
            icon: 'fa-check',
            position: 'top-right',
            duration: 3000,
          })
          this.getPAsByNode()
          this.$emit('refresh', 'topology.pa')
        })
        .catch(e => {
          console.log(e)
          this.showToast('Prefix announcement failed', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 3000,
          })
        })
      this.showCreatePA = false
    },
    deletePrefixAnn (id) {
      axios.delete('https://localhost:8787/api/topology/prefixAnn/' + id.toString())
        .then(response => {
          this.showToast('Prefix withdrawn', {
            icon: 'fa-check',
            position: 'top-right',
            duration: 3000,
          })
          this.getPAsByNode()
          this.$emit('refresh', 'topology.pa')
        })
        .catch(e => {
          console.log(e)
        })
    },

    // other
    getLtpsByNode () {
      const ltpsApi = 'https://localhost:8787/api/topology/node/' + this.node.id.toString() + '/ltps'
      axios.get(ltpsApi)
        .then(response => {
          this.node.vltps = response.data
        })
        .catch(e => {
          console.log(e)
        })
    },
    getXcsByNode () {
      const xcsApi = 'https://localhost:8787/api/topology/node/' + this.node.id.toString() + '/xcs'
      axios.get(xcsApi)
        .then(response => {
          this.node.vxcs = response.data
        })
        .catch(e => {
          console.log(e)
        })
    },
    getPAsByNode () {
      const pasApi = 'https://localhost:8787/api/topology/node/' + this.node.id.toString() + '/prefixAnns'
      axios.get(pasApi)
        .then(response => {
          this.node.pas = response.data
        })
        .catch(e => {
          console.log(e)
        })
    },

    refresh (type) {
      this.$emit('refresh', type)
    },

    getNextLtpName () {
      if (this.node.vltps.length > 0) {
        const maxLtpNo = this.node.vltps[this.node.vltps.length - 1].name.split(':')[2].substring(1)
        this.nextLtpName = this.node.name + ':l' + (parseInt(maxLtpNo, 10) + 1)
      } else {
        this.nextLtpName = this.node.name + ':l0'
      }
    },
    getNextXcName () {
      if (this.node.vxcs.length > 0) {
        const maxXcNo = this.node.vxcs[this.node.vxcs.length - 1].name.split(':')[2].substring(1)
        this.nextXcName = this.node.name + ':x' + (parseInt(maxXcNo, 10) + 1)
      } else {
        this.nextXcName = this.node.name + ':x0'
      }
    },
  },

  computed: {},
}
</script>

<style lang="scss">
</style>
