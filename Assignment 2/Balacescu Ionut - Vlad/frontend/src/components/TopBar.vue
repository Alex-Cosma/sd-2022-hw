<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>

      <v-toolbar-title>Lab example</v-toolbar-title>

      <v-spacer></v-spacer>
      <template v-slot:extension v-if="this.$store.getters['auth/isAdmin']">
        <v-tabs align-with-title>
          <v-tab @click="changeToUsers">To users</v-tab>
          <v-tab @click ="changeToBooks">To books</v-tab>
        </v-tabs>
      </template>
      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>
    </v-toolbar>
  </v-card>
</template>

<script>
import router from "../router";

export default {
  name: "TopBar",
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
    changeToBooks(){
      router.push("/items");
    },
    changeToUsers(){
      router.push("/users");
    },
    isAdmin(){

    }
  },
};
</script>

<style scoped>
  .invisible{visibility: hidden;}
</style>
