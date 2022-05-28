<template>
  <v-card>
    <v-card-title>
      flights
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addflight">Add flight</v-btn>
      <v-btn @click="usersPage">Users page</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="flights"
      :search="search"
      @click:row="editflight">
     
    </v-data-table>
    <flightDialog
      :opened="dialogVisible"
      :flight="selectedflight"
      @refresh="refreshList"
    ></flightDialog>
  </v-card>
</template>

<script>
import api from "../api";
import flightDialog from "../components/FlightDialog";
import router from "../router";
export default {
  name: "flightList",
  components: { flightDialog },
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
        { text: "Number of Seats", value: "seats" },

      ],
      dialogVisible: false,
      selectedflight: {},
    };
  },
  methods: {
    editflight(flight) {
      this.selectedflight = flight;
      this.dialogVisible = true;
    },
    addflight() {
      this.dialogVisible = true;
    },
    deleteflight(flight) {
      this.selectedflight = flight;
      this.dialogVisible = true;
    },
    usersPage(){
      router.push("/users")
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedflight = {};
      this.flights = await api.flights.allFlights();
      console.log(this.flights[0]);
    },

  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>