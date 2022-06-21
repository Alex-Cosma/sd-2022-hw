<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create movie" : "Edit movie" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="movie.title" label="Title" />
          <v-text-field v-model="movie.description" label="Description" />
          <v-text-field v-model="movie.year" label="Year" />
          <v-text-field v-model="movie.genre" label="Genre" />
          <v-text-field v-model="movie.rating" label="Rating" />
          <v-text-field
            v-model="movie.numberOfReviews"
            label="Number of reviews"
          />
          <v-text-field v-model="movie.imagePath" label="Image Path" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
// import SockJS from "sockjs-client";
// import Stomp from "webstomp-client";

export default {
  name: "MovieDialog",
  data() {
    return {
      send_message: null,
      connected: false,
    };
  },
  props: {
    movie: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.movies
          .create({
            title: this.movie.title,
            year: this.movie.year,
            description: this.movie.description,
            genre: this.movie.genre,
            rating: this.movie.rating,
            numberOfReviews: this.movie.numberOfReviews,
            imagePath: this.movie.imagePath,
          })
          .then(() => this.$emit("refresh"));
        this.send();
      } else {
        api.movies
          .update({
            id: this.movie.id,
            title: this.movie.title,
            year: this.movie.year,
            description: this.movie.description,
            genre: this.movie.genre,
            rating: this.movie.rating,
            numberOfReviews: this.movie.numberOfReviews,
            imagePath: this.movie.imagePath,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    // connect() {
    //   this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
    //   this.stompClient = Stomp.over(this.socket);
    //   this.stompClient.connect(
    //     {},
    //     (frame) => {
    //       this.connected = true;
    //       console.log(frame);
    //       this.stompClient.subscribe("/topic/greetings", (tick) => {
    //         console.log(tick);
    //       });
    //     },
    //     (error) => {
    //       console.log(error);
    //       this.connected = false;
    //     }
    //   );
    // },
    // send() {
    //   console.log("Send message:" + this.movie.title);
    //   if (this.stompClient && this.stompClient.connected) {
    //     const msg = { message: this.movie.title };
    //     console.log(JSON.stringify(msg));
    //     this.stompClient.send("/app/hello", JSON.stringify(msg), {});
    //   }
    // },
  },
  created() {
    //this.connect();
  },
  computed: {
    isNew: function () {
      return !this.movie.id;
    },
  },
};
</script>

<style scoped></style>
