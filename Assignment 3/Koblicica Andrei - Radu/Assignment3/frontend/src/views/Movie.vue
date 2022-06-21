<template>
  <v-container>
    <v-row >
      <v-col cols="7">
        <h1 >{{this.movie.title}}</h1>
      </v-col>
      <v-col cols="2">
        <v-btn @click="addToWatchlist">Add to Watchlist</v-btn>
      </v-col>
      <v-col cols="2">
        <v-btn @click="removeFromWatchlist">Remove from Watchlist</v-btn>
      </v-col>

    </v-row>

    <v-row>
      <v-col cols="3">
        <v-card height="100%">
          <img v-bind:src="this.movie.imagePath" :alt="movie.name" height="400px">
        </v-card>
      </v-col>
      <v-col cols="4">
        <v-card
            height="100%"
        >
          <p> {{this.movie.description}}</p>
          <p><b>Genre:</b> {{this.movie.genre}}</p>
          <p><b>Year: </b>{{this.movie.year}}</p>
          <p><b>Rating:</b> {{this.movie.rating}}</p>
          <p><b>Number of reviews:</b> {{this.movie.numberOfReviews}}</p>
        </v-card>
      </v-col>
      <v-col cols="5">
        <v-card class="d-flex flex-column" height="100%">
          <b>Add review:</b>
          <v-spacer/>
          <v-textarea
              v-model="content"
              filled
              name="input-7-4"
              height="200px"
          ></v-textarea>
          <b>Rate:</b>
          <v-rating
              v-model="rating"
              color="yellow darken-3"
              background-color="grey darken-1"
              empty-icon="$ratingFull"
              hover
              medium
              length="10"
          ></v-rating>
          <p></p>
          <v-btn @click="addReview">Submit review</v-btn>
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="2">
        <h3>User reviews:</h3>
      </v-col>
    </v-row>
    <v-row>
      <v-flex>
        <v-card class="elevation-0 transparent card-container">
          <v-flex d-flex>
            <v-list three-line>
              <template v-for="(review, index) in reviews">

                <v-divider
                    :key="'A'+index"
                    :inset="review.inset"
                ></v-divider>

                <v-list-item
                    :key="'B'+index">

                  <v-list-item-content>
                    <v-list-item-title v-html="review.username"></v-list-item-title>
                    <v-rating
                        readonly
                        :value="review.rating"
                        color="yellow darken-3"
                        background-color="grey darken-1"
                        empty-icon="$ratingFull"
                        hover
                        small
                        length="10"
                    ></v-rating>
                    <v-list-item-subtitle v-html="review.content"></v-list-item-subtitle>


                  </v-list-item-content>
                </v-list-item>
              </template>
            </v-list>
          </v-flex>
        </v-card>
      </v-flex>
    </v-row>

  </v-container>




</template>

<script>
import api from "@/api";
//const user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "Movie.vue",
  data() {
    return {
      user : JSON.parse(localStorage.getItem("user")),
      movie: [],
      reviews: [],
      rating: 0,
      content: "",
    };
  },
  methods: {
    async refreshPage() {
      this.movie = await api.movies.movieById(this.$route.params.id);
      this.reviews= await api.movies.reviewsByMovieId(this.$route.params.id);
      this.user = JSON.parse(localStorage.getItem("user"));
    },
    addToWatchlist(){
      api.users.addToWatchlist(this.user.id,this.$route.params.id);
    },
    removeFromWatchlist(){
      api.users.removeFromWatchlist(this.user.id,this.$route.params.id);
    },
    addReview(){
      api.movies.addReview({
        userId: this.user.id,
        username: this.user.username,
        movieId: this.movie.id,
        rating: this.rating,
        content: this.content,
      }).then(()=>this.refreshPage());
    },
  },
  created() {
    this.refreshPage();
  },
};
</script>

<style scoped></style>
