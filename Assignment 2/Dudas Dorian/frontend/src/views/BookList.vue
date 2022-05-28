<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
        @input="searchBooks"
      ></v-text-field>
      <v-btn v-if="isAdmin" @click="addBook">Add Book</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="editBook"
    ></v-data-table>
    <v-btn v-if="isAdmin" @click="generateReport">Generate Report</v-btn>
    <v-text-field
      v-if="isAdmin"
      v-model="type"
      label="Type"
      single-line
      hide-details
    ></v-text-field>
    <BookDialog
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></BookDialog>
  </v-card>
</template>

<script>
import api from "../api";
import { auth as store } from "../store/auth.module";
import BookDialog from "../components/BookDialog";

export default {
  name: "BookList",
  components: { BookDialog },
  data() {
    return {
      books: [],
      search: "",
      type: "PDF",
      headers: [
        {
          text: "Title",
          align: "start",
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" },
      ],
      dialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    editBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    addBook() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allBooks();
    },
    async searchBooks() {
      if (this.search !== "") {
        this.books = await api.books.filterBooks(this.search);
      } else {
        this.books = await api.books.allBooks();
      }
    },
    generateReport() {
      api.books.exportReport(this.type.toUpperCase());
    },
  },
  created() {
    this.refreshList();
  },
  computed: {
    isAdmin() {
      return store.getters.isAdmin(store.state);
    },
  },
};
</script>

<style scoped></style>
