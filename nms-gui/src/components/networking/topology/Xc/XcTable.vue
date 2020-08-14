<template>
  <div>

    <div v-if="xcs.length == 0" class="text-center mt-5">
      <va-chip color="gray">No Cross Connections to show</va-chip>
    </div>

    <table v-if="xcs.length > 0" class="va-table va-table--striped va-table--hoverable">
      <thead>
        <tr>
          <th>Name</th>
          <th>Label</th>
          <th>Created</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(xc, index) in xcs" :key="index" @click="onSelected(xc.id)" :class="getStatusClass(xc.status)">
          <td>{{ xc.name }}</td>
          <td>{{ xc.label }}</td>
          <td>{{ xc.created }}</td>
        <!-- td>
          <va-button small color="danger" @click="onDelete(xc.id)"> Delete </va-button>
          <va-button small color="info" @click="onEdit(xc)"> Edit </va-button>
        </td -->
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getStatusClass } from '../../../../assets/icons/colors.js'
export default {
  name: 'XcTable',
  props: ['xcs', 'onSelected'],
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
