<template>
  <v-card>
    <v-card-title>
      Stocks
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
<!--      <v-btn @click="addStock">Add Stock</v-btn>-->
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="stocks"
      :search="search"
      @click:row="editStock"
    ></v-data-table>

    <v-card-title>
      Investments
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-btn @click="mail">Email Investments</v-btn>
    <v-data-table
      :headers="headers2"
      :items="investments"
      :search="search"
    ></v-data-table>

    <StockDialog
      :opened="dialogVisible"
      :stock="selectedStock"
      @refresh="refreshList"
    ></StockDialog>
  </v-card>
</template>

<script>
import api from "../api";
import StockDialog from "../components/StockDialog";
const user = JSON.parse(localStorage.getItem("user"));
export default {
  name: "StockList",
  components: { StockDialog},
  data() {
    return {
      stocks: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Symbol", value: "symbol" },
        { text: "Price", value: "stockPrices[0].closePrice" },
      ],
      headers2: [
        {
          text: "Investment",
          align: "start",
          sortable: false,
          value: "symbol",
        },
        { text: "Quantity", value: "quantity" },
      ],
      dialogVisible: false,
      selectedStock: {},
      //selectedUser: {},
      searchDialogVisible: false,
      searchStock: [],
      investments: [],
    };
  },
  methods: {
    editStock(stock) {
      this.selectedStock = stock;
      this.dialogVisible = true;
    },

    mail() {
      api.users.mail(user);
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedStock = {};
      this.stocks = await api.stocks.allStocks();
      this.investments = await api.investments.allInvestments(user);
      console.log(user.id);
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
