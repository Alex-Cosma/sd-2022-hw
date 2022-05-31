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
      <v-btn @click="addCourt">Add Court</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="courtsForAdmin"
      :search="search"
      @click:row="editCourt"
    ></v-data-table>
    <CourtDialogForAdmin
      :opened="dialogVisible"
      :court="selectedCourt"
      @refresh="refreshList"
    ></CourtDialogForAdmin>
  </v-card>
</template>

<script>
import api from "../api";
import CourtDialogForAdmin from "@/components/CourtDialogForAdmin";

export default {
  name: "CourtListForAdmin",
  components: { CourtDialogForAdmin },
  data() {
    return {
      courtsForAdmin: [],
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
    addCourt() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedCourt = {};
      this.courtsForAdmin = await api.users.allCourtsForAdmin();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
