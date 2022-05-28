<template>
  <v-card>
    <v-card-title>
      Users
      <v-btn small @click="switchToItems()">Items</v-btn>
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
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
        <UserDialog
          :opened="dialogVisible"
          :user="selectedUser"
          @refresh="refreshList()"
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
        { text: "Password", value: "password" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    editUser(user) {
      this.selectedUser = user;
        this.dialogVisible = true;
    },
    switchToItems() {
        this.$router.push('/itemsAdmin');
    },
    async refreshList() {
        this.dialogVisible = false;
        this.selectedUser = {};
        this.users = await api.users.allUsers();
    },
  },
  async created() {
    this.users = await api.users.allUsers();
  },
};
</script>

<style scoped></style>
