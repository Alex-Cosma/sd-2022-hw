<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>
      <v-btn v-if="isAdmin" icon @click="goToUsers">
        <v-icon>perm_identity</v-icon>
      </v-btn>

      <v-btn v-if="isAdmin" icon @click="goToBooks">
        <v-icon>book</v-icon>
      </v-btn>

      <v-toolbar-title>Book store</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>
    </v-toolbar>
  </v-card>
</template>

<script>
import router from "../router";
import { auth as store } from "../store/auth.module";

export default {
  name: "TopBar",
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
    goToBooks() {
      router.push("/books");
    },
    goToUsers() {
      router.push("/users");
    },
  },
  computed: {
    isAdmin() {
      return store.getters.isAdmin(store.state);
    },
  },
};
</script>

<style scoped></style>
