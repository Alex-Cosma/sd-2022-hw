<template>
  <v-card>
    <v-card-title>
      Reports
    </v-card-title>
    <div class="padded">
      Here you can generate reports of your flight activity.
    </div>
    <v-btn @click="getCSV" class="padded">
      <v-icon>mdi-file-document-outline</v-icon>
      <span>Download CSV</span>
    </v-btn>
  </v-card>
</template>

<script>
import api from "@/api";
import {auth as store} from "../store/auth.module";

export default {
  name: "Reports",
  methods: {
    getCSV() {
      api.reports.getCSV(store.state.user.id);
    },
  },
};
</script>

<style>
.padded {
  margin: 1rem;
}
</style>
