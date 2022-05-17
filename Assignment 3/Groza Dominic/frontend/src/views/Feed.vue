<template>
  <v-card>
    <v-card-title>
      Welcome to the Feed!
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
      <v-btn @click="addPost">Share something</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="posts"
        :search="search"
        @click:row="editPost">
      <template v-slot:item="row">
        <tr>
          <td >{{ new Date(row.item.created_at).toLocaleString() }}</td>
          <td>{{ row.item.user.firstName + " " + row.item.user.lastName }}</td>
          <td>{{ row.item.body }}</td>
          <td>{{ row.item.likes }}</td>
          <td>{{ row.item.disLikes }}</td>
          <td v-if="user.id===row.item.user.id">
            <v-btn depressed @click="deletePost(row.item.id)" color="red">Delete</v-btn>
          </td>
        </tr>
      </template>

    </v-data-table>
    <v-btn @click="generateReportPDF" color="yellow">Generate PDF</v-btn>
    <v-btn @click="generateReportCSV" color="blue">Generate CSV</v-btn>
    <NewPost
        :opened="dialogVisible"
        @refresh="refreshList"
    ></NewPost>
  </v-card>
</template>

<script>
import api from "../api";
import NewPost from "../components/NewPost";

const user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "Feed",
  components: {NewPost},
  data() {
    return {
      posts: [],
      search: "",
      headers: [
        {text: "Date", align: "start", value: "created_at",},
        {text: "Author", value: "user"},
        {text: "Post", value: "body"},
        {text: "Likes", value: "likes"},
        {text: "Dislikes", value: "disLikes"},
      ],
      dialogVisible: false,
      selectedPost: {},
      user: user,
    };
  },
  methods: {
    editPost(post) {
      this.selectedPost = post;
      this.dialogVisible = true;
    },
    addPost() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedPost = {};
      this.posts = (await api.posts.allPosts(user.id));
      console.log(this.posts);
    },
    generateReportPDF() {
      api.items.report("PDF");
    },
    generateReportCSV() {
      api.items.report("CSV");
    },
    deletePost(id) {
      api.posts.delete(id);
      // this.refreshList();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
