<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Save new user" : "Edit user" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.name" label="Username"/>
          <v-text-field v-model="user.password" label="Password"/>
          <v-text-field v-model="user.email" label="Email"/>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteUser">
            Delete User
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
    persist() {
      if (this.isNew) {
        api.users
            .create({
              name: this.user.name,
              password: this.user.password,
              email: this.user.email,
            })
            .then(() => this.$emit("refresh"));
      } else {
        api.users
            .edit({
              id: this.user.id,
              name: this.user.name,
              password: this.user.password,
              email: this.user.email,
            })
            .then(() => this.$emit("refresh"));
      }
    },
    deleteUser() {
      api.users
          .delete(this.user)
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
