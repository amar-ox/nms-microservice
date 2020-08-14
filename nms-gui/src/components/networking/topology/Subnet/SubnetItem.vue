<template>
  <div>
    <div class="row row-equal">
      <div class="flex lg8">
        <va-card :title="tTopology">
          <div class="row mt-1">
            <va-button-toggle
              small
              outline
              v-model="kind"
              :options="kindOptions"
              color="dark"
            />
          </div>
          <div class="topology">
            <d3-network
              ref='net'
              :net-nodes="graphNodes"
              :net-links="graphLinks"
              :options="options"
              @node-click="nodeClick"
              @link-click="linkClick"
            />
          </div>

          <div v-if="subnet.id != 0" class="row">
            <div class="md4">
              <va-button small color="danger" @click="onDelete(subnet.id)"> Delete </va-button>
              <va-button small color="info" @click="onEdit(subnet)"> Edit </va-button>
            </div>

            <div v-if="subnet.id != 0" class="md4 offset--md4">
              <va-button color="warning" small @click="initCreateNode" >
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                Create node
              </va-button>
              <va-button color="warning" small @click="initCreateLink" >
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                Create {{ kind }}
              </va-button>
            </div>
          </div>
        </va-card>
      </div>

      <div class="flex lg4">
        <va-card :title="tTrails">
          <trail-table :trails="trails" :onSelected="getTrail" />
          <div v-if="subnet.id != 0" class="text-center mt-5">
            <va-button small color="warning" @click="initCreateTrail">
              <i class="fa fa-plus-circle" aria-hidden="true"></i>
              Create trail
            </va-button>
          </div>
        </va-card>
      </div>
    </div>

    <va-modal
      v-model="showItem"
      size="large"
      hideDefaultActions
    >
      <node-item v-if="type === 1" :node="selectedNode" :onEdit="initEditNode" :onDelete="deleteNode" @refresh="refresh" />
      <link-item v-if="type === 2" :link="selectedLink" :onEdit="initEditLink" :onDelete="deleteLink" @refresh="refresh"/>
      <link-conn-item v-if="type === 3" :linkConn="selectedLc" :onEdit="initEditLc" :onDelete="deleteLc" @refresh="refresh" />
      <trail-item v-if="type === 4" :trail="selectedTrail" :onEdit="initEditTrail" :onDelete="deleteTrail" @refresh="refresh" />
    </va-modal>

    <create-node @onOk="postNode" @onCancel="showCreateNode = false" :show="showCreateNode" :subnetId="subnet.id" :name="nextNodeName" />
    <create-link @onOk="postLink" @onCancel="showCreateLink = false" :show="showCreateLink"/>
    <create-link-conn @onOk="postLc" @onCancel="showCreateLc = false" :show="showCreateLc" :linkId="0"/>
    <create-trail @onOk="postTrail" @onCancel="showCreateTrail = false" :show="showCreateTrail" />

  </div>
</template>

<script>
import axios from 'axios'
// import icons from '../../../../assets/icons/graph-icons.json'

import { getStatusColor } from '../../../../assets/icons/colors.js'
import D3Network from 'vue-d3-network/src/vue-d3-network.vue'
import LinkItem from '../Link/LinkItem'
import CreateLink from '../Link/CreateLink.vue'
import LinkConnItem from '../LinkConn/LinkConnItem.vue'
import CreateLinkConn from '../LinkConn/CreateLinkConn.vue'
import NodeItem from '../Node/NodeItem.vue'
import CreateNode from '../Node/CreateNode.vue'
import TrailTable from '../Trail/TrailTable.vue'
import TrailItem from '../Trail/TrailItem.vue'
import CreateTrail from '../Trail/CreateTrail.vue'

