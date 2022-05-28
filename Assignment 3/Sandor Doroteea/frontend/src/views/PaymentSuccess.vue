<template>
  <v-card>
    <div class="text-center">
      <span class="sr-only">Payment successful...</span>

      <div>
        <v-btn class="sr-only" @click="goBack">Finish</v-btn>
        <v-btn @click="generatePDF">Download tracks PDF</v-btn>
        <v-btn @click="generateCSV">Download tracks CSV</v-btn>
      </div>
    </div>

    <v-card-title>
      Payments
      <v-spacer></v-spacer>
    </v-card-title>
    <v-data-table :headers="headers" :items="payments"></v-data-table>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "Success",
  props: ["baseURL"],
  data() {
    return {
      token: null,
      sessionId: null,
      payments: [],
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "User id", value: "user_id" },
        { text: "Track id", value: "track_id" },
      ],
    };
  },
  methods: {
    goBack() {
      this.$router.push("/tracksApi");
    },
    generatePDF() {
      api.payment.generatePDF({
        user_id: this.$store.getters["auth/getUserID"],
      });
    },
    generateCSV() {
      api.payment.generateCSV({
        user_id: this.$store.getters["auth/getUserID"],
      });
    },
    async refreshList() {
      this.payments = await api.payment.findAll();
    },
  },
  mounted() {
    this.token = localStorage.getItem("token");
    this.sessionId = localStorage.getItem("sessionId");
    this.refreshList();
  },
  created() {
    this.refreshList();
  },
};
</script>
