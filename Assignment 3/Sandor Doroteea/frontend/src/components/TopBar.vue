<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-toolbar-title>Music Player Library</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="goToTracks">Tracks</v-btn>
      <v-btn @click="goToPlaylists">Playlists</v-btn>
      <v-btn @click="goToTracksApi">Tracks Api</v-btn>
      <v-btn @click="goToComments">Comments</v-btn>
      <v-btn @click="goToGreetings">Greetings</v-btn>
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
    goToTracks() {
      this.$router.push("/tracks");
    },
    goToTracksApi() {
      this.$router.push("/tracksApi");
    },
    goToPlaylists() {
      this.$router.push("/playlists");
    },
    goToComments() {
      this.$router.push("/comments");
    },
    goToGreetings(){
      this.$router.push("/topic/greetings");
    }
  },
};
</script>

<style scoped></style>
