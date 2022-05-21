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
      ></v-text-field>
      <v-btn @click="addBook">Add Book</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="editBook"
    ></v-data-table>
    <CRUDBookDialog
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></CRUDBookDialog>
  </v-card>
</template>

<script>
import api from "../api";
import CRUDBookDialog from "../components/CRUDBookDialog";

export default {
  name: "AdminBooksCRUD",
  components: { CRUDBookDialog },
  data() {
    return {
      books: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Price", value: "price" },
        { text: "Quantity", value: "quantity" },
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
    deleteBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.getAllBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
