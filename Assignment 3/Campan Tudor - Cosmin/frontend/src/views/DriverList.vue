<template>
  <v-card>
    <v-card-title>
      Drivers
      <v-spacer></v-spacer>
    </v-card-title>

    <v-btn @click="addUser">Add Driver</v-btn>
    <v-btn @click="jumpToProducts">Fines</v-btn>
    <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
    ></v-text-field>
    <v-data-table
        :headers="headers"
        :items="drivers"
        :search="search"
        @click:row="editUser"
    ></v-data-table>
    <DriverDialog
        :opened="driverDialogVisible"
        :driver="selectedDriver"
        @refresh="refreshUserList"
    ></DriverDialog>
  </v-card>
</template>

<script>
import api from "../api";
import router from "../router";
import DriverDialog from "@/components/DriverDialog";
export default {
  name: "DriverList",
  components: { DriverDialog },
  data() {
    return {
      drivers: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
        { text: "Section", value: "sectionNumber" },
      ],

      driverDialogVisible: false,
      selectedDriver: {},
    };
  },
  methods: {
    editUser(driver) {
      this.selectedDriver = driver;
      this.driverDialogVisible = true;
    },
    addUser() {
      this.driverDialogVisible = true;
    },
    async refreshUserList() {
      this.driverDialogVisible = false;
      this.selectedDriver = {};
      this.drivers = await api.drivers.allDrivers();
    },
    jumpToProducts()
    {

      router.push("/fines")

    },

  },
  async created() {
    this.drivers = await api.drivers.allDrivers();
  },
};
</script>

<style scoped></style>

<style scoped></style>
