<template>
  <div>

    <div v-if="pas.length == 0" class="text-center mt-5">
      <va-chip color="gray">No Prefix Annoucements to show</va-chip>
    </div>

    <table v-if="pas.length > 0" class="va-table va-table--hoverable">
      <thead>
        <tr>
          <th>Name</th>
          <th>Origin</th>
          <th>Advertized</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(pa, index) in pas" :key="index" :class="getAvailableClass(pa.available)">
          <td>{{ pa.name }}</td>
          <td>{{ findNodeName(pa.originId) }}</td>
          <td>{{ new Date(pa.created).toLocaleString() }}</td>
          <td>

            <va-button flat color="dark" icon="fa fa-trash-o" @click="onDelete(pa.id)" />
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getAvailableClass } from '../../../../assets/icons/colors.js'
export default {
  name: 'PATable',
  props: ['pas', 'nodes', 'onDelete'],
  components: {
  },
  data: function () {
    return {
      getAvailableClass,
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
