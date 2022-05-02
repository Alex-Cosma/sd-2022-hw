<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
    </v-card-title>
    <v-btn @click="addUser">Add user</v-btn>
    <v-btn @click="jumpToProducts">Books</v-btn>
    <v-btn @click="exportPDF">Export PDF</v-btn>
    <v-btn @click="exportCSV">Export CSV</v-btn>
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
        { text: "Roles", value: "roles" },
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
    exportPDF() {
      api.users.reportPDF();
    },
    exportCSV() {
      api.users.reportCSV();
    },
    async refreshUserList() {
      this.userDialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
    jumpToProducts()
    {

      router.push("/items")

    },

  },
  async created() {
    this.users = await api.users.allUsers();
  },
};
</script>

<style scoped></style>
