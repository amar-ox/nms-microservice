<template>
  <va-list fit class="logging-list">
    <va-list-label>
      {{ $t('dashboard.serviceLogs') }}
    </va-list-label>

    <va-item v-for="(line, index) in data.slice().reverse()" :key="index" >
      <va-item-section>
        <va-item-label>{{ line }}</va-item-label>
      </va-item-section>
    </va-item>
  </va-list>
</template>

<script>

export default {
  name: 'DashboardServiceLogs',
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
.logging-list {
  flex-direction: column;
  height: 100%;
  overflow: scroll;
}
</style>
