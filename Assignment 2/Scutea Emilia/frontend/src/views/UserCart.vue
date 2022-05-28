<template>
  <v-card>
    <v-card-title>
      Shopping Cart
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table :headers="headers" :items="cart_books">
      <template v-slot:item="row">
        <tr>
          <td>{{ row.item.title }}</td>
          <td>{{ row.item.author }}</td>
          <td>{{ row.item.genre }}</td>
          <td>{{ row.item.price }}</td>
          <td>
            <v-btn color="white" @click="deleteFromCart(row.item)">-</v-btn>
          </td>
          <td>
            <v-btn color="white" @click="addToCart(row.item)">+</v-btn>
          </td>
        </tr>
      </template>

      :search="search" ></v-data-table
    >
    <v-btn color="white" @click="placeOrder">Place order</v-btn>
    <v-btn color="white" @click="clearCart">Clear cart</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
const user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "UserCart",
  data() {
    return {
      cart_books: [],
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
      ],
    };
  },
  methods: {
    async refreshList() {
      this.cart_books = await api.cart.getCart(user.id);
    },
    placeOrder() {
      api.cart.placeOrder(user.id);
      this.refreshList();
    },
    clearCart() {
      api.cart.deleteCart(user.id);
      this.refreshList();
    },
    deleteFromCart(book) {
      api.cart.deleteFromCart(user.id, book.id, book);
      this.refreshList();
    },
    addToCart(book) {
      api.cart.createCart(book, user.id);
      this.refreshList();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
