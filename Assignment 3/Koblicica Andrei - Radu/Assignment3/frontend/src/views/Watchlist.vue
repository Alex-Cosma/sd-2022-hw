<template>
  <v-container>
    <v-col cols="12">
      <v-row>
        <h1>Watchlist</h1>
      </v-row>
      <br>
      <br>
      <v-flex v-for="movie in movies" :key="movie.id" >
        <v-card @click="selectMovie(movie)"
        >

          <div class="d-flex flex-no-wrap">


                <img v-bind:src="movie.imagePath" :alt="movie.name" height="200px">


            <div>

              <v-card-title class="headline">
                <div class="truncate">{{ movie.title }}</div>
              </v-card-title>


              <v-card-subtitle v-text="'Year: '+movie.year" class="font-weight-bold"></v-card-subtitle>
              <v-card-subtitle v-text="'Genre: '+movie.genre" class="font-weight-bold"></v-card-subtitle>
              <v-card-subtitle v-text="'Rating: '+movie.rating" class="font-weight-bold"></v-card-subtitle>


            </div>


          </div>
        </v-card>
        <br>
      </v-flex>
    </v-col>

  </v-container>
</template>

<script>
import api from "@/api";
import router from "@/router";

export default {
  name: "Watchlist",
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user")),
      movies: [],
    };
  },
  methods: {
    selectMovie(movie) {
      router.push("/movies/id="+movie.id)
    },
    async refreshPage() {
      this.movies = await api.users.getWatchlist(this.user.id);
      this.user=JSON.parse(localStorage.getItem("user"));
    },
  },
  created() {
    this.refreshPage();
  },
};
</script>

<style scoped></style>
