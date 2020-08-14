<template>
  <va-list fit>
    <va-list-label>
      {{ $t('dashboard.serviceStatus') }}
    </va-list-label>

    <va-item>
      <va-item-section side>
        <va-icon name="fa fa-exchange" color="red" />
      </va-item-section>

      <va-item-section>
        <va-item-label>Data Streaming</va-item-label>
      </va-item-section>

      <va-item-section>
        <va-badge color="primary">{{ data.dss.status }}</va-badge>
      </va-item-section>
    </va-item>

    <va-item>
      <va-item-section side>
        <va-icon name="fa fa-map" color="red" />
      </va-item-section>

      <va-item-section>
        <va-item-label>Topology</va-item-label>
      </va-item-section>

      <va-item-section>
        <va-badge color="primary">{{ data.topology.status }}</va-badge>
      </va-item-section>
    </va-item>

    <va-item>
      <va-item-section side>
        <va-icon name="fa fa-send" color="red" />
      </va-item-section>

      <va-item-section>
        <va-item-label>Routing</va-item-label>
      </va-item-section>

      <va-item-section>
        <va-badge color="primary">{{ data.routing.status }}</va-badge>
      </va-item-section>
    </va-item>

    <va-item>
      <va-item-section side>
        <va-icon name="fa fa-database" color="red" />
      </va-item-section>

      <va-item-section>
        <va-item-label>Storage</va-item-label>
      </va-item-section>

      <va-item-section>
        <va-badge color="primary">{{ data.storage.status }}</va-badge>
      </va-item-section>
    </va-item>

    <va-item>
      <va-item-section side>
        <va-icon name="fa fa-desktop" color="red" />
      </va-item-section>

      <va-item-section>
        <va-item-label>REST API</va-item-label>
      </va-item-section>

      <va-item-section>
        <va-badge color="primary">{{ data.rest.status }}</va-badge>
      </va-item-section>
    </va-item>
  </va-list>
</template>

<script>

export default {
  name: 'DashboardServiceList',
  props: ['data'],
  data () {
    return {
    }
  },
  mounted () {
  },
  methods: {
  },
}
</script>

<style scoped lang="scss">
</style>
