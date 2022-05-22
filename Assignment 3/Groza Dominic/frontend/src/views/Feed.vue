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
        <tr v-if="isFriend(row.item.user) || row.item.user.id===user.id">
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
\
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
          <td>{{ row.item.users.length }}</td>
          <td v-if="!userInGroup(row.item)">
            <v-btn depressed @click="enterGroup(row.item)" style="margin-right:2em" color="green">Join Group</v-btn>
          </td>
        </tr>
      </template>
    </v-data-table>

    <v-card-title>
      All Users
    </v-card-title>

    <v-data-table
        :headers="headersUsers"
        :items="users">
      <template v-slot:item="row">
        <tr v-if="row.item.id!==user.id">
          <td>{{ row.item.username }}</td>
          <td>{{ row.item.firstName }}</td>
          <td>{{ row.item.lastName }}</td>
          <td v-if="!isFriend(row.item)">
            <v-btn depressed @click="addFriend(row.item)" color="blue">Add Friend</v-btn>
          </td>
          <td v-if="isFriend(row.item)">
            <strong style="color: lightskyblue">You already are friends</strong>
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
    users: [],
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
      headersUsers: [
        {text: "Username", align: "start", value: "username",},
        {text: "First Name", value: "first_name"},
        {text: "Last Name", value: "last_name"},
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
      this.posts = (await api.posts.allPosts());
      this.groups = (await api.groups.allGroups());
      this.users = (await api.users.allUsers());
    },
    enterGroup(group) {
      api.groups.addUser(group)
          .then(() => this.refreshList());
    },
    userInGroup(group) {
      let ingroup = false;
      group.users.forEach(groupUser => {
        if (user.id === groupUser.id) {
          ingroup = true;
        }
      });
      return ingroup;
    },
    addFriend(friend){
      api.users.addFriend(friend.id)
          .then(() => window.location.reload());
    },
    isFriend(friend) {
      let isFriend = false;
      console.log("friend",friend);
      friend.friends.forEach(friendUser => {
        if (user.id === friendUser.id) {
          isFriend = true;
        }
      });
      return isFriend;
    },
    deletePost(id) {
      api.posts.delete(id);
      window.location.reload();
    }
    ,
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
