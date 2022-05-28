<template>
  <v-card>
    <v-card-title>
      Quizz History Reports
      <v-spacer></v-spacer>
      <v-btn @click="exportPDF">PDF Report</v-btn>
      <v-btn @click="exportCSV">DOCX Report</v-btn>
    </v-card-title>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "ReportList",
  methods: {
    exportPDF() {
      api.quizzSession.exportReport(
        "PDF",
        JSON.parse(localStorage.getItem("user")).id
      );
    },
    exportCSV() {
      api.quizzSession.exportReport(
        "DOCX",
        JSON.parse(localStorage.getItem("user")).id
      );
    },
  },
};
</script>

<style scoped></style>
