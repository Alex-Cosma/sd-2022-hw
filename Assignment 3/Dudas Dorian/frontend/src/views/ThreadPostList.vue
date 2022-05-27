<template>
  <v-card>
    <v-card-title>
      {{ theThreadName }}
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addPost">Post New Reply</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="posts"
      :search="search"
      @click:row="editPost"
    >
    </v-data-table>
    <EditPostDialog
      :opened="editDialogVisible"
      :post="selectedPost"
      @refresh="refreshList"
    ></EditPostDialog>
    <AddPostDialog
      :opened="dialogVisible"
      :post="{}"
      :thread="thread.title"
      @refresh="refreshList"
    ></AddPostDialog>
  </v-card>
</template>

<script>
import api from "@/api";
import { auth as store } from "@/store/auth.module";
import AddPostDialog from "@/components/AddPostDialog";
import EditPostDialog from "@/components/EditPostDialog";

export default {
  name: "ThreadPostList",
  components: { EditPostDialog, AddPostDialog },
  data() {
    return {
      posts: [],
      search: "",
      headers: [
        {
          text: "Replies",
          align: "start",
          value: "content",
        },
        { text: "Created At", value: "creationDate" },
        { text: "User", value: "posterUsername" },
      ],
      editDialogVisible: false,
      dialogVisible: false,
      selectedPost: {},
      thread: {},
    };
  },
  methods: {
    editPost(post) {
      if (this.isAdmin) {
        this.editDialogVisible = true;
        this.selectedPost = post;
      }
    },
    async refreshList() {
      this.dialogVisible = false;
      this.editDialogVisible = false;
      this.posts = await api.posts.allPostsFromThread(
        parseInt(this.$route.params.id)
      );
    },
    async setThread() {
      this.thread = await api.threads.threadById(
        parseInt(this.$route.params.id)
      );
    },
    addPost() {
      this.dialogVisible = true;
    },
  },
  created() {
    this.refreshList();
    this.setThread();
  },
  computed: {
    theThreadName() {
      return "Replies in Thread " + this.thread.title;
    },
    isAdmin() {
      return store.getters.isAdmin(store.state);
    },
  },
};
</script>

<style scoped></style>
