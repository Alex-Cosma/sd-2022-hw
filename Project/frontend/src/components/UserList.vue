<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-btn @click="addUser">Add User</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="users"
        @click:row="editUser"
        @refresh="refreshList"
    ></v-data-table>
    <UserDialog
        :opened="dialogVisible"
        :user="selectedUser"
        @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
export default {
  name: "UserList",
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
          value: "username",
        },
        { text: "Email", value: "email" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    addUser() {
      this.dialogVisible = true;
    },
    editUser(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
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
}
</script>

<style scoped></style>