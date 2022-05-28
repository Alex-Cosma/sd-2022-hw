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
      <v-btn @click="addTraining">Add Training</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="trainings"
        @click:row="editTraining"
        @refresh="refreshList"
    ></v-data-table>
    <TrainingDialog
        :opened="dialogVisible"
        :training="selectedTraining"
        @refresh="refreshList"
    ></TrainingDialog>
  </v-card>
</template>

<script>
import api from "../api";
import TrainingDialog from "./TrainingDialog";
export default {
  name: "TrainingList",
  components: { TrainingDialog },
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
        { text: "User", value: "user" },
      ],
      dialogVisible: false,
      selectedTraining: {},
    };
  },
  methods: {
    addTraining() {
      this.dialogVisible = true;
    },
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