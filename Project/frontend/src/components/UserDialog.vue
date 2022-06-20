<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="black" dark>
          {{ isNew ? "Create user" : "Edit user" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Username" />
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
          <v-btn v-if="!isNew" @click="deleteU">Delete</v-btn>
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
    deleteU() {
      api.users.delete(this.user.id);
      this.$emit("refresh");
    },
    persist() {
      if (this.isNew) {
        api.users
          .create({
            username: this.user.username,
            email: this.user.email,
            password: this.user.password,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.users
          .edit(this.user.id, {
            id: this.user.id,
            username: this.user.username,
            email: this.user.email,
            password: this.user.password,
          })
          .then(() => this.$emit("refresh"));
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
