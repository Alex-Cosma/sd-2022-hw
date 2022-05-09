<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search item"
        single-line
        hide-details
      ></v-text-field>
      <v-btn small @click="addItem" v-if="this.$store.getters['auth/isAdmin']"
        >Add Book</v-btn
      >
      <v-btn
        small
        @click="generatePDF"
        v-if="this.$store.getters['auth/isAdmin']"
        >Generate PDF</v-btn
      >
      <v-btn
        small
        @click="generateCSV"
        v-if="this.$store.getters['auth/isAdmin']"
        >Generate CSV</v-btn
      >
    </v-card-title>
    <v-data-table :headers="headers" :items="items" :search="search">
      <template
        v-slot:item.actions="{ item }"
        v-if="this.$store.getters['auth/isAdmin']"
      >
        <v-icon small class="mr-2" @click="editItem(item)"> mdi-pencil </v-icon>
        <v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
        <v-icon small @click="sellItem(item)"> mdi-store </v-icon>
      </template>
      <template v-slot:item.actions="{ item }" v-else>
        <v-icon small @click="sellItem(item)"> mdi-store </v-icon>
      </template>
    </v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
    <DeleteDialog
      :opened="dialogDelete"
      :item="selectedItem"
      @refresh="refreshList"
    >
    </DeleteDialog>
    <SellDialog
      :opened="dialogSell"
      :item="selectedItem"
      @refresh="refreshList"
    >
    </SellDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";
import DeleteDialog from "../components/DeleteDialog";
import SellDialog from "../components/SellDialog";

export default {
  name: "ItemList",
  components: { ItemDialog, DeleteDialog, SellDialog },
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
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "Actions", value: "actions", sortable: false },
      ],
      dialogVisible: false,
      dialogDelete: false,
      dialogSell: false,
      selectedItem: {},
    };
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addItem() {
      this.dialogVisible = true;
    },
    deleteItem(item) {
      this.selectedItem = item;
      this.dialogDelete = true;
    },
    sellItem(item) {
      this.selectedItem = item;
      this.dialogSell = true;
    },
    generatePDF() {
      console.log("PDF");
      api.items.generatePDF();
    },
    generateCSV() {
      console.log("CSV");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.dialogDelete = false;
      this.dialogSell = false;
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
