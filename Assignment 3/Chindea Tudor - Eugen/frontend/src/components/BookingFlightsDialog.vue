<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{  "Book flight " }}
        </v-toolbar>
        <v-form>
         <v-text-field v-model="flight.price" label="Price" />
          <v-text-field v-model="flight.departureCityName" label="Departure City" />
          <v-text-field v-model="flight.arrivalCityName" label="Arrival City" />
          <v-text-field type="date" v-model="flightDate" label="Date"/>

        </v-form>
        <v-card-actions>
          <v-btn @click="bookflight"> Book Seat</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "flightDialog",
  props: {
    user: Object,
    flight: Object,
    flightDate: Date,
    
    opened: Boolean,
  },
  methods: {
    bookflight() {
        console.log(this.startDate);
    api.bookingflights
        .bookFlight({
        date: this.flightDate,
        flightId: this.flight.id,
        userNames:[this.user.username],
        
        })
        .then(() => this.$emit("refresh"));
  },
},
};
</script>

<style scoped></style>
