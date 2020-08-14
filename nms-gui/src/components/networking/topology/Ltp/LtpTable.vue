<template>
  <div>

    <div v-if="ltps.length == 0" class="text-center mt-5">
      <va-chip color="gray">No LTPs to show</va-chip>
    </div>

    <table v-if="ltps.length > 0" class="va-table va-table--hoverable">
      <thead>
        <tr>
          <th>Name</th>
          <th>Label</th>
          <th>Busy</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(ltp, index) in ltps" :key="index" @click="onSelected(ltp.id)" :class="getStatusClass(ltp.status)">
          <td>{{ ltp.name }}</td>
          <td>{{ ltp.label }}</td>
          <td>
            <va-badge small :color="getBusyColor(ltp.busy)" >{{ ltp.busy }}</va-badge>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getStatusClass, getBusyColor } from '../../../../assets/icons/colors.js'
export default {
  name: 'LtpTable',
  props: ['ltps', 'onSelected'],
  components: {
  },
  data: function () {
    return {
      getStatusClass,
      getBusyColor,
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
