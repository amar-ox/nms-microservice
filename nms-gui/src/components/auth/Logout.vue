<template>
  <div></div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Logout',
  data () {
    return {
    }
  },
  computed: {
  },
  created () {
    this.logout()
  },
  methods: {
    logout () {
      axios.post('https://localhost:8787/logout', {}, {
        headers: {},
      })
        .then(response => {
        })
        .catch(e => {
          console.log('login error: ', e)
        })
      delete axios.defaults.headers.common.Authorization
      localStorage.removeItem('user-token')
      this.$router.push({ name: 'Login' })
    },
  },
}
</script>

<style lang="scss">
</style>
