<template>
  <v-card>
    <v-btn @click="viewProperties">My Properties</v-btn>
    <v-btn @click="viewBookings">My Bookings</v-btn>
    <v-btn @click="searchProperties">Book Something</v-btn>
    <v-btn @click="upgradePremium">Upgrade to Premium (todo)</v-btn>
  </v-card>
</template>

<script>
import router from "../router";
//import api from "../api";

export default {
  name: "AdminOptions",
  methods: {
    viewProperties() {
      router.push("/user-properties");
    },
    searchProperties() {
      router.push("/search-properties");
    },
    viewBookings() {
      router.push("/bookings");
    },
    upgradePremium() {
      router.push("/bookings");
    },
  },
};
</script>
