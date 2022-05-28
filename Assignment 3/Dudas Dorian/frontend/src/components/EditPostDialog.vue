<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Edit post </v-toolbar>
        <v-form v-if="isAdmin">
          <v-text-field v-if="post" v-model="post.topicTitle" label="Topic" />
          <v-text-field
            v-if="post"
            v-model="post.posterUsername"
            label="User"
          />
          <v-text-field v-if="post" v-model="post.content" label="Content" />
        </v-form>
        <v-card-actions>
          <v-btn v-if="isAdmin" @click="persist"> Save </v-btn>
          <v-btn v-if="isAdmin" @click="deletePost">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import { auth as store } from "../store/auth.module";

export default {
  name: "EditPostDialog",
  props: {
    post: Object,
    opened: Boolean,
  },
  methods: {
    deletePost() {
      api.posts.delete(this.post).then(
        () => this.$emit("refresh"),
        (error) => alert(error.response.data.message)
      );
    },
    persist() {
      api.posts
        .edit({
          id: this.post.id,
          topicTitle: this.post.topicTitle,
          posterUsername: this.post.posterUsername,
          content: this.post.content,
          creationDate: this.post.creationDate,
        })
        .then(
          () => this.$emit("refresh"),
          (error) => alert(error.response.data.message)
        );
    },
  },
  computed: {
    isAdmin: function () {
      return store.getters.isAdmin(store.state);
    },
  },
};
</script>

<style scoped></style>
