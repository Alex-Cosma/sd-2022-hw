<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
    </v-card-title>
    <v-btn @click="addUser">Add user</v-btn>
    <v-btn @click="jumpToProducts">Drivers</v-btn>
    <v-btn @click="jumpToFines">Fines</v-btn>
    <v-btn @click="downloadFile">Download Report</v-btn>
    <v-data-table
        :headers="headers"
        :items="users"
        @click:row="editUser"
    ></v-data-table>
    <UserDialog
        :opened="userDialogVisible"
        :user="selectedUser"
        @refresh="refreshUserList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import router from "../router";
import {BASE_URL} from "@/api/http";
import axios from "axios";

export default {
  name: "AdminView",
  components: { UserDialog },
  data() {
    return {
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
      ],

      userDialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    editUser(user) {
      this.selectedUser = user;
      this.userDialogVisible = true;
    },
    addUser() {
      this.userDialogVisible = true;
    },
    async refreshUserList() {
      this.userDialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
    jumpToProducts()
    {

      router.push("/drivers")

    },
    jumpToFines()
    {

      router.push("/fines")

    },
    downloadFile() {
      axios({
        url: BASE_URL+"/report",
        method: 'GET',
        responseType: 'blob',
      }).then((response) => {
        var fileURL = window.URL.createObjectURL(new Blob([response.data]));
        var fURL = document.createElement('a');

        fURL.href = fileURL;
        fURL.setAttribute('download', 'file.pdf');
        document.body.appendChild(fURL);

        fURL.click();
      });
    },

  },
  async created() {
    this.users = await api.users.allUsers();
  },

};
</script>

<style scoped></style>

<style scoped></style>
