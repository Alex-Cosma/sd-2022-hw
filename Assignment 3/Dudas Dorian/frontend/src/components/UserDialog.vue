<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create user" : "Edit user" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.name" label="Username" />
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field
            v-model="user.password"
            label="Password"
            type="password"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteUser">Delete</v-btn>
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
    deleteUser() {
      api.users.delete(this.user).then(
        () => this.$emit("refresh"),
        (error) => alert(error.response.data.message)
      );
    },
    persist() {
      if (this.isNew) {
        api.users
          .create({
            name: this.user.name,
            email: this.user.email,
            password: this.user.password,
            roles: null,
          })
          .then(
            () => this.$emit("refresh"),
            (error) => alert(error.response.data.message)
          );
      } else {
        api.users
          .edit({
            id: this.user.id,
            name: this.user.name,
            email: this.user.email,
            password: this.user.password,
            roles: null,
          })
          .then(
            () => this.$emit("refresh"),
            (error) => alert(error.response.data.message)
          );
      }
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
