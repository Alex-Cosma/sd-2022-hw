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
      <v-btn @click="sellBook">Sell Book</v-btn>
      <v-text-field
        v-model="this.selectedQuantity"
        label="Insert Quantity"
        type="number"
        min="0"
        @input="selectQuantity"
      />
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="selectBook"
    ></v-data-table>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "EmployeeView",
  data() {
    return {
      books: [],
      search: "",
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
      selectedBook: {},
      selectedQuantity: 1,
    };
  },
  methods: {
    selectBook(book) {
      this.selectedBook = book;
    },
    selectQuantity(quantity) {
      this.selectedQuantity = quantity;
    },
    sellBook() {
      api.books.edit({
        id: this.selectedBook.id,
        title: this.selectedBook.title,
        author: this.selectedBook.author,
        genre: this.selectedBook.genre,
        quantity: this.selectedBook.quantity - this.selectedQuantity,
        price: this.selectedBook.price,
      });
      this.selectedBook = {};
      this.refreshList();
    },
    async refreshList() {
      this.selectedBook = {};
      // this.books = await api.books.allBooks();
      if (this.search !== "") {
        this.books = await api.books.filterBooks(this.search);
      } else {
        this.books = await api.books.allBooks();
      }
    },
    async searchBooks() {
      if (this.search !== "") {
        this.books = await api.books.filterBooks(this.search);
      } else {
        this.books = await api.books.allBooks();
      }
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
