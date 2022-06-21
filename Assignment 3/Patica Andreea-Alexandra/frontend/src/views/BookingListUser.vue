
<template>
  <v-card>
    <v-card-title>
      My Bookings
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
        :items="bookings"
        :search="search"
        @click:row="deleteBooking"
    ></v-data-table>

    <DeleteBookingDialog
        :opened="dialogVisible"
        :booking="selectedBooking"
        @refresh="refreshList"
        @close="dialogVisible = !dialogVisible"
    ></DeleteBookingDialog>
  </v-card>
</template>

<script>
import api from "../api";
import DeleteBookingDialog from "../components/deleteBookingDialog";
import { auth as store } from "../store/auth.module";

export default {
  name: "BookingListUser",
  components: { DeleteBookingDialog },
  data() {
    return {
      bookings: [],
      search: "",
      headers: [
        {
          text: "Property",
          align: "start",
          sortable: false,
          value: "property",
        },
        { text: "Date", value: "date" },
      ],
      dialogVisible: false,
      selectedBooking: {},
    };
  },
  methods: {
    deleteBooking(booking) {
      this.selectedBooking = booking;
      this.dialogVisible = true;
    },
    async refreshList() {
      console.log("aaaaaaaaaaaaa");
      this.dialogVisible = false;
      this.selectedBooking = {};
      this.bookings = await api.book.getBookingsByOwner(store.state.user);
      console.log(this.bookings);
      console.log("bbbbbbbbbbbbbbb");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>



