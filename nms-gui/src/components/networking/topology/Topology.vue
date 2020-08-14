<template>
  <div class="row">
    <div class="flex xs12">
      <subnet-item ref="subnet" :subnet="subnet" :onEdit="initEditSubnet" :onDelete="deleteSubnet" @refresh="refresh" />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import SubnetItem from './Subnet/SubnetItem.vue'

export default {
  name: 'topology',
  props: ['subnet'],
  components: {
    SubnetItem,
  },
  data: function () {
    return {
      title: 'Subnets',
    }
  },
  created () {
  },
  computed: {
  },
  watch: {
  },
  methods: {
    refreshAll () {
      this.$refs.subnet.getSubnetContent()
    },

    initEditSubnet (subnet) {},
    patchSubnet (subnet) {},
    deleteSubnet (id) {
      axios.delete('https://localhost:8787/api/topology/subnet/' + id.toString())
        .then(response => {
          this.$emit('refresh', 'topology.subnet')
        })
        .catch(e => {
          console.log(e)
        })
    },

    refresh (type) {
      this.$emit('refresh', type)
    },
  },
}

</script>
<style lang="stylus">
</style>
