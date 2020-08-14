<template>
  <div class="rest-api">
    <div class="row row-equal">
      <div class="flex xs12 lg6">
        <va-card :title="$t('restApi.request')">
          <form>
            <va-medium-editor>
              <pre class="json-body">
                  {{ request }}
                </pre>
            </va-medium-editor>
          </form>
        </va-card>
        <va-button color="success" @click="sendRequest()"> {{ $t('restApi.send') }}</va-button>
      </div>
      <div class="flex xs12 lg6">
        <va-card :title="$t('restApi.response')">
          <pre v-if="!errors.length" class="json-body">
              {{ response }}
            </pre>
          <pre v-if="errors.length" class="json-body">
              {{ errors }}
            </pre>
        </va-card>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'rest-api',
  components: {
  },
  data () {
    return {
      request: {
        service: 'nms.topology',
        query: {
          action: 'get_all_nodes',
          params: {},
        },
      },
      response: {},
      errors: [],
    }
  },
  created () {
  },
  methods: {
    sendRequest () {
      const headers = {}
      this.errors = []
      this.response = {}
      const pre = document.getElementsByClassName('json-body')[0]
      const sBody = JSON.parse(pre.textContent)
      axios.post('http://localhost:9090/nms', sBody, {
        headers: headers,
      })
        .then(response => {
          this.response = response.data
          console.log(response.data)
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
  },
}
</script>

<style lang="scss">
  .row-equal .flex {
    .va-card {
      height: 600px;
    }
  }

  .rest-api {
    .va-card {
      margin-bottom: 0 !important;
      max-height: 600px;
      overflow: scroll;
    }
  }
</style>
