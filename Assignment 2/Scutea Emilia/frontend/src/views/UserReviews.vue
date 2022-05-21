<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="userreviews"
      :search="search"
    ></v-data-table>
  </v-card>
</template>

<script>
import api from "../api";
const user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "UserReviews.vue",
  data() {
    return {
      userreviews: [],
      search: "",
      headers: [
        { text: "Text", value: "text" },
        { text: "Rating", value: "rating" },
      ],
    };
  },
  methods: {
    async refreshList() {
      this.userreviews = await api.userreview.getReviewsForUser(user.id);
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
