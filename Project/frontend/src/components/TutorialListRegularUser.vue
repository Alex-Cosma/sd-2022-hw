<template>
  <v-card>
    <v-card-title>
      Tutorials
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="tutorials"
        @click:row="downloadTutorial"
        @refresh="refreshList"
    ></v-data-table>
    <TutorialDialogRegularUser
        :opened="dialogVisible"
        :tutorial="selectedTutorial"
        @refresh="refreshList"
    ></TutorialDialogRegularUser>
  </v-card>
</template>

<script>
import api from "../api";
import TutorialDialogRegularUser from "./TutorialDialogRegularUser";
export default {
  name: "TutorialListRegularUser",
  components: { TutorialDialogRegularUser },
  data() {
    return {
      tutorials: [],
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
      ],
      dialogVisible: false,
      selectedTutorial: {},
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.selectedTutorial = {};
      this.tutorials = await api.tutorials.getAll();
    },
    downloadTutorial(tutorial) {
      this.selectedTutorial = tutorial;
      this.dialogVisible = true;
    },
  },
  created() {
    this.refreshList();
  },
}
</script>

<style scoped>

</style>