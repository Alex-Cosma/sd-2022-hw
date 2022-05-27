<template>
  <v-card>
    <v-card-title>
      <v-spacer>
        <v-btn color="blue" @click="toAllGames()">Explore</v-btn>
        <v-btn color="blue" @click="toWishList()">Wishlist</v-btn>
      </v-spacer>
      <!--      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search game"
        single-line
        hide-details
      ></v-text-field>-->
    </v-card-title>
    <v-carousel cycle hide-delimiters>
      <v-carousel-item
        v-for="(game, i) in randomGames"
        :key="i"
        :src="game.thumbnail"
        reverse-transition="fade-transition"
        transition="fade-transition"
        style="width: 900px; height: auto; margin: auto"
      ></v-carousel-item>
    </v-carousel>
    <!--    <v-data-table :headers="headers" :items="games" :search="search">
    </v-data-table>-->
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
      randomGames: [],
      games: [],
    };
  },
  methods: {
    toAllGames() {
      router.push("/games");
    },
    toWishList() {
      router.push("/wishlist/" + this.$store.getters["auth/getId"]);
    },
    async refreshList() {
      this.games = await api.games.allGames();
      let i = 0;
      while (i < 9) {
        let a = Math.floor(Math.random() * 370);
        this.randomGames.push(this.games[a]);
        i++;
      }
    },
  },

  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
