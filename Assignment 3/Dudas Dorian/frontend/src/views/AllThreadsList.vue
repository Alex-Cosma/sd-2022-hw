<template>
  <v-card>
    <v-card-title>
      Threads
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
      :items="threads"
      :search="search"
      show-expand
      @click:row="goToThread"
    >
      <template v-slot:expanded-item="{ headers, item }">
        <td :colspan="headers.length">{{ item.content }}</td>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import api from "@/api";

export default {
  name: "AllThreadsList",
  data() {
    return {
      threads: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          value: "title",
        },
        { text: "Created At", value: "creationDate" },
        { text: "User", value: "originalPoster" },
        { text: "In Category", value: "categoryName" },
      ],
    };
  },
  methods: {
    goToThread(thread) {
      this.$router.push({
        path: "/posts-from-thread/" + thread.id,
      });
    },
    async refreshList() {
      this.threads = await api.threads.allThreads();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
