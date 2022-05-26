<template>
  <v-card>
    <v-card-title>
      Categories
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn v-if="isAdmin" @click="addCategory">Add Category</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="categories"
      :search="search"
      @click:row="goToCategory"
    ></v-data-table>
  </v-card>
</template>

<script>
import api from "@/api";
import { auth as store } from "@/store/auth.module";

export default {
  name: "CategoryList",
  data() {
    return {
      categories: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          value: "name",
        },
        { text: "Description", value: "description" },
      ],
      dialogVisible: false,
    };
  },
  methods: {
    goToCategory(category) {
      this.$router.push({
        path: "/threads-from-category/" + category.id,
      });
    },
    addCategory() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.categories = await api.categories.allCategories();
    },
  },
  created() {
    this.refreshList();
  },
  computed: {
    isAdmin() {
      return store.getters.isAdmin(store.state);
    },
  },
};
</script>

<style scoped></style>
