<template>
  <div>

    <div v-if="routes.length == 0" class="text-center mt-5">
      <va-chip color="gray">No Routes to show</va-chip>
    </div>

    <table v-if="routes.length > 0" class="va-table va-table--striped va-table--hoverable">
      <thead>
        <tr>
          <th>Node</th>
          <th>Prefix</th>
          <th>NextHop</th>
          <th>Face</th>
          <th>Cost</th>
          <th>Origin</th>
          <th>Updated</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(route, index) in routes" :key="index" >
          <td>{{ findNodeName(route.nodeId) }}</td>
          <td>{{ route.prefix }}</td>
          <td>{{ findNodeName(route.nextHopId) }}</td>
          <td>{{ route.faceId }}</td>
          <td>{{ route.cost }}</td>
          <td>{{ route.origin }}</td>
          <td>{{ new Date(route.updated).toLocaleString() }}</td>
          <td>
            <va-button flat color="dark" icon="fa fa-trash-o" @click="onDelete(route.id)" />
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'RouteTable',
  props: ['routes', 'nodes', 'onDelete'],
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
    findNodeName (id) {
      return this.nodes.find(x => x.id === id).name.split(':')[1]
    },
  },
  computed: {},
}
</script>

<style lang="scss">
</style>
