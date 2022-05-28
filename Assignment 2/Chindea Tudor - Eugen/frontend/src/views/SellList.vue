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
      @click:row="sellItem"
    ></v-data-table>
   <SellDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></SellDialog>
  </v-card>
</template>

<script>
import api from "../api";
import SellDialog from "../components/SellDialog";
export default {
  name: "SellList",
  components: {
    SellDialog,
  },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Tile",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    sellItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
      
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
