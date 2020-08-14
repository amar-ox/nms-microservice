// Polyfills
import 'core-js/stable'
import 'regenerator-runtime/runtime'
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import { ColorThemePlugin } from '../services/vuestic-ui'
import store from '../store/index'
import router from '../router/index'
import { VuesticPlugin } from '../services/vuestic-ui/components'
import '../i18n/index'
import YmapPlugin from 'vue-yandex-maps'
import VueClipboard from 'vue-clipboard2'

import VertxEventBus from 'vue-vertx3-eventbus-client'
import axios from 'axios'

import '../metrics'
import '../registerServiceWorker'

export const EventBus = new Vue()

if (process.env.VUE_APP_BUILD_VERSION) {
  // eslint-disable-next-line
  const message = `%c${'Build_information:'}\n %c${'Version'}: %c${VERSION},\n %c${'Timestamp'}: %c${TIMESTAMP},\n %c${'Commit'}: %c${COMMIT}`
  // eslint-disable-next-line
  console.info(
    message,
    'color: blue;', 'color: red;', 'color: blue;', 'color: red;', 'color: blue;', 'color: red;', 'color: blue;',
  )
}

Vue.use(VertxEventBus, {
  host: 'localhost',
  path: '/eventbus',
  port: 8888,
  options: {
    transports: [
      'xhr-polling',
      'websocket',
    ],
  },
})

Vue.use(VuesticPlugin)
Vue.use(YmapPlugin)
Vue.use(VueClipboard)

Vue.use(ColorThemePlugin, {
  // override colors here.
})

router.beforeEach((to, from, next) => {
  store.commit('setLoading', true)
  setTimeout(() => { next() }, 100)
  // next()
})

router.afterEach((to, from) => {
  store.commit('setLoading', false)
})

const token = localStorage.getItem('user-token')
if (token) {
  axios.defaults.headers.common.Authorization = 'Bearer ' + token
}

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
})
