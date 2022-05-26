<template>
  <v-card>
    <v-card-title>
      Accommodations
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="viewBookings">Your bookings</v-btn>
      <v-btn color="blue" @click="openMap"> Search locations on the map </v-btn>
      <v-btn @click="becomeHost" :disabled="isUnauthorized()"
        >Become host</v-btn
      >
    </v-card-title>
    <v-data-table :headers="headers" :items="items" :search="search">
      <template v-slot:item="row">
        <tr>
          <td @click="viewItem(row.item)">
            <img
              :src="row.item.imageURL.picture_url"
              height="100px"
              width="150px"
            />
          </td>
          <td>{{ row.item.name }}</td>
          <td>
            {{ row.item.bedrooms }}
            {{ row.item.bedrooms == 1 ? "bedroom" : "bedrooms" }},
            {{ row.item.beds }} {{ row.item.beds == 1 ? "bed" : "beds" }},
            {{ Math.ceil(row.item.bathrooms) }}
            {{ Math.ceil(row.item.bathrooms) == 1 ? "bathroom" : "bathrooms" }}
          </td>
          <td>
            {{ row.item.address.street }}
          </td>
          <td>{{ row.item.price }}$/night</td>
          <td>
            <v-btn color="yellow" @click="writeReview(row.item)">
              Write review
            </v-btn>
          </td>
        </tr>
      </template>
    </v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
    <ReviewDialog
      :opened="reviewDialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ReviewDialog>
    <BookingsDialog
      :opened="bookingsDialogVisible"
      :items="bookings"
      @refresh="refreshList"
    ></BookingsDialog>
    <MapDialog
      :opened="mapDialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></MapDialog>
    <HostDialog
      :opened="hostDialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    >
    </HostDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";
import ReviewDialog from "../components/ReviewDialog";
import BookingsDialog from "../components/BookingsDialog";
import MapDialog from "../components/MapDialog";
import HostDialog from "../components/HostDialog";

const auth_user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "ItemList",
  components: {
    HostDialog,
    ItemDialog,
    ReviewDialog,
    BookingsDialog,
    MapDialog,
  },

  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "",
          align: "start",
          sortable: false,
          value: "image",
        },
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        {
          text: "Details",
          align: "start",
          sortable: false,
          value: "details",
        },
        {
          text: "Location",
          align: "start",
          sortable: false,
          value: "location",
        },
        {
          text: "Price",
          align: "start",
          sortable: false,
          value: "price",
        },
        {
          text: "",
          align: "start",
          sortable: false,
          value: "reviewBtn",
        },
      ],
      dialogVisible: false,
      reviewDialogVisible: false,
      bookingsDialogVisible: false,
      mapDialogVisible: false,
      hostDialogVisible: false,
      bookings: [],
      selectedItem: {},
      dates: ["2019-09-10", "2019-09-20"],
      computed: {
        dateRangeText() {
          return this.dates.join(" ~ ");
        },
      },
    };
  },
  methods: {
    async becomeHost() {
      await api.users.becomeHost(auth_user).then(() => this.refreshList());
    },
    openMap() {
      this.mapDialogVisible = true;
    },
    viewItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    writeReview(item) {
      this.selectedItem = item;
      this.reviewDialogVisible = true;
    },
    async viewBookings() {
      this.bookings = await api.items.allBookings(auth_user);
      this.bookingsDialogVisible = true;
    },
    deleteItem(item) {
      api.items
        .delete({
          id: item.id,
        })
        .then(() => this.refreshList());
    },
    async refreshList() {
      this.dialogVisible = false;
      this.reviewDialogVisible = false;
      this.bookingsDialogVisible = false;
      this.mapDialogVisible = false;
      this.hostDialogVisible = false;
      this.selectedItem = {};
      this.items = (await api.items.allItems()).content;
    },

    isUnauthorized() {
      console.log("auth", auth_user.roles);
      return auth_user.roles.includes("HOST");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
