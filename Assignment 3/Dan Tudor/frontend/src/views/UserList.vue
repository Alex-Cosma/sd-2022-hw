<template>
  <v-card>
    <v-card-title>
      Users and Stocks
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add User</v-btn>
      <v-btn @click="addStock">Add Stock</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <v-data-table
      :headers="headers2"
      :items="stocks"
      @click:row="editStock"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedUser"
      @refresh="refreshList"
    ></UserDialog>
    <StockDialog
      :opened="dialogVisible2"
      :stock="selectedStock"
      @refresh="refreshList"
      ></StockDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import StockDialog from "../components/StockDialog";

export default {
  name: "UserList",
  components: { UserDialog, StockDialog },
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
      headers2: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Symbol", value: "symbol" },
        { text: "Price", value: "stockPrices[0].closePrice" },
      ],
      dialogVisible: false,
      dialogVisible2: false,
      selectedUser: {},
      stocks: [],
      selectedStock: {},
    };
  },
  methods: {
    addItem() {
      this.dialogVisible = true;
    },
    editItem(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
      this.stocks = await api.stocks.allStocks();
    },
    addStock() {
      this.dialogVisible2 = true;
    },
    editStock(stock){
      this.selectedStock = stock;
      this.dialogVisible2 = true;
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
