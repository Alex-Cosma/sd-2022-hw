<template>
  <v-card>
    <v-card-title>
      Courts
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="courts"
      :search="search"
      @click:row="editCourt"
    ></v-data-table>
    <CourtDialog
      :opened="dialogVisible"
      :court="selectedCourt"
      @refresh="refreshList"
    ></CourtDialog>
  </v-card>
</template>

<script>
import api from "../api";
import CourtDialog from "../components/CourtDialog";

export default {
  name: "CourtList",
  components: { CourtDialog },
  data() {
    return {
      courts: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Availability", value: "availability" },
        { text: "Sport Type", value: "sportType" },
      ],
      dialogVisible: false,
      selectedCourt: {},
    };
  },
  methods: {
    editCourt(court) {
      this.selectedCourt = court;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedCourt = {};
      this.courts = await api.courts.allCourts();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
