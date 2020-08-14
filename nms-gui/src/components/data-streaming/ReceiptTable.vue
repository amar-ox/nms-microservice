<template>
  <div>
    <va-card :title="title">
      <table v-if="receipts.length" class="va-table va-table--striped va-table--hoverable">
        <thead>
          <tr>
            <th v-for="(col, index) in headers" :key="index">{{ col }}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rct in receipts" :key="rct.schema">
            <td> {{ rct.schema.slice(0,10) }} </td>
            <td> {{ rct.name }} </td>
            <td> {{ rct.agentId }} </td>
            <td> {{ rct.timestamp }} </td>
            <td>
              <va-button small v-if="rct.receipt === 'measure'" color="success" v-on:click="streamLive(rct)" > Stream live </va-button>
              <va-button small color="danger" v-on:click="stopSpec(rct.schema)" > Stop </va-button>
            </td>
          </tr>
        </tbody>
      </table>
    </va-card>

  </div>
</template>

<script>
export default {
  name: 'receipt',
  props: ['receipts'],
  data () {
    return {
      title: 'Active specifications',
      headers: ['Schema', 'Type', 'Agent', 'Start time', 'Action'],
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    streamLive (rct) {
      this.$emit('stream-live', rct)
    },
    stopSpec (schema) {
      this.$emit('stop-spec', schema)
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
