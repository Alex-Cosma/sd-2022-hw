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
      <v-btn @click="addItem" :disabled="isUnauthorized()">Add Book</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editItem"
    >
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
              color="green"
              :disabled="isUnauthorized()"
              depressed
              @click="editItem(row.item)"
            >
              Edit
            </v-btn>
          </td>
          <td>
            <v-btn
              color="red"
              :disabled="isUnauthorized()"
              depressed
              @click="deleteItem(row.item)"
            >
              Delete
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
  name: "ItemList",
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
      api.items
        .delete({
          id: item.id,
        })
        .then(() => this.refreshList());
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
    isUnauthorized() {
      return !auth_user.roles.includes("MANAGER");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
