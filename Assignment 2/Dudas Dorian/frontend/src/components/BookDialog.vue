<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{
            isNew && isAdmin
              ? "Create book"
              : !isNew && isAdmin
              ? "Edit book"
              : "Sell book"
          }}
        </v-toolbar>
        <v-form v-if="isAdmin">
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field
            v-model="book.quantity"
            label="Quantity"
            type="number"
            min="0"
          />
          <v-text-field
            v-model="book.price"
            label="Price"
            type="number"
            min="0"
          />
        </v-form>
        <v-card-actions>
          <v-btn v-if="isAdmin" @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew && isAdmin" @click="deleteBook">Delete</v-btn>
          <v-btn v-if="book.quantity >= selectedQuantity" @click="sellBook"
            >Sell Book</v-btn
          >
          <v-text-field
            v-model="selectedQuantity"
            label="Insert Quantity"
            type="number"
            min="0"
            @input="selectQuantity"
          />
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import { auth as store } from "../store/auth.module";

export default {
  name: "BookDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  data() {
    return {
      selectedQuantity: 0,
    };
  },
  methods: {
    deleteBook() {
      api.books.delete(this.book).then(
        () => this.$emit("refresh"),
        (error) => alert(error.response.data.message)
      );
    },
    persist() {
      if (this.isNew) {
        api.books
          .create({
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .then(
            () => this.$emit("refresh"),
            (error) => alert(error.response.data.message)
          );
      } else {
        api.books
          .edit({
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .then(
            () => this.$emit("refresh"),
            (error) => alert(error.response.data.message)
          );
      }
    },
    sellBook() {
      api.books
        // can't figure out the CORS issue
        // .sellBook({ id: this.book.id, quantity: this.book.quantity })
        // .then(
        //   () => this.$emit("refresh"),
        //   (error) => alert(error.response.data.message)
        // );
        .edit({
          id: this.book.id,
          title: this.book.title,
          author: this.book.author,
          genre: this.book.genre,
          quantity: this.book.quantity - this.selectedQuantity,
          price: this.book.price,
        })
        .then(
          () => this.$emit("refresh"),
          (error) => alert(error.response.data.message)
        );
    },
    selectQuantity(quantity) {
      this.selectedQuantity = quantity;
    },
  },
  computed: {
    isNew: function () {
      return !this.book.id;
    },
    isAdmin: function () {
      return store.getters.isAdmin(store.state);
    },
  },
};
</script>

<style scoped></style>
