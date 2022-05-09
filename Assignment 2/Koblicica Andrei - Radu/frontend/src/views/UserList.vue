<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-btn @click="addUser">Add User</v-btn>
      <v-btn @click="deleteUser">Delete User</v-btn>
      <v-btn @click="updateUser">Update User</v-btn>
      <v-btn @click="generatePdfReport">PDF Report</v-btn>
      <v-btn @click="generateCsvReport">CSV Report</v-btn>
    </v-card-title>
    <v-data-table :headers="userHeaders" :items="users" @click:row="selectUser">
    </v-data-table>

    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Book</v-btn>
      <v-btn @click="deleteItem">Delete Book</v-btn>
      <v-btn @click="updateItem">Update Book</v-btn>
    </v-card-title>
    <v-data-table
      :headers="itemHeaders"
      :items="items"
      :search="search"
      @click:row="selectItem"
    >
    </v-data-table>
    <ItemDialog
      :opened="itemDialogVisible"
      :book="selectedItem"
      @refresh="refreshItems"
    ></ItemDialog>
    <UserDialog
      :opened="userDialogVisible"
      :user="selectedUser"
      @refresh="refreshUsers"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "@/components/ItemDialog";
import UserDialog from "@/components/UserDialog";

export default {
  name: "UserList",
  components: { ItemDialog, UserDialog },
  data() {
    return {
      users: [],
      items: [],
      search: "",
      itemHeaders: [
        {
          text: "Title",
          align: "start",
          sortable: true,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Price", sortable: true, value: "price" },
        { text: "Quantity", sortable: true, value: "quantity" },
      ],
      userHeaders: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
      ],
      userDialogVisible: false,
      itemDialogVisible: false,
      selectedItem: {},
      selectedUser: {},
    };
  },
  methods: {
    updateItem() {
      if (this.selectedItem) {
        this.itemDialogVisible = true;
      }
    },
    addItem() {
      this.selectedUser={};
      this.itemDialogVisible = true;
    },
    deleteItem() {
      if (this.selectedItem) {
        api.items.delete(this.selectedItem).then(() => this.refreshItems());
      }
    },
    updateUser() {
      if (this.selectedUser) {
        this.userDialogVisible = true;
      }
    },
    addUser() {
      this.userDialogVisible = true;
    },
    deleteUser() {
      if (this.selectedUser) {
        api.users.delete(this.selectedUser).then(() => this.refreshUsers());
      }
    },
    selectItem(item) {
      this.selectedItem = item;
    },
    selectUser(user) {
      this.selectedUser = user;
    },
    generatePdfReport() {
      api.users.generateReport("PDF");
    },
    generateCsvReport() {
      api.users.generateReport("CSV");
    },

    async refreshItems() {
      this.itemDialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
    async refreshUsers() {
      this.userDialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
  },
  created() {
    this.refreshUsers();
    this.refreshItems();
  },
};
</script>

<style scoped></style>
