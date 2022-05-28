<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Create thread </v-toolbar>
        <v-form>
          <v-text-field v-model="thread.title" label="Title" />
          <v-text-field v-model="thread.content" label="Content" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Create </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";

export default {
  name: "AddThreadDialog",
  props: {
    thread: Object,
    opened: Boolean,
    category: String,
  },
  methods: {
    persist() {
      const user = JSON.parse(localStorage.getItem("user"));
      api.threads
        .create({
          categoryName: this.category,
          originalPoster: user.username,
          title: this.thread.title,
          content: this.thread.content,
          creationDate: new Date(),
        })
        .then(
          () => this.$emit("refresh"),
          (error) => alert(error.response.data.message)
        );
    },
  },
};
</script>

<style scoped></style>
