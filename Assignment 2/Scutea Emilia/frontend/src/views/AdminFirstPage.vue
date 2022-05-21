<template>
  <v-card>
    <v-card-title>
      Administrator Page
      <v-btn @click="crudBooks">CRUD on Books</v-btn>
      <v-btn @click="crudUsers">CRUD on Users</v-btn>
      <v-btn @click="pdfBox">Generate PDF report</v-btn>
      <v-btn @click="csv">Generate CSV report</v-btn>
    </v-card-title>
  </v-card>
</template>

<script>
import api from "../api";
import router from "../router";
export default {
  name: "AdminFirstPage",
  data() {
    return {
      users: [],
      search: "",
      headers: [],
    };
  },
  methods: {
    crudUsers() {
      router.push("/users");
    },
    crudBooks() {
      router.push("/admin/books");
    },
    pdfBox() {
      api.admin.generateReport("PDF");
    },
    csv() {
      api.admin.generateReport("CSV");
    },
  },
};
</script>

<style scoped></style>
