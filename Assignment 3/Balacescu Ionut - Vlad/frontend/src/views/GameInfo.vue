<template>
  <v-card>
    <v-card-title>
      <v-spacer>
        <v-btn color="blue" @click="toHomePage()">Back to game list</v-btn>
        <v-img :src="game.thumbnail"></v-img>

        <v-card-title>{{ game.title }}</v-card-title>

        <v-card-text>
          <v-row align="center" class="mx-0">
            <v-rating
              :value="this.finalReview"
              color="amber"
              dense
              half-increments
              readonly
              size="14"
            ></v-rating>

            <div class="grey--text ms-4">
              {{ this.finalReview }}({{ this.finalReviews.length }})
            </div>
          </v-row>

          <div class="my-4 text-subtitle-1">{{ game.genre }}</div>

          <div>
            {{ game.description }}
          </div>
        </v-card-text>

        <v-divider class="mx-4"></v-divider>
        <div class="text-center mt-12">
          <v-form ref="ra"
            ><v-rating
              v-model="rating"
              clearable
              color="yellow darken-3"
              background-color="grey darken-1"
              empty-icon="$ratingFull"
              half-increments
              hover
              large
            ></v-rating
          ></v-form>
        </div>
        <div>
          <v-form ref="re"
            ><v-text-field
              label="Leave a review"
              v-model="description"
              clearable
              type="text"
            ></v-text-field>
            <v-btn small @click="addReviewToGame()">Ok</v-btn></v-form
          >
        </div>
        <v-divider class="mx-4"></v-divider>
        <v-row class="fill-height" align="center" justify="center">
          <template v-for="(review, i) in finalReviews">
            <v-col :key="i" cols="1" md="12">
              <v-card :elevation="hover ? 12 : 2">
                <v-card-title class="text-h6 white&#45;&#45;text">
                  <v-row
                    class="fill-height flex-column"
                    justify="space-between"
                  >
                    <v-rating
                      :value="review.rating"
                      color="amber"
                      dense
                      half-increments
                      readonly
                      size="14"
                    ></v-rating>
                    <div class="my-4 text-subtitle-1">{{ review.reviewer }}</div>

                    <div>
                      {{ review.text }}
                    </div>
                  </v-row>
                </v-card-title>
              </v-card>
            </v-col>
          </template>
        </v-row>
      </v-spacer>
    </v-card-title>
  </v-card>
</template>
<script>
import router from "../router";
import api from "../api";

export default {
  name: "GameInfo",
  components: {},
  data() {
    return {
      finalReview: 0,
      game: Object,
      review: Object,
      finalReviews: [],
    };
  },
  methods: {
    toHomePage() {
      router.push("/games");
    },
    addReviewToGame() {
      this.review = {
        rating: this.rating,
        text: this.description,
        game: this.game,
        user: this.$store.getters["auth/getUser"],
      };
      console.log(this.review);
      this.review.user.roles = this.$store.getters["auth/getRole"];
      api.reviews.addReview(this.review);
      this.resetAll();
      this.refreshList();
    },
    resetAll() {
      this.$refs.re.reset();
    },
    async refreshList() {
      this.game = await api.games.gameByID(this.$route.params.id);
      this.finalReviews = await api.reviews.getReviews(this.game.id);
      if (this.finalReviews.length === 0) {
        this.finalReview = 0;
      } else {
        let sum = 0;
        for (let i = 0; i < this.finalReviews.length; i++) {
          sum = sum + this.finalReviews[i].rating;
        }
        let aux = sum / this.finalReviews.length;
        this.finalReview = aux.toFixed(1);
      }
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
