<template>
  <v-card>
    <v-card-title>
      Movies
      <v-spacer></v-spacer>
      <v-text-field
        v-model="movieSearch"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addMovie">Add Movie</v-btn>
    </v-card-title>
    <v-data-table
      :headers="movieHeader"
      :items="movies"
      :search="movieSearch"
      @click:row="editMovie"
    ></v-data-table>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="userSearch"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser">Add User</v-btn>
    </v-card-title>
    <v-data-table
      :headers="userHeader"
      :items="users"
      :search="userSearch"
      @click:row="editUser"
    ></v-data-table>
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
import MovieDialog from "../components/MovieDialog";
import UserDialog from "../components/UserDialog";

export default {
  name: "Admin",
  components: { MovieDialog, UserDialog },
  data() {
    return {
      movies: [],
      users: [],
      movieHeader: [
        { text: "Title", value: "title" },
        { text: "Description", value: "description" },
        { text: "Poster", value: "imageLink" },
        { text: "Backdrop", value: "backdropLink" },
        { text: "Genres", value: "genresString" },
      ],
      userHeader: [
        { text: "Username", value: "username" },
        { text: "Email", value: "email" },
      ],
      movieSearch: "",
      userSearch: "",
      movieDialogVisible: false,
      userDialogVisible: false,
      selectedMovie: {},
      selectedUser: {},
    };
  },
  methods: {
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
    async addMovie() {
      this.movieDialogVisible = true;
    },
    async addUser() {
      this.userDialogVisible = true;
    },
    async editMovie(movie) {
      this.selectedMovie = movie;
      this.movieDialogVisible = true;
    },
    async editUser(user) {
      this.selectedUser = user;
      this.userDialogVisible = true;
    },
  },
  created() {
    this.refreshMovies();
    this.refreshUsers();
  },
};
</script>

<style scoped></style>
