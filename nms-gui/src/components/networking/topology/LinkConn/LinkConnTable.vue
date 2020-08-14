<template>
  <div>

    <div v-if="lcs.length == 0" class="text-center mt-5">
      <va-chip color="gray">No LinkConns to show</va-chip>
    </div>

    <table v-if="lcs.length > 0" class="va-table va-table--hoverable">
      <thead>
        <tr>
          <th>Name</th>
          <th>Label</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(lc, index) in lcs" :key="index" @click="onSelected(lc.id)" :class="getStatusClass(lc.status)">
          <td>{{ lc.name }}</td>
          <td>{{ lc.label }}</td>
        <!--  td>
          <va-button small color="danger" @click="onDelete(lc.id)"> Delete </va-button>
          <va-button small color="info" @click="onEdit(lc)"> Edit </va-button>
        </td -->
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getStatusClass } from '../../../../assets/icons/colors.js'
export default {
  name: 'LinkConnTable',
  props: ['lcs', 'onSelected'],
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
  computed: {
  },
}
</script>

<style lang="scss">
</style>
