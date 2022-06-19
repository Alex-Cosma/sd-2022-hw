<template>
  <v-card>
    <v-btn @click="viewProperties">View Properties</v-btn>
    <v-btn @click="viewUsers">View Users</v-btn>
    <v-btn @click="generateReportPDF">Generate Report PDF</v-btn>
    <v-btn @click="generateReportCSV">Generate Report CSV</v-btn>
  </v-card>
</template>

<script>
import router from "../router";
import api from "../api";

export default {
  name: "AdminOptions",
  methods: {
    viewProperties() {
      router.push("/admin-properties");
    },
    viewUsers() {
      router.push("/users");
    },
    generateReportPDF() {
      console.log("PDF");
      api.properties.generateReport("PDF");
    },
    generateReportCSV() {
      console.log("CSV");
      api.properties.generateReport("CSV");
    },
  },
};
</script>
