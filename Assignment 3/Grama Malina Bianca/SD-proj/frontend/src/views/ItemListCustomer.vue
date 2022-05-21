<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      >
      </v-text-field>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            class="mx-2"
            fab
            dark
            small
            @click="openOrders"
            color="blue"
            v-bind="attrs"
            v-on="on"
          >
            <v-icon dark>list</v-icon>
          </v-btn>
        </template>
        <span>View Orders</span>
      </v-tooltip>
      <v-badge
        :content="this.selectedItems.length"
        :value="numOfItems"
        @click.native="openCart"
        color="green"
      >
        <v-icon>mdi-cart</v-icon>
      </v-badge>
    </v-card-title>
    <v-data-table :headers="headers" :items="items" :search="search">
      <template v-slot:item="row">
        <tr>
          <td>{{ row.item.title }}</td>
          <td>{{ row.item.author }}</td>
          <td>{{ row.item.genre }}</td>
          <td>{{ row.item.pages }}</td>
          <td>{{ row.item.year }}</td>
          <td>{{ row.item.quantity }}</td>
          <td>{{ row.item.description }}</td>
          <td>
            <v-btn
              color="blue"
              :disabled="isUnauthorized()"
              depressed
              @click="addItemToCart(row.item)"
            >
              Add to Cart
            </v-btn>
          </td>
        </tr>
      </template>
    </v-data-table>
    <CartComponent
      :opened="dialogVisible"
      :selectedItems="selectedItems"
      @show-items="changeSelectedItems($event)"
      @refresh="refreshList"
    ></CartComponent>
    <OrderListComponent
      :orders="orders"
      :opened="orderListVisible"
      @refresh="refreshList"
    ></OrderListComponent>
  </v-card>
</template>

<script>
import api from "../api";
import CartComponent from "../components/CartComponent";
import OrderListComponent from "@/components/OrderListComponent";

const auth_user = JSON.parse(localStorage.getItem("user"));
export default {
  name: "ItemListCustomer",
  components: { OrderListComponent, CartComponent },
  data() {
    return {
      orders: [],
      items: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        {
          text: "Author",
          align: "start",
          sortable: false,
          value: "author",
        },
        {
          text: "Genre",
          align: "start",
          sortable: false,
          value: "genre",
        },
        {
          text: "Pages",
          align: "start",
          sortable: false,
          value: "pages",
        },
        {
          text: "Year",
          align: "start",
          sortable: false,
          value: "year",
        },
        {
          text: "Quantity",
          align: "start",
          sortable: false,
          value: "quantity",
        },
        {
          text: "Description",
          align: "start",
          sortable: false,
          value: "description",
        },
      ],
      dialogVisible: false,
      orderListVisible: false,
      selectedItems: [],
      numOfItems: 0,
    };
  },
  methods: {
    openCart() {
      this.dialogVisible = true;
    },
    addItemToCart(item) {
      this.selectedItems.push(item);
      this.numOfItems++;
      this.dialogVisible = false;
      this.orderListVisible = false;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.orderListVisible = false;
      this.items = await api.items.allItems();
      this.orders = await api.orders.ordersForCustomer(auth_user.id);
    },
    isUnauthorized() {
      return auth_user.roles.includes("CUSTOMER") && this.isOutOfStock();
    },
    isOutOfStock() {
      return this.items.quantity === 0;
    },
    changeSelectedItems(items) {
      this.selectedItems = items;
      this.numOfItems = items.length;
    },
    async openOrders() {
      this.orderListVisible = true;
      this.orders = await api.orders.ordersForCustomer(auth_user.id);
      console.log(this.orders);
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
