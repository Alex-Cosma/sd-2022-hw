<template>
  <v-card>
    <v-card-title>
      Reviews
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
        @input="searchAllBy"
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="reviews"
      @click:row="onClick"
      @refresh="refreshList"
    ></v-data-table>
    <ReviewDialogAdmin
      :opened="reviewDialogVisible"
      :review="selectedReview"
      @refresh="refreshList"
    ></ReviewDialogAdmin>
  </v-card>
</template>

<script>
import api from "../api";
import ReviewDialogAdmin from "@/components/ReviewDialogAdmin";

export default {
  name: "ReviewListAdmin",
  components: { ReviewDialogAdmin },
  data() {
    return {
      reviews: [],
      search: "",
      headers: [
        {
          text: "Text",
          align: "start",
          sortable: false,
          value: "text",
        },
        { text: "Rating", value: "rating" },
        { text: "Reviewer", value: "reviewer" },
      ],
      reviewDialogVisible: false,
      selectedReview: {},
    };
  },
  methods: {
    onClick(review) {
      this.selectedReview = review;
      this.reviewDialogVisible = true;
    },
    async refreshList() {
      this.reviewDialogVisible = false;
      this.selectedReview = {};
      this.reviews = await api.reviews.findAll();
    },
    async searchAllBy() {
      if (this.search !== "") {
        this.reviews = await api.reviews.filterAll(this.search);
      } else {
        this.search = "";
        this.reviews = await api.reviews.findAll();
      }
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
