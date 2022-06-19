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
      <v-btn @click="addProperty">Add Property</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :properties="properties"
        :search="search"
        @click:row="editProperty"
    ></v-data-table>
    <PropertyDialog
        :opened="dialogVisible"
        :property="selectedProperty"
        @refresh="refreshList"
        @close="dialogVisible = !dialogVisible"
    ></PropertyDialog>
  </v-card>
</template>

<script>
import api from "../api";
import PropertyDialog from "../components/PropertyDialog";
import { auth as store } from "../store/auth.module";

export default {
  name: "PropertyListUser",
  components: { PropertyDialog },
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
    };
  },
  methods: {
    editProperty(property) {
      this.selectedProperty = property;
      this.dialogVisible = true;
    },
    addProperty() {
      this.selectedProperty = {};
      this.dialogVisible = true;
    },
    async refreshList() {
      console.log("aaaaaaaaaaaaa");
      this.dialogVisible = false;
      this.selectedProperty = {};
      this.properties = await api.properties.getPropertiesByOwner(store.getters.getUser());     //TODO
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



