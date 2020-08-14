<template class="subnet">
  <div class="row">
    <div class="flex xs12">
      <table class="va-table va-table--hoverable">
        <thead>
          <tr>
            <th>Name</th>
            <th>Label</th>
            <th>Created</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(subnet, index) in subnets" :key="index" @click="onSelected(index)" :class="getStatusClass(subnet.status)">
            <td>{{ subnet.name }}</td>
            <td>{{ subnet.label }}</td>
            <td>{{ subnet.created }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
import { getStatusClass } from '../../../../assets/icons/colors.js'
export default {
  name: 'SubnetTable',
  props: ['subnets', 'onSelected'],
  components: {
  },
  data: function () {
    return {
      getStatusClass,
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
