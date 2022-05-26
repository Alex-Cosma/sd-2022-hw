<template>
  <v-flex xs12 sm12>
    <v-card>
      <v-container fluid grid-list-md>
        <v-layout row wrap>
          <v-flex
            v-for="movie in movies"
            v-bind="{ [`xs${movie.flex}`]: true }"
            :key="movie.title"
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
            <v-btn @click="remove(movie.id)">Remove</v-btn>
          </v-flex>
        </v-layout>
      </v-container>
    </v-card>
  </v-flex>
</template>

<script>
import api from "../api";

export default {
  name: "Watchlist",
  data() {
    return {
      movies: [],
      user: {},
    };
  },
  methods: {
    async refresh() {
      this.user = JSON.parse(localStorage.getItem("user"));
      this.movies = await api.watchlists.findByUserId(this.user.id);
    },
    async remove(id) {
      await api.watchlists.remove({ movieId: id, userId: this.user.id });
      await this.refresh();
    },
  },
  created() {
    this.refresh();
  },
};
</script>

<style scoped></style>
