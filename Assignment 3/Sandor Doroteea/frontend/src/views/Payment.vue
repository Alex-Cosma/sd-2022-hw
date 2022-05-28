<template>
  <v-card>
    <v-card-title>
      Recipes
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="track"
      :search="search"
    ></v-data-table>
    <iframe
      width="600"
      height="450"
      style="border: 0"
      loading="lazy"
      allowfullscreen
      src="https://www.google.com/maps/embed/v1/place?q=place_id:ChIJUyN2aONHRkcRRFNd1oakVRs&key=AIzaSyBR8cxgxePm65q9HH1C7xas6yQPix1E9h4"
    >
    </iframe>
    <div>
      <stripe-checkout
        ref="checkoutRef"
        mode="payment"
        :pk="publishableKey"
        :line-items="lineItems"
        :success-url="successURL"
        :cancel-url="cancelURL"
        @loading="(v) => (loading = v)"
      />
      <v-btn @click="submit">Checkout</v-btn>
    </div>
  </v-card>
</template>

<script>
import api from "../api";
import router from "../router";
import { StripeCheckout } from "@vue-stripe/vue-stripe";

export default {
  name: "RecipeList",
  components: { StripeCheckout },
  data() {
    this.publishableKey =
      "pk_test_51KzStYBeCTO9xhNT27bFgNkPT3oiGKBwgVa0h0uBrq2NFcoYD8DiR8nGzbYKA9VcDfqSeX1KGC2zTDaPROTlToEt00AjJnedIi";
    return {
      track: [],
      search: "",
      message: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "id", value: "id" },
        { text: "link", value: "link" },
        { text: "duration", value: "duration" },
        { text: "explicit_lyrics", value: "explicit_lyrics" },
        { text: "preview", value: "preview" },
        { text: "artist", value: "artist" },
        { text: "album", value: "album" },
      ],
      loading: false,
      lineItems: [
        {
          price: "price_1L0D5TBeCTO9xhNTTCCUuO7j", // The id of the one-time price you created in your Stripe dashboard
          quantity: 1,
        },
      ],
      successURL: "http://localhost:8091/#/payment/success",
      cancelURL: "http://localhost:8091/#/payment/fail",
      dialogVisible: false,
      selectedRecipe: {},
    };
  },
  methods: {
    submit() {
      // You will be redirected to Stripe's secure checkout page
      this.$refs.checkoutRef.redirectToCheckout();
    },
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },

    async refreshList() {
      this.dialogVisible = false;
      this.selectedRecipe = {};
      this.recipes = await api.recipes.allRecipes();
    },
  },

  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
