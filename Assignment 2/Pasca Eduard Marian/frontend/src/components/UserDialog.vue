<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          Edit user
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Username" />
          <v-text-field v-model="user.email" label="Email" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist()">
            Save
          </v-btn>
          <v-btn @click="remove" v-if = "isNew === false">
            {{ "Delete" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    remove() {
        api.users
            .remove({
                id: this.user.id,
                username: this.user.username,
                email: this.user.email,
                password: this.user.password,
            })
            .then(() => this.$emit("refresh"));
    },
    persist() {
        api.users
          .edit({
            id: this.user.id,
            username: this.user.username,
            email: this.user.email,
          })
          .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.user.id;
    },
  },

};
</script>

<style scoped></style>
