<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        label="Search"
        v-model="filterText"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="searchItems">Search</v-btn>
      <v-btn @click="refreshList">Clear filter</v-btn>
      <v-btn @click="sellItem">Sell book</v-btn>
    </v-card-title>
    <v-data-table :headers="headers" :items="items" @click:row="selectItem">
    </v-data-table>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "ItemList",
  data() {
    return {
      items: [],
      filterText: "",
      headers: [
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
      selectedItem: {},
    };
  },
  methods: {
    async searchItems() {
      this.items = await api.items.filteredItems(this.filterText );
    },

    sellItem() {
      if (this.isInStock(this.selectedItem)) {
        api.items
          .sell({
            id: this.selectedItem.id,
            quantity: this.selectedItem.quantity,
          })
          .then(() => this.refreshList());
      }
    },
    isInStock(item) {
      return item.quantity > 0;
    },
    selectItem(item) {
      this.selectedItem = item;
    },
    async refreshList() {
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
