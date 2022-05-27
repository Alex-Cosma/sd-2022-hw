<template>
  <v-card>
    <v-card-title>
      Hotels
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="flightsPage">Flights page</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="hotels"
      :search="search"
      @click:row="bookhotel"
    >
    </v-data-table>
    <bookingHotelsDialog
      :opened="dialogVisible"
      :hotel="selectedhotel"
      :user="user"
      @refresh="refreshList"
    ></bookingHotelsDialog>
  </v-card>
</template>

<script>
import api from "../api";
import bookingHotelsDialog from "../components/BookingHotelsDialog";
import router from "../router";
export default {
  name: "bookingHotelsList",
  components: { bookingHotelsDialog },
  data() {
    return {
      
      hotels: [],
      search: "",
      headers: [
        {
          text: "Name ",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "City", value: "cityName" },
        { text: "Price", value: "price" },
      ],
      dialogVisible: false,
      selectedhotel: {},
    };
  },
  methods: {
    bookhotel(hotel) {
      this.selectedhotel = hotel;
      this.dialogVisible = true;
    },
    
    flightsPage(){
      router.push("/bookingflights")
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedhotel = {};
      this.hotels = await api.hotels.allHotels();
      this.user = JSON.parse(localStorage.getItem("user"));
    },

  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>