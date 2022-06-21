<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-btn @click="addUser">Add User</v-btn>
      <v-btn @click="deleteUser">Delete User</v-btn>
      <v-btn @click="updateUser">Update User</v-btn>
    </v-card-title>
    <v-data-table :headers="userHeaders" :items="users" @click:row="selectUser">
    </v-data-table>

    <v-card-title>
      Movies
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addMovie">Add Movie</v-btn>
      <v-btn @click="deleteMovie">Delete Movie</v-btn>
      <v-btn @click="updateMovie">Update Movie</v-btn>
      <v-btn @click="generateReport">Generate Report</v-btn>
    </v-card-title>
    <v-data-table
      :headers="movieHeaders"
      :items="movies"
      :search="search"
      @click:row="selectMovie"
    >
    </v-data-table>
    <MovieDialog
      :opened="movieDialogVisible"
      :movie="selectedMovie"
      @refresh="refreshMovies"
    ></MovieDialog>
    <UserDialog
      :opened="userDialogVisible"
      :user="selectedUser"
      @refresh="refreshUsers"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import MovieDialog from "@/components/MovieDialog";
import UserDialog from "@/components/UserDialog";
// import axios from "axios";
// import authHeader, {BASE_URL} from "@/api/http";


export default {
  name: "UserList",
  components: { MovieDialog, UserDialog },
  data() {
    return {
      users: [],
      movies: [],
      search: "",
      movieHeaders: [
        {
          text: "Title",
          align: "start",
          sortable: true,
          value: "title",
        },
        { text: "Genre", value: "genre" },
        { text: "Rating", sortable: true, value: "rating" },
        { text: "Number of reviews", sortable: true, value: "numberOfReviews" },
      ],
      userHeaders: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "username",
        },
        { text: "Email", value: "email" },
      ],
      userDialogVisible: false,
      movieDialogVisible: false,
      selectedMovie: {},
      selectedUser: {},
    };
  },
  methods: {
    updateMovie() {
      if (this.selectedMovie) {
        this.movieDialogVisible = true;
      }
    },
    addMovie() {
      this.selectedMovie={};
      this.movieDialogVisible = true;
    },
    deleteMovie() {
      if (this.selectedMovie) {
        api.movies.delete(this.selectedMovie).then(() => this.refreshMovies());
      }
    },
    updateUser() {
      if (this.selectedUser) {
        this.userDialogVisible = true;
      }
    },
    addUser() {
      this.userDialogVisible = true;
    },
    deleteUser() {
      if (this.selectedUser) {
        api.users.delete(this.selectedUser).then(() => this.refreshUsers());
      }
    },
    selectMovie(movie) {
      this.selectedMovie = movie;
    },
    selectUser(user) {
      this.selectedUser = user;
    },
    generateReport(){
      api.users.generateReport().then((response)=>
      { var fileURL = window.URL.createObjectURL(new Blob([response.data]));
        var fURL = document.createElement('a');
        fURL.href = fileURL;
        fURL.setAttribute("download", "topMovies.pdf");
        document.body.appendChild(fURL);
        fURL.click();
      })

    },

    async refreshMovies() {
      this.movieDialogVisible = false;
      this.selectedMovie = {};
      this.movies = await api.movies.allMovies();
    },
    async refreshUsers() {
      this.userDialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
  },
  created() {
    this.refreshUsers();
    this.refreshMovies();
  },
};
</script>

<style scoped></style>
