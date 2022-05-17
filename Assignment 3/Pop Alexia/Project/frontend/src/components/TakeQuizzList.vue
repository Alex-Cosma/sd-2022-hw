<template>
  <v-card>
    <v-card-title>
      Click on a quizz and test your knowledge!
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
      :items="quizzes"
      @click:row="startQuizz"
      @refresh="refreshList"
    ></v-data-table>
    <TakeQuizzDialog
      :opened="quizzDialogVisible"
      :quizz="selectedQuizz"
      :correct-answers="correctAnswersSelectedQuizz"
      @refresh="refreshList"
    ></TakeQuizzDialog>
  </v-card>
</template>

<script>
import api from "../api";
import TakeQuizzDialog from "@/components/TakeQuizzDialog";

export default {
  name: "TakeQuizzList",
  components: { TakeQuizzDialog },
  data() {
    return {
      quizzes: [],
      singleExpand: false,
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Description", value: "description" },
        { text: "Points", value: "points" },
      ],
      quizzDialogVisible: false,
      selectedQuizz: {},
      correctAnswersSelectedQuizz: [],
    };
  },
  methods: {
    startQuizz(quizz) {
      for (let i = 0; i < quizz.questions.length; i++) {
        for (let j = 0; j < quizz.questions[i].answers.length; j++) {
          this.correctAnswersSelectedQuizz.push(quizz.questions[i].answers[j]);
        }
      }

      this.selectedQuizz = quizz;
      this.quizzDialogVisible = true;
    },
    async refreshList() {
      this.quizzDialogVisible = false;
      this.selectedQuizz = {};
      this.quizzes = await api.quizzes.allQuizzes();
    },
    async searchAllBy() {
      if (this.search !== "") {
        this.quizzes = await api.quizzes.filterAll(this.search);
      } else {
        this.search = "";
        this.quizzes = await api.quizzes.allQuizzes();
      }
    },
  },

  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
