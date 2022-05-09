<template>
  <v-dialog max-width="500px" :value="opened">
    <v-card>
      <v-card-title class="text-h5"
        >Are you sure you want to delete this user?</v-card-title
      >
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
        <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
        <v-spacer></v-spacer>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
/*import api from "../api";*/

import api from "@/api";

export default {
  name: "DeleteUserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  selectedItem: {},
  methods: {
    deleteItemConfirm() {
      api.users
        .deleteUser({
          id: this.user.id,
          username: this.user.username,
          email: this.user.email,
          password: this.user.password,
          role: this.user.role,
        })
        .then(() => this.$emit("refresh"));
    },
    closeDelete() {
      this.$emit("refresh");
    },
  },
};
</script>

<style scoped></style>
