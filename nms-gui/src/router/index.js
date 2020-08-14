import Vue from 'vue'
import Router from 'vue-router'
import AppLayout from '../components/admin/AppLayout'
import Login from '../components/auth/Login'
import Logout from '../components/auth/Logout'

Vue.use(Router)

const EmptyParentComponent = {
  template: '<router-view></router-view>',
}

const ifNotAuthenticated = (to, from, next) => {
  if (!localStorage.getItem('user-token')) {
    next()
    return
  }
  next('/')
}

const ifAuthenticated = (to, from, next) => {
  if (localStorage.getItem('user-token')) {
    next()
    return
  }
  next('/login')
}

export default new Router({
  mode: process.env.VUE_APP_ROUTER_MODE_HISTORY === 'true' ? 'history' : 'hash',
  routes: [
    {
      path: '*',
      redirect: { name: 'dashboard' },
    },
    {
      name: 'Login',
      path: '/login',
      component: Login,
      beforeEnter: ifNotAuthenticated,
    },
    {
      name: 'Logout',
      path: '/logout',
      component: Logout,
    },
    {
      name: 'Admin',
      path: '/admin',
      beforeEnter: ifAuthenticated,
      component: AppLayout,
      children: [
        {
          name: 'dashboard',
          path: 'dashboard',
          component: () => import('../components/dashboard/Dashboard.vue'),
          default: true,
        },
        {
          name: 'data-streaming',
          path: 'data-streaming',
          component: () => import('../components/data-streaming/DataStreaming.vue'),
          default: true,
        },
        {
          name: 'networking',
          path: 'networking',
          component: () => import('../components/networking/Networking.vue'),
          default: true,
        },
        {
          name: 'storage',
          path: 'storage',
          component: EmptyParentComponent,
          children: [
            {
              name: 'measurements',
              path: 'measurements',
              component: () => import('../components/storage/measurements/Measurements.vue'),
            },
            {
              name: 'collections',
              path: 'collections',
              component: () => import('../components/storage/collections/Collections.vue'),
            },
          ],
        },
        {
          name: 'rest-api',
          path: 'rest-api',
          component: () => import('../components/rest-api/RestApi.vue'),
          default: true,
        },
      ],
    },
  ],
})