export default {
  name: 'subnet-item',
  props: ['subnet', 'onEdit', 'onDelete'],
  components: {
    D3Network,
    LinkItem,
    LinkConnItem,
    NodeItem,
    TrailTable,
    TrailItem,
    CreateTrail,
    CreateNode,
    CreateLink,
    CreateLinkConn,
  },
  data: function () {
    return {
      tTopology: 'Topology',
      tTrails: 'Trails',

      kind: 'link',
      kindOptions: [
        { label: 'Physical', value: 'link' },
        { label: 'Logical', value: 'linkConn' },
      ],

      nodes: [],
      links: [],
      pas: [],
      trails: [],

      graphNodes: [],
      graphLinks: [],

      type: 0,
      showItem: false,
      selectedNode: {},
      selectedLink: {},
      selectedLc: {},
      selectedTrail: {},

      showCreateNode: false,
      showCreateLink: false,
      showCreateLc: false,
      showCreateTrail: false,

      nextNodeName: '',
    }
  },
  created () {
    this.getSubnetContent()
  },
  computed: {
    options () {
      return {
        force: 2000,
        nodeSize: 80,
        nodeLabels: true,
        linkLabels: false,
        canvas: false,
        linkWidth: 3,
        fontSize: 18,
      }
    },
  },
  watch: {
    subnet: {
      handler: function () {
        this.getSubnetContent()
      },
    },
    kind: {
      handler: function () {
        this.getSubnetContent()
      },
    },
  },
  methods: {
    getSubnetContent () {
      const api = 'https://localhost:8787/api/topology/'
      let nodesApi = ''
      let linksApi = ''
      let pasApi = ''
      if (this.subnet.id === 0) {
        pasApi = api + 'prefixAnns'
        nodesApi = api + 'nodes'
        linksApi = api + this.kind + 's'
      } else {
        nodesApi = api + 'subnet/' + this.subnet.id.toString() + '/nodes'
        linksApi = api + 'subnet/' + this.subnet.id.toString() + '/' + this.kind + 's'
        pasApi = api + '/subnet/' + this.subnet.id.toString() + '/prefixAnns'
      }
      axios.get(nodesApi)
        .then(response => {
          this.nodes = response.data
          axios.get(linksApi)
            .then(response => {
              this.links = response.data
              axios.get(pasApi)
                .then(response => {
                  this.pas = response.data
                  this.processGraph()
                })
                .catch(e => {
                  console.log(e)
                })
            })
            .catch(e => {
              console.log(e)
            })
        })
        .catch(e => {
          console.log(e)
        })
      this.getTrails()
    },
    processGraph () {
      this.graphNodes = []
      this.graphLinks = []
      this.nodes.forEach(node => {
        const numPas = this.pas.reduce((n, pa) => n + (pa.originId === node.id), 0)
        let pasLabel = ''
        if (numPas > 0) {
          let p = 'prefix'
          if (numPas > 1) {
            p += 'es'
          }
          pasLabel = '\n[' + numPas + ' ' + p + ']'
        }
        const newNode = {
          id: node.id,
          name: node.name.split(':')[1] + pasLabel,
          pinned: true,
          fixed: true,
          fx: node.posx,
          fy: node.posy,
        }
        /* if (node.type === 'fwd') {
          newNode = Object.assign(newNode, { svgSym: icons.routerIcon, svgIcon: null, svgObj: null })
        } else if (node.type === 'switch') {
          newNode = Object.assign(newNode, { svgSym: icons.routerIcon, svgIcon: null, svgObj: null })
        } */
        newNode._color = getStatusColor(node.status)
        newNode._labelClass = 'node-label'
        this.graphNodes.push(newNode)
      })
      this.links.forEach(link => {
        if (this.kind === 'linkConn' || this.subnet.id === 0 || link.type === 'IN') {
          const newLink = {
            id: link.id,
            sid: link.srcVnodeId,
            tid: link.destVnodeId,
          }
          newLink._color = getStatusColor(link.status)
          this.graphLinks.push(newLink)
        }
      })
    },

    // CRUD Node
    getNode (id) {
      this.showItem = false
      const nodeApi = 'https://localhost:8787/api/topology/node/' + id.toString()
      const xcsApi = 'https://localhost:8787/api/topology/node/' + id.toString() + '/xcs'
      const pasApi = 'https://localhost:8787/api/topology/node/' + id.toString() + '/prefixAnns'
      axios.get(nodeApi)
        .then(response => {
          this.selectedNode = response.data
          axios.get(xcsApi)
            .then(response => {
              this.selectedNode.vxcs = response.data
              axios.get(pasApi)
                .then(response => {
                  this.selectedNode.pas = response.data
                  this.showItem = true
                  this.type = 1
                })
                .catch(e => {
                  console.log(e)
                })
            })
            .catch(e => {
              console.log(e)
            })
        })
        .catch(e => {
          console.log(e)
        })
    },
    initCreateNode () {
      this.getNextNodeName()
      this.showCreateNode = true
    },
    postNode (node) {
      axios.post('https://localhost:8787/api/topology/nodes', node, {
        headers: {},
      })
        .then(response => {
          this.showToast('Node ' + node.name + ' created', {
            icon: 'fa-check',
            position: 'top-right',
            duration: 3000,
          })
          this.getSubnetContent()
        })
        .catch(e => {
          console.log(e)
          this.showToast('Node creation failed', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 3000,
          })
        })
      this.showCreateNode = false
    },
    initEditNode (node) {},
    updateNode (node) {
      axios.put('https://localhost:8787/api/topology/node/' + node.id, node, {
        headers: {},
      })
        .then(response => {
          this.showToast('Node ' + node.name + ' updated', {
            icon: 'fa-check',
            position: 'top-right',
            duration: 3000,
          })
          this.getSubnetContent()
        })
        .catch(e => {
          console.log(e)
          this.showToast('Failed to update node', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 3000,
          })
        })
    },
    deleteNode (id) {
      axios.delete('https://localhost:8787/api/topology/node/' + id.toString())
        .then(response => {
          this.getSubnetContent()
          this.$emit('refresh', 'topology.node')
          this.showItem = false
        })
        .catch(e => {
          console.log(e)
        })
    },

    // CRUD Link
    getLink (id) {
      this.showItem = false
      const linkApi = 'https://localhost:8787/api/topology/link/' + id.toString()
      axios.get(linkApi)
        .then(response => {
          this.selectedLink = response.data
          this.showItem = true
          this.type = 2
        })
        .catch(e => {
          console.log(e)
        })
    },
    postLink (link) {
      axios.post('https://localhost:8787/api/topology/links', link, {
        headers: {},
      })
        .then(response => {
          this.showToast('Link ' + link.name + ' created', {
            icon: 'fa-check',
            position: 'top-right',
            duration: 3000,
          })
          this.getSubnetContent()
        })
        .catch(e => {
          console.log(e)
          this.showToast('Link creation failed', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 3000,
          })
        })
      this.showCreateLink = false
    },
    initEditLink (link) {},
    patchLink (link) {},
    deleteLink (link) {
      axios.delete('https://localhost:8787/api/topology/link/' + link.id.toString())
        .then(response => {
          this.getSubnetContent()
          this.$emit('refresh', 'topology.link')
          this.showItem = false
        })
        .catch(e => {
          console.log(e)
        })
    },

    // CRUD LinkConn
    getLc (id) {
      this.showItem = false
      const lcApi = 'https://localhost:8787/api/topology/linkConn/' + id.toString()
      axios.get(lcApi)
        .then(response => {
          this.selectedLc = response.data
          this.showItem = true
          this.type = 3
        })
        .catch(e => {
          console.log(e)
        })
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
          this.getSubnetContent()
        })
        .catch(e => {
          console.log(e)
          this.showToast('LinkConn creation failed', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 3000,
          })
        })
      this.showCreateLc = false
    },
    initEditLc (lc) {},
    deleteLc (lc) {
      axios.delete('https://localhost:8787/api/topology/linkConn/' + lc.id.toString())
        .then(response => {
          this.getSubnetContent()
          this.$emit('refresh', 'topology.lc')
          this.showItem = false
        })
        .catch(e => {
          console.log(e)
        })
    },

    // for both link and lc
    initCreateLink () {
      if (this.kind === 'link') {
        this.showCreateLink = true
      } else {
        this.showCreateLc = true
      }
    },

    nodeClick (event, node) {
      const sNode = this.nodes.find(x => x.id === node.id)
      const dist = Math.sqrt(Math.pow(sNode.posx - node.fx, 2) + Math.pow(sNode.posy - node.fy, 2))
      if (dist < 10) {
        this.getNode(node.id)
      } else {
        if (confirm('Move node?')) {
          sNode.posx = node.fx
          sNode.posy = node.fy
          this.updateNode(sNode)
        } else {
          this.processGraph()
        }
      }
    },
    linkClick (event, link) {
      if (this.kind === 'link') {
        this.getLink(link.id)
      } else {
        this.getLc(link.id)
      }
    },

    // CRUD Trail
    getTrails () {
      let trailsApi = 'https://localhost:8787/api/topology'
      if (this.subnet.id === 0) {
        trailsApi = trailsApi + '/trails'
      } else {
        trailsApi = trailsApi + '/subnet/' + this.subnet.id.toString() + '/trails'
      }
      axios.get(trailsApi)
        .then(response => {
          this.trails = response.data
        })
        .catch(e => {
          console.log(e)
        })
    },

    getTrail (id) {
      this.showItem = false
      const trailApi = 'https://localhost:8787/api/topology/trail/' + id.toString()
      axios.get(trailApi)
        .then(response => {
          this.selectedTrail = response.data
          this.showItem = true
          this.type = 4
        })
        .catch(e => {
          console.log(e)
        })
    },
    initCreateTrail () {
      this.showCreateTrail = true
    },
    postTrail (trail) {
      for (var i = 0, len = this.trails.length; i < len; i++) {
        if (this.trails[i].name === trail.name) {
          this.showToast('Name ' + trail.name + ' already exists', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 3000,
          })
          return
        }
      }
      axios.post('https://localhost:8787/api/topology/trails', trail, {
        headers: {},
      })
        .then(response => {
          this.showToast('Trail ' + trail.name + ' created', {
            icon: 'fa-check',
            position: 'top-right',
            duration: 3000,
          })
          this.getTrails()
        })
        .catch(e => {
          console.log(e)
          this.showToast('Trail creation failed', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 3000,
          })
        })
      this.showCreateTrail = false
    },
    initEditTrail (trail) {},
    patchTrail (trail) {},
    deleteTrail (id) {
      axios.delete('https://localhost:8787/api/topology/trail/' + id.toString())
        .then(response => {
          this.$emit('refresh', 'topology.trail')
          this.getTrails()
          this.showItem = false
        })
        .catch(e => {
          console.log(e)
        })
    },

    refresh (type) {
      this.$emit('refresh', type)
      this.getSubnetContent()
      this.getTrails()
    },

    getNextNodeName () {
      // this.nodes.sort(function(a, b){return a.name - b.name})
      if (this.nodes.length > 0) {
        const maxNodeNo = this.nodes[this.nodes.length - 1].name.split(':')[1].substring(1)
        this.nextNodeName = this.subnet.name + ':n' + (parseInt(maxNodeNo, 10) + 1)
      } else {
        this.nextNodeName = this.subnet.name + ':n0'
      }
    },
  },
}

</script>
<style lang="stylus">
.topology {
  height: 500px;
  max-height: 500px;
}

.net-svg {
  width: 100%;
  height: 100%;
}

.node-label {
  white-space: pre-line;
  margin: 0 0 0 -20px;
}

</style>
