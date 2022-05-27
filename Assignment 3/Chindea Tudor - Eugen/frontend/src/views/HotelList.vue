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
      <v-btn @click="addhotel">Add hotel</v-btn>
      <v-btn @click="usersPage">Users page</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="hotels"
      :search="search"
      @click:row="edithotel"
    >
    </v-data-table>
    <hotelDialog
      :opened="dialogVisible"
      :hotel="selectedhotel"
      @refresh="refreshList"
    ></hotelDialog>
  </v-card>
</template>

<script>
import api from "../api";
import hotelDialog from "../components/HotelDialog";
import router from "../router";
export default {
  name: "hotelList",
  components: { hotelDialog },
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
        { text: "Places", value: "places" },

      ],
      dialogVisible: false,
      selectedhotel: {},
    };
  },
  methods: {
    edithotel(hotel) {
      this.selectedhotel = hotel;
      this.dialogVisible = true;
    },
    addhotel() {
        
      this.dialogVisible = true;
    },
    deletehotel(hotel) {
      this.selectedhotel = hotel;
      this.dialogVisible = true;
    },
    usersPage(){
      router.push("/users")
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedhotel = {};
      this.hotels = await api.hotels.allHotels();
    },

  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>