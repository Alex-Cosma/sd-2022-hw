<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Edit user </v-toolbar>
        <v-form>
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field v-model="user.name" label="Username" />
          <v-text-field
            v-model="user.password"
            label="Password"
            type="password"
            v-if="false"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Save </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialogEdit",
  props: {
    user: Object,
    opened: Boolean,
    edit: Boolean,
  },
  data() {
    return {
      chosenRole: "",
    };
  },

  methods: {
    persist() {
      api.users
        .edit({
          id: this.user.id,
          email: this.user.email,
          name: this.user.name,
          password: this.user.password,
          roles: this.user.roles,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
