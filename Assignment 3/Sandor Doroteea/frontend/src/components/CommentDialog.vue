<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create comment" : "Edit comment" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="comment.content" label="Content" />
          <!--          <v-text-field v-model="comment.trackId" label="track_id" />-->
          <!--          <v-text-field v-model="comment.userId" label="user_id" />-->
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create comment" : "Save comment" }}
          </v-btn>
          <v-btn @click="deleteComment">Delete Comment</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "CommentDialog",
  props: {
    trackId: Number,
    comment: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.comments
          .create({
            content: this.comment.content,
            trackId: this.trackId,
            userId: this.$store.getters["auth/getUserID"],
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.comments
          .edit({
            id: this.comment.id,
            content: this.comment.content,
            trackId: this.comment.trackId,
            userId: this.comment.userId,
          })
          .then(() => this.$emit("refresh"));
      }
    },

    deleteComment() {
      api.comments
        .delete({
          id: this.comment.id,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.comment.id && !this.comment.trackId;
    },
  },
};
</script>

<style scoped></style>
