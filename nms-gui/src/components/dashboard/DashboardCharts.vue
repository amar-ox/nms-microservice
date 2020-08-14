<template>
  <div class="flex xs12 md6 xl3">
    <!-- va-card :title="$t('dashboard.serviceActivity')">
        <va-chart class="chart chart--donut" :data="serviceActivityData" type="donut"/>
      </va-card -->
  </div>
</template>

<script>
// import { getLineChartData } from '../../data/charts/LineChartData'

const themes = {
  primary: '#40e583',
  secondary: '#002c85',
  success: '#40e583',
  info: '#2c82e0',
  danger: '#e34b4a',
  warning: '#ffc200',
  gray: '#babfc2',
  dark: '#34495e',
}

export default {
  name: 'dashboard-charts',
  props: ['data'],
  components: {
  },
  data () {
    return {
      serviceActivityData: {
        labels: ['Data Streaming', 'Storage', 'REST API', 'Topology', 'Routing'],
        datasets: [{
          label: 'Messages (#)',
          backgroundColor: [themes.danger, themes.info, themes.primary, themes.dark, themes.warning],
          data: [0, 0, 0, 0, 0],
        }],
      },
    }
  },
  watch: {
    servicesData: {
      handler: function () {
        this.serviceActivityData.datasets[0].data[0] = this.servicesData.dss.activity
        this.serviceActivityData.datasets[0].data[1] = this.servicesData.storage.activity
        this.serviceActivityData.datasets[0].data[2] = this.servicesData.rest.activity
        this.serviceActivityData.datasets[0].data[3] = this.servicesData.topology.activity
        this.serviceActivityData.datasets[0].data[4] = this.servicesData.routing.activity
      },
      deep: true,
    },

  },
  methods: {
  },
  computed: {
  },
}
</script>

<style scoped>
  .chart {
    height: 400px;
  }
</style>
