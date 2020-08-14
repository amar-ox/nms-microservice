<template>
  <div>

    <div v-if="faces.length == 0" class="text-center mt-5">
      <va-chip color="gray">No Faces to show</va-chip>
    </div>

    <table v-if="faces.length > 0" class="va-table va-table--hoverable">
      <thead>
        <tr>
          <th>Id</th>
          <th>Local</th>
          <th>Remote</th>
          <th>Scheme</th>
          <th>Updated</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(face, index) in faces" :key="index" :class="getStatusClass(face.status)">
          <td>{{ face.id }}</td>
          <td>{{ face.local }}</td>
          <td>{{ face.remote }}</td>
          <td>{{ face.scheme }}</td>
          <td>{{ new Date(face.updated).toLocaleString() }}</td>
          <td>
            <va-button flat color="dark" icon="fa fa-trash-o" @click="onDelete(face.id)" />
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getStatusClass } from '../../../../assets/icons/colors.js'
export default {
  name: 'FaceTable',
  props: ['faces', 'onDelete'],
  components: {
  },
  data: function () {
    return {
      getStatusClass,
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
