<template>
  <v-card>
    <v-card-title>
      Properties
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
        :properties="properties"
        :search="search"
        @click:row="openProperty"
    ></v-data-table>
    <BookDialog
        :opened="dialogVisible"
        :property="selectedProperty"
        :user="authenticatedUser"
        @refresh="refreshList"
        @close="dialogVisible = !dialogVisible"
    ></BookDialog>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";
import router from "../router";
import { auth as store } from "../store/auth.module";

export default {
  name: "SearchPropertyList",
  components: { BookDialog },
  data() {
    return {
      properties: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Owner", value: "owner" },
        { text: "Address", value: "address" },
        { text: "Price", value: "price" },
        { text: "Description", value: "description" },
        { text: "NumberOfRooms", value: "numberOfRooms" },
        { text: "NumberOfBeds", value: "numberOfBeds" },
        { text: "NumberOfBathrooms", value: "numberOfBathrooms" },
        { text: "Kitchen", value: "kitchen" },
      ],
      dialogVisible: false,
      selectedProperty: {},
      authenticatedUser:store.getters.getUser(),                        //TODO
    };
  },
  methods: {
    openProperty(property) {
      router.push("/property");
    },
    async refreshList() {
      console.log("aaaaaaaaaaaaa");
      this.dialogVisible = false;
      this.selectedProperty = {};
      this.properties = await api.properties.getAllItemz();
      console.log("bbbbbbbbbbbbbbb");
      console.log(this.properties);
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
