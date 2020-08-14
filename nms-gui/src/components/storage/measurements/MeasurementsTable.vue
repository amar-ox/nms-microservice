<template>
  <div>
    <va-card v-if="measurements.length" :title="title">
      <table class="va-table va-table--striped va-table--hoverable">
        <thead>
          <tr>
            <th v-for="(col, index) in headers" :key="index">{{ col }}</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(msr, index) in measurements" :key="index">
            <td>
              <va-badge color="info">
                {{ msr.name }}
              </va-badge>
            </td>
            <td>
              <p>
                {{ msr.endpoint }}
              </p>
            </td>

            <td>
              <p>
                {{ msr.begins }}
              </p>
            </td>

            <td>
              <p>
                {{ msr.ends }}
              </p>
            </td>

            <td>
              {{ msr.total }}
            </td>

            <td>
              <va-button small color="info" @click="expandMsr(msr._id)" >
                Expand
              </va-button>
              <va-button small color="danger" @click="deleteMsr(msr._id)" >
                Delete
              </va-button>
            </td>
          </tr>
        </tbody>
      </table>
    </va-card>
  </div>
</template>

<script>
// import { EventBus } from '../../../app/main'

export default {
  name: 'measurements',
  props: ['measurements'],
  data () {
    return {
      headers: ['Name', 'Endpoint', 'Starts at', 'Ends at', 'Total results', 'Actions'],
      title: 'Stored measurements',
    }
  },
  created () {
  },
  computed: {
  },
  methods: {
    expandMsr (id) {
      this.$emit('expand-msr', id)
    },
    deleteMsr (id) {
      this.$emit('delete-msr', id)
    },
  },
}
</script>

<style lang="scss">
  .markup-tables {
    .va-table {
      width: 100%;
    }

    .va-card {
      max-width: 1200px;
    }
  }
</style>
