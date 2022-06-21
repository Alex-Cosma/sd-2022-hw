<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="goToCourts">Go to courts</v-btn>
      <v-btn @click="downloadReservations">Download reservations</v-btn>
      <v-snackbar
        v-model="snackbar"
        >
        {{ snackbarText }}
        <v-btn color="green" text @click="snackbar = false">Close</v-btn>
      </v-snackbar>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="deleteUser"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedUser"
      @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>



<!--@click:row="selectedUser"-->

<script>
import api from "../api";
import UserDialog from "@/components/UserDialog";

export default {
  name: "UserList",
  components: { UserDialog },
  data() {
    return {
      snackbar: false,
      snackbarText: "Downloaded reservations",
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    deleteUser(user){
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    downloadReservations(){
      api.users.downloadReservations();
      this.snackbar = true;
    },
    goToCourts(){
      this.$router.push("/users/courts-admin");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
