<template>
  <v-card>
    <v-card-title>
      Flights
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="hotelsPage">Hotels page</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="flights"
      :search="search"
      @click:row="bookflight"
    >
    </v-data-table>
    <bookingflightsDialog
      :opened="dialogVisible"
      :flight="selectedflight"
      :user="user"
      @refresh="refreshList"
    ></bookingflightsDialog>
  </v-card>
</template>

<script>
import api from "../api";
import bookingflightsDialog from "../components/BookingFlightsDialog";
import router from "../router";
export default {
  name: "bookingflightsList",
  components: { bookingflightsDialog },
  data() {
    return {
      flights: [],
      search: "",
      headers: [
        {
          text: "Departure City",
          align: "start",
          sortable: false,
          value: "departureCityName",
        },
        { text: "Arrival City", value: "arrivalCityName" },
        { text: "Price", value: "price" },
      ],
      dialogVisible: false,
      selectedflight: {},
    };
  },
  methods: {
    bookflight(flight) {
      this.selectedflight = flight;
      this.dialogVisible = true;
    },
    
    hotelsPage(){
      router.push("/bookinghotels")
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedflight = {};
      this.flights = await api.flights.allFlights();
      this.user = JSON.parse(localStorage.getItem("user"));
    },

  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>