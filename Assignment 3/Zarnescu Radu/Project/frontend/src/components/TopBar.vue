<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-menu offset-y>
        <template v-slot:activator="{ attrs, on }">
          <v-app-bar-nav-icon
            v-bind:class="attrs"
            v-on="on"
          ></v-app-bar-nav-icon>
        </template>
        <v-list>
          <v-list-item link @click="changeRoute('/movies')">
            <v-list-item-title>Movies</v-list-item-title>
          </v-list-item>
          <v-list-item link @click="changeRoute('/watchlist')">
            <v-list-item-title>Watchlist</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>

      <v-toolbar-title v-text="title"></v-toolbar-title>

      <v-spacer></v-spacer>

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
  data() {
    return {
      title: "Movies",
    };
  },
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
    async changeRoute(route) {
      await router.push(".." + route);
      if (route === "/movies") {
        this.title = "Movies";
      } else {
        this.title = "Watchlist";
      }
    },
  },
};
</script>

<style scoped></style>
