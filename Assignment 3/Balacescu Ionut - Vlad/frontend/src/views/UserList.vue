<!--
<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search user"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table :headers="headersUser" :items="users" :search="search">
      <template v-slot:item.actions="{ user }">
        <v-icon small class="mr-2" @click="editUser(user)"> mdi-pencil </v-icon>
        <v-icon small @click="deleteUser(user)"> mdi-delete </v-icon>
      </template>
    </v-data-table>
    <UserDialog
      :opened="dialogUser"
      :item="selectedUser"
      @refresh="refreshList"
    ></UserDialog>
    <DeleteUserDialog
      :opened="dialogDelete"
      :item="selectedUser"
      @refresh="refreshList"
    ></DeleteUserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import DeleteUserDialog from "../components/DeleteUserDialog";

export default {
  name: "UserList",
  components: { DeleteUserDialog, UserDialog },
  data() {
    return {
      users: [],
      search: "",
      headersUser: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
        /*{ text: "Roles", value: "roles" },*/
        { text: "Actions", value: "actions", sortable: false },
      ],
      dialogUser: false,
      dialogDelete: false,
      selectedUser: {},
    };
  },
  methods: {
    editUser(user) {
      this.selectedUser = user;
      console.log(this.selectedUser.name);
      this.dialogUser = true;
    },
    deleteUser(user) {
      this.selectedUser = user;
      this.dialogDelete = true;
    },
    async refreshList() {
      this.dialogUser = false;
      this.dialogDelete = false;
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
-->
<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search item"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table :headers="headersUser" :items="users" :search="search">
      <template v-slot:item.actions="{ item }">
        <v-icon small @click="editUser(item)"> mdi-pencil </v-icon>
        <v-icon small @click="deleteUser(item)"> mdi-delete </v-icon>
      </template>
    </v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedUser"
      @refresh="refreshList"
    ></UserDialog>
    <DeleteUserDialog
      :opened="dialogDelete"
      :user="selectedUser"
      @refresh="refreshList"
    ></DeleteUserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import DeleteUserDialog from "../components/DeleteUserDialog";

export default {
  name: "UserList",
  components: { UserDialog, DeleteUserDialog },
  data() {
    return {
      users: [],
      search: "",
      headersUser: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "username",
        },
        { text: "Email", value: "email" },
        { text: "Password", value: "password" },
        { text: "Actions", value: "actions", sortable: false },
      ],
      dialogVisible: false,
      dialogDelete: false,
      selectedUser: {},
      selectedIndex: -1,
    };
  },
  methods: {
    editUser(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    deleteUser(user) {
      this.selectedUser = user;
      this.dialogDelete = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.dialogDelete = false;
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
