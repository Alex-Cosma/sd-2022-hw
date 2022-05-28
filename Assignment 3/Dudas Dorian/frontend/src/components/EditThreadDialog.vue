<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar v-if="isAdmin" color="primary" dark> Edit </v-toolbar>
        <v-form v-if="isAdmin">
          <v-text-field v-model="thread.title" label="Title" />
          <v-text-field v-model="thread.content" label="Content" />
        </v-form>
        <v-card-actions>
          <v-btn v-if="isAdmin" @click="persist"> Save </v-btn>
          <v-btn v-if="isAdmin" @click="deleteThread"> Delete </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";
import { auth as store } from "@/store/auth.module";

export default {
  name: "EditThreadDialog",
  props: {
    thread: Object,
    opened: Boolean,
  },
  methods: {
    deleteThread() {
      api.threads.delete(this.thread).then(
        () => this.$emit("refresh"),
        (error) => alert(error.response.data.message)
      );
    },
    persist() {
      api.threads
        .edit({
          id: this.thread.id,
          categoryName: this.thread.categoryName,
          originalPoster: this.thread.originalPoster,
          title: this.thread.title,
          content: this.thread.content,
          creationDate: this.thread.creationDate,
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
