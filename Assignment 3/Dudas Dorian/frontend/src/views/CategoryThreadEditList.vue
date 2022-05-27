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
      <v-btn @click="goBack">Done Editing</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="threads"
      :search="search"
      show-expand
      @click:row="editThread"
    >
      <template v-slot:expanded-item="{ headers, item }">
        <td :colspan="headers.length">{{ item.content }}</td>
      </template>
    </v-data-table>
    <EditThreadDialog
      :opened="dialogVisible"
      :thread="selectedThread"
      @refresh="refreshList"
    ></EditThreadDialog>
  </v-card>
</template>

<script>
import api from "@/api";
import { auth as store } from "@/store/auth.module";
import EditThreadDialog from "@/components/EditThreadDialog";

export default {
  name: "CategoryThreadEditList",
  components: { EditThreadDialog },
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
    };
  },
  methods: {
    goBack() {
      this.$router.push({
        path: "/threads-from-category/" + this.$route.params.id,
      });
    },
    editThread(thread) {
      if (this.isAdmin) {
        this.dialogVisible = true;
        this.selectedThread = thread;
      }
    },
    async refreshList() {
      this.dialogVisible = false;
      this.threads = await api.threads.allThreadsFromCategory(
        parseInt(this.$route.params.id)
      );
    },
    addThread() {
      this.dialogVisible = true;
    },
  },
  created() {
    this.refreshList();
  },
  computed: {
    theCategoryName() {
      if (this.threads.length > 0) {
        return "Threads in Category " + this.threads[0].categoryName;
      }
      return "Threads in Category";
    },
    isAdmin() {
      return store.getters.isAdmin(store.state);
    },
  },
};
</script>

<style scoped></style>
