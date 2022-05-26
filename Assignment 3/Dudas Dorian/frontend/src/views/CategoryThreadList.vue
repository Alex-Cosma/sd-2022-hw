<template>
  <v-card>
    <v-card-title>
      {{ theCategoryName }}
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addThread">New Thread</v-btn>
      <v-btn v-if="isAdmin" @click="editThreads">Edit Threads</v-btn>
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
    <AddThreadDialog
      :opened="dialogVisible"
      :thread="selectedThread"
      :category="category.name"
      @refresh="refreshList"
    ></AddThreadDialog>
  </v-card>
</template>

<script>
import api from "@/api";
import AddThreadDialog from "@/components/AddThreadDialog";
import { auth as store } from "@/store/auth.module";

export default {
  name: "CategoryThreadList",
  components: { AddThreadDialog },
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
      ],
      dialogVisible: false,
      selectedThread: {},
      category: {},
    };
  },
  methods: {
    goToThread(thread) {
      this.$router.push({
        path: "/posts-from-thread/" + thread.id,
      });
    },
    editThreads() {
      this.$router.push({
        path: "/edit-threads-from-category/" + this.$route.params.id,
      });
    },
    async refreshList() {
      this.threads = await api.threads.allThreadsFromCategory(
        parseInt(this.$route.params.id)
      );
    },
    async setCategory() {
      this.category = await api.categories.categoryById(
        parseInt(this.$route.params.id)
      );
    },
    addThread() {
      this.dialogVisible = true;
    },
  },
  created() {
    this.refreshList();
    this.setCategory();
  },
  computed: {
    theCategoryName() {
      return "Threads in Category " + this.category.name;
    },
    isAdmin() {
      return store.getters.isAdmin(store.state);
    },
  },
};
</script>

<style scoped></style>
