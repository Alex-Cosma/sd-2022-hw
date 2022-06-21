<template>
  <v-card>
    <v-card-title>
      Trainings
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
          @input="searchAllBy"
      ></v-text-field>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="trainings"
        @click:row="editTraining"
        @refresh="refreshList"
    ></v-data-table>
    <RegularTrainingDialog
        :opened="dialogVisible"
        :training="selectedTraining"
        @refresh="refreshList"
    ></RegularTrainingDialog>
  </v-card>
</template>

<script>
import api from "../api";
import RegularTrainingDialog from "./RegularTrainingDialog";
export default {
  name: "RegularTrainingList",
  components: { RegularTrainingDialog },
  data() {
    return {
      trainings: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Type", value: "type" },
        { text: "Date", value: "date" },
        { text: "Location", value: "location" },
        { text: "Trainer", value: "user" },
      ],
      dialogVisible: false,
      selectedTraining: {},
    };
  },
  methods: {
    editTraining(training) {
      this.selectedTraining = training;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedTraining = {};
      this.trainings = await api.trainings.allTrainings();
    },
    async searchAllBy() {
      if (this.search !== "") {
        this.trainings = await api.trainings.filter(this.search);
      } else {
        this.search = "";
        this.trainings = await api.trainings.allTrainings();
      }
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>