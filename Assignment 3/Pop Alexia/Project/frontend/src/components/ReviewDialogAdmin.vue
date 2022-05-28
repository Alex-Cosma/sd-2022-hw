<template>
  <v-app>
    <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
    >
      <template>
        <v-card>
          <v-toolbar color="black" dark>
            {{ "Delete Review" }}
          </v-toolbar>
          <v-card-text> Delete this review? </v-card-text>
          <v-card-actions>
            <v-btn @click="close">Close</v-btn>
            <v-btn @click="deleteR">Delete</v-btn>
          </v-card-actions>
        </v-card>
      </template>
    </v-dialog>
  </v-app>
</template>

<script>
import api from "../api";

export default {
  data() {
    return {
      show: false,
    };
  },
  name: "ReviewDialogAdmin",
  props: {
    review: Object,
    opened: Boolean,
  },
  methods: {
    deleteR() {
      api.reviews.deleteReview(this.review.id);
      this.$emit("refresh");
    },

    close() {
      this.$emit("refresh");
    },
  },
};
</script>

<style scoped></style>
