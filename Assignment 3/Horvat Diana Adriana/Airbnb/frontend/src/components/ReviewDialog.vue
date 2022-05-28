<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          Write a review
        </v-toolbar>
        <v-form>
          <textarea
              v-model="item.review"
              style="width: 600px; height: 200px"
          ></textarea>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            Post
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
const auth_user = JSON.parse(localStorage.getItem("user"));

import api from "@/api";

  export default {
  name: "ReviewDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
    methods: {
      persist() {
        api.items.postReview({
          userId: auth_user.id,
          content: this.item.review,
          accommodationId: this.item.id,
        })
            .then(() => this.$emit("refresh"));
      },
    },

}
</script>

<style scoped>

</style>