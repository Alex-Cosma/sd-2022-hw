<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create flight" : "Edit flight" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="flight.seats" label="Seats" />
          <v-text-field v-model="flight.price" label="Price" />
          <v-text-field v-model="flight.departureCityName" label="Departure City" />
          <v-text-field v-model="flight.arrivalCityName" label="Arrival City" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteFlight" v-if="!isNew"> Delete </v-btn>
        </v-card-actions>
      </v-card> 
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "FlightDialog",
  props: {
    flight: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {

        api.flights
          .create({
            seats: this.flight.seats,
            departureCityName: this.flight.departureCityName,
            arrivalCityName: this.flight.arrivalCityName,
            price: this.flight.price,
          })
         .then(() => this.$emit("refresh"));
      } else {

        api.flights
          .edit({
            id: this.flight.id,
            seats: this.flight.seats,
            departureCityName: this.flight.departureCityName,
            arrivalCityName: this.flight.arrivalCityName,
            price: this.flight.price,
          })
          .then(() => this.$emit("refresh"));
      }
    },
     deleteFlight() {
      if (!this.isNew) {
        api.flights
          .delete({
            id: this.flight.id,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    
  },
  computed: {
    isNew: function () {
      return !this.flight.id;
    },
  },
};
</script>

<style scoped></style>
