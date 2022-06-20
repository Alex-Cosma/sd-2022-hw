<template>
  <v-card>
    <v-card-title>
      Trainings enrolled
      <v-spacer></v-spacer>
      <v-btn @click="exportPDF">PDF Report</v-btn>
      <v-btn @click="exportCSV">CSV Report</v-btn>
    </v-card-title>
  </v-card>
</template>

<script>
import api from "../api";
export default {
  name: "ReportList",
  methods: {
    exportPDF() {
      const user = JSON.parse(localStorage.getItem("user"));
      api.trainings.report("PDF", user.username);
    },
    exportCSV() {
      const user = JSON.parse(localStorage.getItem("user"));
      api.trainings.report("CSV", user.username);
    },
  },
};
</script>

<style scoped></style>
