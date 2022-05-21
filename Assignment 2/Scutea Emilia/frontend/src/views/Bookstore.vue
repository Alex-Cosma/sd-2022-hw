<template>
  <v-card>
    <v-card-title>
      Bookstore
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="white" v-bind="attrs" v-on="on"> Menu </v-btn>
        </template>
        <v-list>
          <v-list-item
            link
            @click="getBooksByGenre(item)"
            v-for="(item, index) in allGenreTypes"
            :key="index"
          >
            <v-list-item-title link @click="getBooksByGenre(item)">{{
              item
            }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-btn color="white" @click="cart">My Cart</v-btn>
      <v-btn color="white" @click="myreviews">My Reviews</v-btn>
    </v-card-title>
    <v-data-table :headers="headers" :items="books" :search="search">
      <template v-slot:item="abc">
        <tr>
          <td @click="sellBook(abc.item)">
            <img :src="abc.item.imageUrl" />
          </td>
          <td>{{ abc.item.title }}</td>
          <td>{{ abc.item.author }}</td>
          <td>{{ abc.item.genre }}</td>
        </tr>
      </template>
    </v-data-table>
    <BookInformationDialog
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></BookInformationDialog>
  </v-card>
</template>

<script>
import api from "../api";
import BookInformationDialog from "../components/BookInformationDialog";
import router from "../router";

export default {
  name: "BookStore",
  components: {
    BookInformationDialog,
  },
  data() {
    return {
      books: [],
      allGenreTypes: [],
      search: "",
      headers: [
        {
          text: "",
          align: "start",
          sortable: false,
          value: "imageUrl",
        },
        { text: "Title", value: "title" },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
      ],
      dialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    sellBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    cart() {
      router.push("/cart");
    },
    myreviews() {
      router.push("/userreviews");
    },
    getBooksByGenre(genre) {
      this.books = api.books.getBooksByGenre(genre);
      this.refreshListWithGenreType(genre);
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allBooks();
      this.allGenreTypes = await api.books.getAllGenreTypes();
    },
    async refreshListWithGenreType(genre) {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.getBooksByGenre(genre);
      this.allGenreTypes = await api.books.getAllGenreTypes();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>
