<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Name" />
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field v-model="user.password" label="Email" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">Edit user</v-btn>
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
      api.users
        .edit({
          id: this.user.id,
          username: this.user.username,
          email: this.user.email,
          password: this.user.password,
          role: this.user.role,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
