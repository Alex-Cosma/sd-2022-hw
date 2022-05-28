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
      <v-btn @click="addReview">Create Review</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="reviews"
      @click:row="editReview"
      @refresh="refreshList"
    ></v-data-table>
    <ReviewDialog
      :opened="reviewDialogVisible"
      :review="selectedReview"
      @refresh="refreshList"
    ></ReviewDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ReviewDialog from "@/components/ReviewDialogClient";

export default {
  name: "ReviewList",
  components: { ReviewDialog },
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
    editReview(review) {
      let loggedUser = JSON.parse(localStorage.getItem("user"));
      if (loggedUser.email === review.reviewer) {
        this.selectedReview = review;
        this.reviewDialogVisible = true;
      }
    },
    addReview() {
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
