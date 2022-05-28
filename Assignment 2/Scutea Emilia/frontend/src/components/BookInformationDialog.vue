<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark></v-toolbar>
        <img :src="book.imageUrl" class="mx-auto" width="200px" />
        <v-card-text v-model="book.title">Title : {{ book.title }}</v-card-text>
        <v-card-text v-model="book.author"
          >Author : {{ book.author }}
        </v-card-text>
        <v-card-text v-model="book.genre">Genre : {{ book.genre }}</v-card-text>
        <v-card-text v-model="book.description"
          >Description : {{ book.description }}
        </v-card-text>

        <v-card-text v-model="book.price">Price : {{ book.price }}</v-card-text>
        <v-card-text v-if="existsPageCount" v-model="book.pageCount"
          >Number of pages : {{ book.pageCount }}
        </v-card-text>
        <v-card-text v-model="book.quantity"
          >Quantity : {{ book.quantity }}
        </v-card-text>
        <v-card-actions>
          <v-btn @click="sellButton">Add to cart</v-btn>
        </v-card-actions>

        <v-text-field v-model="review.text" label="Review"></v-text-field>
        <v-card-actions>
          <v-btn
            :color="changeBackgroundColor1 ? 'secondary' : 'info'"
            @click="
              addRating('Excellent'),
                (changeBackgroundColor1 = !changeBackgroundColor1)
            "
            :style="{
              backgroundColor: changeBackgroundColor1 ? 'black !important' : '',
              color: changeBackgroundColor1 ? 'secondary' : 'info',
            }"
            >Excellent
          </v-btn>
          <v-btn
            :color="changeBackgroundColor2 ? 'secondary' : 'info'"
            @click="
              addRating('Great'),
                (changeBackgroundColor2 = !changeBackgroundColor2)
            "
            :style="{
              backgroundColor: changeBackgroundColor2 ? 'black !important' : '',
              color: changeBackgroundColor2 ? 'secondary' : 'info',
            }"
            >Great
          </v-btn>
          <v-btn
            :color="changeBackgroundColor3 ? 'secondary' : 'info'"
            @click="
              addRating('Average'),
                (changeBackgroundColor3 = !changeBackgroundColor3)
            "
            :style="{
              backgroundColor: changeBackgroundColor3 ? 'black !important' : '',
              color: changeBackgroundColor3 ? 'secondary' : 'info',
            }"
            >Average
          </v-btn>
          <v-btn
            :color="changeBackgroundColor4 ? 'secondary' : 'info'"
            @click="
              addRating('Poor'),
                (changeBackgroundColor4 = !changeBackgroundColor4)
            "
            :style="{
              backgroundColor: changeBackgroundColor4 ? 'black !important' : '',
              color: changeBackgroundColor4 ? 'secondary' : 'info',
            }"
            >Poor
          </v-btn>
        </v-card-actions>

        <v-card-actions>
          <v-btn @click="addReview">Add review</v-btn>
        </v-card-actions>

        <v-data-table :headers="headers" :items="book.reviews" :search="search">
        </v-data-table>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

const user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "BookInformationDialog",
  props: {
    book: Object,
    opened: Boolean,
    review: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      changeBackgroundColor1: false,
      changeBackgroundColor2: false,
      changeBackgroundColor3: false,
      changeBackgroundColor4: false,
      reviews: [],
      search: "",
      headers: [
        { text: "Text", value: "text" },
        { text: "Rating", value: "rating" },
      ],
    };
  },
  methods: {
    existsPageCount() {
      return this.book.price > 0;
    },
    sellButton() {
      api.books.sellBook({
        id: this.book.id,
        title: this.book.title,
        author: this.book.author,
        genre: this.book.genre,
        price: this.book.price,
        quantity: this.book.quantity,
      });

      api.cart
        .createCart(
          {
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            price: this.book.price,
            quantity: this.book.quantity,
          },
          user.id
        )
        .then(() => this.$emit("refresh"));
    },
    checkExistReviews() {
      return api.bookreviews.getReviews(this.book.id).size() !== 0;
    },
    addReview() {
      api.bookreview
        .addReview(
          {
            text: this.review.text,
            rating: this.review.rating,
            book: this.book,
            id: this.book.id,
          },
          user.id
        )
        .then(() => this.$emit("refresh"));
    },
    addRating(rating) {
      this.review.rating = rating;
    },
  },
};
</script>

<style scoped></style>
