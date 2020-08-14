<template>
  <div class="networking">
    <div class="row">
      <div class="flex xs12">
        <va-card :title="title">

          <va-button v-if="subnets.length > 0" small outline :color="colorSelected(subnetAll.id)" @click="selectAllSubnets()"> {{ subnetAll.name }} </va-button>
          <va-chip v-if="subnets.length == 0" color="gray">No Subnets to show</va-chip>

          <va-popover v-for="(subnet, index) in subnets" :key="index"
            :message="subnet.description"
            placement="right"
          >
            <va-button small outline :color="colorSelected(subnet.id)"  @click="getSubnetIndex(index)">
              {{ subnet.name }}
            </va-button>
          </va-popover>

          <va-button class="x" small color="warning" @click="initCreateSubnet">
            <i class="fa fa-plus-circle" aria-hidden="true"></i>
            Create subnet
          </va-button>
        </va-card>
      </div>
    </div>

    <div class="row">
      <div class="flex xs12">
        <Topology ref="topology" :subnet="selectedSn" @refresh="refresh" />
      </div>
    </div>

    <div class="row">
      <div class="flex xs12">
        <Routing ref="routing" :subnet="selectedSn" @refresh="refresh" />
      </div>
    </div>

    <create-subnet @onOk="postSubnet" @onCancel="showCreateSubnet = false" :show="showCreateSubnet" :name="nextSnName" />
  </div>
</template>

<script>
import axios from 'axios'
import Topology from './topology/Topology.vue'
import Routing from './routing/Routing.vue'
import CreateSubnet from './topology/Subnet/CreateSubnet.vue'

export default {
  name: 'networking',
  components: {
    Topology,
    Routing,
    CreateSubnet,
  },
  data: function () {
    return {
      title: 'Subnets',
      subnetAll: {
        id: 0,
        name: 'all subnets',
      },
      subnets: [],
      selectedSn: {},
      showCreateSubnet: false,
      nextSnName: 's0',
    }
  },
  created () {
    this.getAllSubnets()
    this.selectAllSubnets()
  },

  methods: {
    selectAllSubnets () {
      this.selectedSn = this.subnetAll
    },
    getSubnetIndex (index) {
      this.selectedSn = this.subnets[index]
    },

    // CRUD Subnet
    getAllSubnets () {
      axios.get('https://localhost:8787/api/topology/subnets')
        .then(response => {
          this.subnets = response.data
        })
        .catch(e => {
          console.log(e)
        })
    },
    initCreateSubnet () {
      this.getNextSnName()
      this.showCreateSubnet = true
    },
    postSubnet (subnet) {
      axios.post('https://localhost:8787/api/topology/subnets', subnet, {
        headers: {},
      })
        .then(response => {
          this.showToast('Subnet ' + subnet.name + ' created', {
            icon: 'fa-check',
            position: 'top-right',
            duration: 5000,
          })
          this.getAllSubnets()
        })
        .catch(e => {
          console.log(e)
          this.showToast('Subnet creation failed', {
            icon: 'fa-close',
            position: 'top-right',
            duration: 5000,
          })
        })
      this.showCreateSubnet = false
    },

    colorSelected (id) {
      if (id === this.selectedSn.id) {
        return 'info'
      } else {
        return 'gray'
      }
    },

    getNextSnName () {
      // this.nodes.sort(function(a, b){return a.name - b.name})
      if (this.subnets.length > 0) {
        const maxSnNo = this.subnets[this.subnets.length - 1].name.substring(1)
        this.nextSnName = 's' + (parseInt(maxSnNo, 10) + 1)
      }
    },

    refresh (type) {
      const refType = type.split('.')
      if (refType[0] === 'routing') {
        this.$refs.topology.refreshAll()
      } else {
        this.$refs.routing.refreshAll()
      }
      if (refType[1] === 'subnet') {
        this.getAllSubnets()
        this.selectAllSubnets()
      }
    },
  },
  eventbus: {
    lifecycleHooks: {
      created (context, eventbus) {
        // subscribe to topology service info
        eventbus.registerHandler('nms.to.ui', function (err, msg) {
          if (err) {
            console.log('VertxEventBus error: ', err)
            return
          }
          context.getAllSubnets()
          context.$refs.topology.refreshAll()
          context.$refs.routing.refreshAll()
        })
      },
    },
  },
}
</script>

<style lang="scss">
  .row-equal .flex {
    .va-card {
      height: 100%;
    }
  }

  .networking {
    .va-card {
      margin-bottom: 0 !important;
    }
  }

  .row-up {
    background-color: #40e583;
  }

  .row-down {
    background-color: #e34b4a;
  }

  .row-disconn {
    background-color: #ffc200;
  }

</style>
