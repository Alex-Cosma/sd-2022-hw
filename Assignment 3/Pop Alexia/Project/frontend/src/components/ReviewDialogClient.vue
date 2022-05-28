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
            {{ isNew ? "Create review" : "Edit review" }}
            <v-alert v-if="showAlert" type="error" value="errors">{{
              errors
            }}</v-alert>
          </v-toolbar>
          <v-form>
            <v-text-field v-model="review.text" label="Text" />
            <select v-model="selected">
              <option disabled value="">Please select one</option>
              <option>Excellent</option>
              <option>Great</option>
              <option>Average</option>
              <option>Poor</option>
              <option></option>
            </select>
          </v-form>
          <v-card-actions>
            <v-btn @click="persist">
              {{ isNew ? "Create" : "Save" }}
            </v-btn>
            <v-btn v-if="!isNew" @click="deleteR">Delete</v-btn>
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
      showAlert: false,
      errors: [],
      loggedUser: {},
      selected: "",
    };
  },
  name: "ReviewDialog",
  props: {
    review: Object,
    opened: Boolean,
  },
  methods: {
    deleteR() {
      api.reviews.deleteReview(this.review.id);
      this.$emit("refresh");
    },

    persist: function () {
      this.loggedUser = JSON.parse(localStorage.getItem("user"));
      this.showAlert = false;
      this.errors = [];
      if (this.isNew) {
        api.reviews
          .createReview({
            text: this.review.text,
            rating: this.selected,
            reviewer: this.loggedUser.email,
          })
          .catch((err) => {
            this.showAlert = true;
            this.errors = err.response.data.message;
          })
          .then(() => {
            if (this.errors.length === 0) {
              this.$emit("refresh");
              this.showAlert = false;
            }
          });
      } else {
        api.reviews
          .editReview(this.review.id, {
            id: this.review.id,
            text: this.review.text,
            rating: this.selected,
          })
          .catch((err2) => {
            this.showAlert = true;
            this.errors = err2.response.data.message;
          })
          .then(() => {
            if (this.errors.length === 0) {
              this.$emit("refresh");
              this.showAlert = false;
            }
          });
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.review.id;
    },
  },
};
</script>

<style scoped></style>
