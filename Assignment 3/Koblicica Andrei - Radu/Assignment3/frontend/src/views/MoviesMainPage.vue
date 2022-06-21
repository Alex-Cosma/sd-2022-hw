<template>
  <v-card>
    <v-card-title>
      Movies
      <v-spacer></v-spacer>
      <v-text-field
        label="Search"
        v-model="filterText"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="searchMovies">Search</v-btn>
      <v-btn @click="refreshList">Clear filter</v-btn>
      <v-btn @click="goToWatchlist">My Watchlist</v-btn>
    </v-card-title>
    <v-data-table :headers="headers" :items="movies" @click:row="selectMovie">

    </v-data-table>
  </v-card>
</template>

<script>
import api from "../api";
import router from "../router";
//import SockJS from "sockjs-client";
//import Stomp from "webstomp-client";


export default {
  name: "MoviesMainPage",
  data() {
    return {
      movies: [],
      received_messages: [],
      connected: false,
      filterText: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: true,
          value: "title",
        },
        { text: "Genre", value: "genre" },
        { text: "Year", sortable: true, value: "year" },
        { text: "Rating", sortable: true, value: "rating" },
      ],
    };
  },
  methods: {
    async searchMovies() {
      this.movies = await api.movies.filteredMovies(this.filterText );
    },

    selectMovie(movie) {
      router.push("/movies/id="+movie.id);
    },
    goToWatchlist(){
      router.push("/movies/watchlist");
    },
    async refreshList() {
      this.movies = await api.movies.allMovies();
    },
    // connect() {
    //   this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
    //   this.stompClient = Stomp.over(this.socket);
    //   this.stompClient.connect(
    //       {},
    //       frame => {
    //         this.connected = true;
    //         console.log(frame);
    //         this.stompClient.subscribe("/topic/greetings", tick => {
    //           console.log(tick);
    //           this.received_messages.push(JSON.parse(tick.body).content);
    //           alert(JSON.parse(tick.body).content)
    //         });
    //       },
    //       error => {
    //         console.log(error);
    //         this.connected = false;
    //       }
    //   );
    // },
  },
  created() {
    this.refreshList();
    //this.connect();
  },
};
</script>

<style scoped></style>
