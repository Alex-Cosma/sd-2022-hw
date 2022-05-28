<template>
  <v-card>
    <h1 style="text-align: center">{{ movie.title }}</h1>
    <v-row>
      <v-col>
        <v-img :src="movie.backdropLink" height="540px" width="960px"></v-img>
      </v-col>
      <v-col>
        <v-row>
          <v-rating
            color="warning"
            empty-icon="$mdiStarOutline"
            length="1"
            size="60"
            value="1"
          ></v-rating>
          <h2 v-text="movieRating" style="padding-top: 1em"></h2>
        </v-row>
        <v-card v-text="movie.description"></v-card>
        <v-btn
          v-if="!isInWatchlist"
          depressed
          class="white--text"
          color="#311B92"
          @click="addWatchlist"
          >Add to Watchlist!</v-btn
        >
      </v-col>
    </v-row>
    <v-chip-group>
      <v-flex>
        <v-chip
          class="ma-2"
          color="cyan"
          text-color="white"
          v-for="genre in movie.genres"
          v-bind="{ [`xs${genre.flex}`]: true }"
          :key="genre.name"
        >
          {{ genre.name }}
        </v-chip>
      </v-flex>
    </v-chip-group>
    <v-flex>
      <v-card v-if="!alreadyReviewed">
        <h2>Leave a review!</h2>
        <v-rating v-model="rating" hover></v-rating>
        <v-textarea
          solo
          name="input-7-4"
          label="Write a comment"
          no-resize
          v-model="message"
        ></v-textarea>
        <v-card-actions>
          <v-btn depressed color="primary" @click="addReview"> Submit </v-btn>
        </v-card-actions>
      </v-card>
      <v-card v-if="alreadyReviewed">
        <h3>You already reviewed this movie.</h3>
      </v-card>
    </v-flex>

    <v-card>
      <h1>Reviews</h1>
      <v-card-subtitle v-if="reviews.length === 0"
        >Be the first to review!</v-card-subtitle
      >
      <v-container fluid>
        <v-row dense>
          <v-col v-for="review in reviews" :key="review.id" cols="12">
            <v-card>
              <div class="d-flex flex-no-wrap justify-space-between">
                <div>
                  <v-card-title>
                    <v-rating v-model="review.rating"></v-rating>
                  </v-card-title>
                  <v-card-subtitle v-text="review.username"></v-card-subtitle>
                  <h3 v-text="review.description"></h3>
                </div>
              </div>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "Movie",
  data() {
    return {
      movie: {},
      rating: 3,
      reviews: {},
      movieRating: "0/5",
      newReview: {},
      message: "",
      alreadyReviewed: false,
      user: {},
      isInWatchlist: false,
    };
  },
  methods: {
    async refresh() {
      this.movie = await api.movies.findById(this.$route.params.id);
      this.reviews = await api.reviews.findForId(this.$route.params.id);
      this.user = JSON.parse(localStorage.getItem("user"));
      this.movieRating = this.calculateRating();
      await this.checkIfReviewed();
      this.isInWatchlist = await api.watchlists.isInWatchlist(
        this.user.id,
        this.$route.params.id
      );
    },
    async addReview() {
      this.newReview.rating = this.rating;
      this.newReview.description = this.message;
      this.newReview.movieId = this.$route.params.id;
      this.newReview.userId = this.user.id;
      this.newReview.username = this.user.username;

      await api.reviews.save(this.newReview);
      await this.refresh();
    },
    async addWatchlist() {
      let watchlist = {};
      watchlist.userId = this.user.id;
      watchlist.movieId = this.movie.id;
      await api.watchlists.save(watchlist);
      await this.refresh();
    },
    calculateRating() {
      if (this.reviews.length === 0) {
        return "No reviews yet";
      }
      let sum = 0;
      for (let i = 0; i < this.reviews.length; i++) {
        sum += this.reviews[i].rating;
      }
      sum /= this.reviews.length;

      return sum + "/5";
    },
    async checkIfReviewed() {
      for (let i = 0; i < this.reviews.length; i++) {
        if (this.reviews[i].userId === this.user.id) {
          this.alreadyReviewed = true;
          break;
        }
      }
    },
  },
  created() {
    this.refresh();
  },
};
</script>

<style scoped></style>
