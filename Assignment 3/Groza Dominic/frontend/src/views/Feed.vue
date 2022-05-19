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
        :search="search">
      <template v-slot:item="row">
        <tr>
          <td>{{ new Date(row.item.created_at).toLocaleString() }}</td>
          <td>{{ row.item.user.firstName + " " + row.item.user.lastName }}</td>
          <td>{{ row.item.body }}</td>
          <td>{{ row.item.likes }}</td>
          <td>{{ row.item.disLikes }}</td>
          <td v-if="user.id===row.item.user.id">
            <v-btn depressed @click="editPost(row.item)" style="margin-right:2em" color="gray">Edit</v-btn>
            <v-btn depressed @click="deletePost(row.item.id)" color="red">Delete</v-btn>
          </td>
        </tr>
      </template>

    </v-data-table>
    <NewPost
        :opened="dialogVisible"
        @refresh="refreshList"
    ></NewPost>
    <EditPost
        :opened="editPostDialog"
        :post="selectedPost"
        @refresh="refreshList"
    ></EditPost>
    <v-card-title>
      All Groups
    </v-card-title>

    <v-data-table
        :headers="headersGroups"
        :items="groups">
      <template v-slot:item="row">
        <tr>
          <td>{{ row.item.name }}</td>
          <td>{{ row.item.users.length}}</td>
          <td v-if="!userInGroup(row.item)">
            <v-btn depressed @click="enterGroup(row.item)" style="margin-right:2em" color="green">Join Group </v-btn>
          </td>
        </tr>
      </template>
    </v-data-table>
  </v-card>

</template>

<script>
import api from "../api";
import NewPost from "../components/NewPost";
import EditPost from "@/components/EditPost";

const user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "Feed",
  components: {NewPost, EditPost},
  props: {
    posts: [],
  },
  data() {
    return {
      groups: [],
      search: "",
      headers: [
        {text: "Date", align: "start", value: "created_at",},
        {text: "Author", value: "user"},
        {text: "Post", value: "body"},
        {text: "Likes", value: "likes"},
        {text: "Dislikes", value: "disLikes"},
        {text: "Actions", value: "actions", align: "center"},
      ],
      headersGroups: [
        {text: "Group Name", align: "start", value: "name",},
        {text: "People joined", value: "user"},
      ],
      dialogVisible: false,
      editPostDialog: false,
      selectedPost: {},
      user: user,
    };
  },
  methods: {
    editPost(post) {
      console.log("this post", post)
      this.selectedPost = post;
      this.editPostDialog = true;
    },
    addPost() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedPost = {};
      this.posts = (await api.posts.allPosts(user.id));
      this.groups = (await api.groups.allGroups());
    },
    enterGroup(group) {
      api.groups.addUser(group)
          .then(() => this.refreshList());
    },
    userInGroup(group) {
     let ingroup = false;
       group.users.forEach(groupUser=> {
         if (user.id === groupUser.id) {
           ingroup = true;
         }
       });
     return ingroup;
    },
    deletePost(id) {
      api.posts.delete(id);
      this.refreshList();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
