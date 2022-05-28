<template>
  <v-card>
    <v-card-title>
      Fines
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Fine</v-btn>
      <v-btn @click="back">Back</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="items"
        :search="search"
        @click:row="editItem"
    ></v-data-table>
    <FineDialog
        :opened="dialogVisible"
        :item="selectedItem"
        @refresh="refreshList"
    ></FineDialog>
  </v-card>
</template>

<script>

import FineDialog from "@/components/FineDialog";
import api from "@/api";

export default {
  name: "FineList",
  components: { FineDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Type",
          align: "start",
          sortable: false,
          value: "type",
        },
        { text: "Description", value: "description" },
        { text: "Price", value: "price" },
      ],
      dialogVisible: false,
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
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.fines.allItems();
    },
    back()
    {
      this.$router.go(-1)
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>

