<template>
  <v-card>
    <v-card-title>
      <v-spacer>
        <v-btn color="blue" @click="toHomePage()">Back to home page</v-btn>
      </v-spacer>
    </v-card-title>
    <v-row class="fill-height" align="center" justify="center">
      <template v-for="(game, i) in wishListGames">
        <v-col :key="i" cols="12" md="4">
          <v-hover v-slot="{ hover }">
            <v-card :elevation="hover ? 12 : 2" :class="{ 'on-hover': hover }">
              <v-img :src="game.thumbnail" aspect-ratio="2">
                <v-card-title class="text-h6 white&#45;&#45;text">
                  <v-row
                    class="fill-height flex-column"
                    justify="space-between"
                  >
                    <v-expand-transition>
                      <div v-if="hover">
                        <v-btn x-small @click="viewInfo(game)">View info</v-btn>
                        <v-btn x-small @click="addOrRemove(game)"
                          >Remove from wishlist</v-btn
                        >
                        <v-btn x-small @click="downloadGame(game)"
                          >Download the game</v-btn
                        >
                      </div>
                    </v-expand-transition>
                  </v-row>
                </v-card-title>
              </v-img>
            </v-card>
          </v-hover>
        </v-col>
      </template>
    </v-row>
  </v-card>
</template>
<script>
import api from "../api";
import router from "../router";

export default {
  name: "WishList",
  components: {},
  data() {
    return {
      wishListGames: [],
    };
  },
  methods: {
    viewInfo(game){
      router.push("/games/" + game.id);
    },
    toHomePage() {
      router.push("/homePage");
    },
    addOrRemove(game) {
      api.wishlist.deleteGame(this.$store.getters["auth/getUser"], game);
    },
    async refreshList() {
      this.wishListGames = await api.wishlist.allGamesFromWishlist(
        this.$store.getters["auth/getUser"]
      );
    },
  },
  computed: {
    isAdded: function () {
      return false;
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
