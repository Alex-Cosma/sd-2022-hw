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
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
    >
      <template v-slot:item="row">
        <tr>
          <td>{{ row.item.title }}</td>
          <td>{{ row.item.author }}</td>
          <td>{{ row.item.genre }}</td>
          <td>{{ row.item.quantity }}</td>
          <td>{{ row.item.price }}</td>
          <td>
            <v-btn
              color="primary"
              :disabled="isOutOfStock(row.item)"
              depressed
              @click="sellItem(row.item)"
            >
              Sell
            </v-btn>
          </td>
        </tr>
      </template>
    </v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";

const auth_user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "ItemListReg",
  components: { ItemDialog },

  data() {
    return {
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
          text: "Quantity",
          align: "start",
          sortable: false,
          value: "quantity",
        },
        {
          text: "Price",
          align: "start",
          sortable: false,
          value: "price",
        },
        {
          text: "",
          align: "start",
          sortable: false,
          value: "",
        },
        {
          text: "",
          align: "start",
          sortable: false,
          value: "",
        },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    sellItem(item) {
      api.items
        .sell({
          id: item.id,
          newQuantity: item.quantity - 1,
        })
        .then(() => this.refreshList());
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },

    isUnauthorized() {
      return auth_user.roles[0] !== "EMPLOYEE";
    },

    isOutOfStock(item) {
      if (!this.isUnauthorized()) {
        return item.quantity === 0;
      } else {
        return true;
      }
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
