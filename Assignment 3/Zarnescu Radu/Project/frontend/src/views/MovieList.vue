<template>
  <v-flex xs12 sm12>
    <v-card>
      <v-container fluid grid-list-md>
        <v-layout row wrap>
          <v-flex
            v-for="movie in movies"
            v-bind="{ [`xs${movie.flex}`]: true }"
            :key="movie.id"
          >
            <a v-bind:href="'./movies/' + movie.id">
              <v-hover v-slot="{ hover }">
                <v-card
                  height="300px"
                  width="200px"
                  @click.prevent="changeView(movie.id)"
                >
                  <v-expand-transition>
                    <div
                      v-if="hover"
                      class="d-flex transition-fast-in-fast-out cyan darken-2"
                      style="height: 100%; text-align: center"
                    >
                      <p>{{ movie.title }}</p>
                      <div class="pa-4">
                        <v-chip-group active-class="primary--text" column>
                          <v-chip
                            v-for="genre in movie.genres"
                            v-bind="{ [`xs${genre.flex}`]: true }"
                            :key="genre.name"
                          >
                            {{ genre.name }}
                          </v-chip>
                        </v-chip-group>
                      </div>
                    </div>
                  </v-expand-transition>
                  <v-img
                    :src="movie.imageLink"
                    height="300px"
                    width="200px"
                  ></v-img>
                </v-card>
              </v-hover>
            </a>
          </v-flex>
        </v-layout>
      </v-container>
    </v-card>
  </v-flex>
</template>

<script>
import api from "../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "MovieList",
  data() {
    return {
      movies: [],
      stompClient: null,
      socket: null,
    };
  },
  methods: {
    async refresh() {
      this.movies = await api.movies.allMovies();
    },
    async changeView(id) {
      await this.$router.push("/movies/" + id);
    },
    connect() {
      this.socket = new SockJS("/websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect({}, function (frame) {
        console.log("Connected: " + frame);
        this.stompClient.subscribe("/topic/messages", function (message) {
          this.showMessage(JSON.parse(message.body).content);
        });
      });
    },
    showMessage(message) {
      console.log(message);
    },
  },
  created() {
    this.refresh();
    // this.connect();
  },
};
</script>

<style scoped></style>
