<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Add Movie" : "Edit Movie" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="movie.title" label="Title" />
          <v-text-field v-model="movie.description" label="Description" />
          <v-text-field v-model="movie.imageLink" label="Poster Path" />
          <v-text-field v-model="movie.backdropLink" label="Backdrop Path" />
          <v-text-field v-model="movie.genresString" label="Genres" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Add" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteMovie"> Delete </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "MovieDialog",
  props: {
    movie: Object,
    opened: Boolean,
  },
  methods: {
    data() {
      return {
        stompClient: null,
        socket: null,
      };
    },
    persist() {
      if (this.isNew) {
        api.movies
          .create({
            title: this.movie.title,
            description: this.movie.description,
            imageLink: this.movie.imageLink,
            backdropLink: this.movie.backdropLink,
            genresString: this.movie.genresString,
          })
          .then(() => this.$emit("refresh"));
        // this.sendMessage(this.movie.title);
      } else {
        api.movies
          .edit({
            id: this.movie.id,
            title: this.movie.title,
            description: this.movie.description,
            imageLink: this.movie.imageLink,
            backdropLink: this.movie.backdropLink,
            genresString: this.movie.genresString,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteMovie() {
      api.movies.delete(this.movie.id).then(() => this.$emit("refresh"));
    },
    connect() {
      this.socket = new SockJS("/websocket");
      this.stompClient = Stomp.over(this.socket);
    },
    sendMessage(message) {
      console.log("Hello");
      console.log(this.connection);
      this.stompClient.send("/ws/message", JSON.stringify(message));
    },
  },
  computed: {
    isNew: function () {
      return !this.movie.id;
    },
  },
  // created() {
  //   this.connect();
  // },
};
</script>

<style scoped></style>
