<template>
  <v-card>
    <v-card-title>
      <v-spacer>
        <v-btn color="blue" @click="toHomePage()">Back to home page</v-btn>
      </v-spacer>
    </v-card-title>
    <v-row class="fill-height" align="center" justify="center">
      <template v-for="(game, i) in games">
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
                          >Add to wish list</v-btn
                        >
                        <v-btn x-small @click="downloadGame()"
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
  name: "GameList",
  components: {},
  data() {
    return {
      games: [],
      wishListGames: [],
      newWishList: Object,
      addedToWishList: false,
    };
  },
  methods: {
    toHomePage() {
      router.push("/homePage");
    },
    viewInfo(game) {
      console.log(game.title);
      router.push("/games/" + game.id);
    },
    addOrRemove(game) {
      let newWishList = {
        game: game,
        user: this.$store.getters["auth/getUser"],
      };
      newWishList.user.roles = this.$store.getters["auth/getRole"];
      api.games.create(newWishList);
    },
    downloadGame() {
      alert(
        "An e-mail has been sended to " +
          this.$store.getters["auth/getUser"].email
      );
    },
    async refreshList() {
      this.games = await api.games.allGames();
    },
  },
  computed: {
    isAdded: function () {
      return this.addedToWishList;
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
