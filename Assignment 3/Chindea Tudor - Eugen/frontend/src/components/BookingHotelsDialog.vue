<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{  "Book hotel room" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="hotel.name" label="Name" />
          <v-text-field v-model="hotel.price" label="Price" />
          <v-text-field v-model="hotel.cityName" label="City" />
          <v-text-field type="date" v-model="startDate" label="Date of arrival"/>
          <v-text-field type="date" v-model="endDate" label="Date lo leave"/>

        </v-form>
        <v-card-actions>
          <v-btn @click="bookhotel"> Book Room</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "hotelDialog",
  props: {
    user: Object,
    hotel: Object,
    startDate: Date,
    endDate: Date,
    opened: Boolean,
  },
  methods: {
    bookhotel() {
        console.log(this.startDate);
    api.bookinghotels
        .bookRoom({
        endDate: this.endDate,
        price: this.hotel.price,
        startDate: this.startDate, 
        hotelName: this.hotel.name,
        userNames:[this.user.username],
        
        })
        .then(() => this.$emit("refresh"));
  },
},
};
</script>

<style scoped></style>
