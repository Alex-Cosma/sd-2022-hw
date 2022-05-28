<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
      persistent
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          Choose a student from the list
        </v-toolbar>
        <v-data-table
          :headers="headers"
          :items="students"
          @click:row="selectStudent">
        </v-data-table>
        <v-card-actions>
          <v-btn @click="close">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import {auth as store} from "@/store/auth.module";
import api from "@/api";

export default {
  name: "StudentDialog",
  props: {
    opened: Boolean,
    students: Array,
    nothing: Array,
  },
  data () {
    return {
      headers: [
        { text: 'Username', value: 'name' },
        { text: 'Name', value: 'firstName' },
        { text: 'Surname', value: 'lastName' },
      ],
    }
  },
  methods: {
    close () {
      this.$emit('close')
    },
    selectStudent (student) {
      api.users.addStudentForInstructor(student.id, store.state.user.id).then(() => {
        this.$emit('refresh')
      })
    },
  },
}
</script>

<style scoped>

</style>