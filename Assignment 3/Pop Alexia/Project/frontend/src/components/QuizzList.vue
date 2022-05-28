<template>
  <v-card>
    <v-card-title>
      Quizzes
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
        @input="searchAllBy"
      ></v-text-field>
      <v-btn @click="addQuizz">Create Quizz</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="quizzes"
      @click:row="editQuizz"
      @refresh="refreshList"
      :single-expand="singleExpand"
      :expanded.sync="expanded"
      item-key="id"
      show-expand
    >
      <template v-slot:expanded-item="{ headers, item }">
        <td>
          <v-data-table
            :headers="headersExpanded"
            :items="item.questions"
            @refresh="refreshList"
          ></v-data-table>
        </td>
      </template>
    </v-data-table>
    <QuizzDialog
      :opened="quizzDialogVisible"
      :quizz="selectedQuizz"
      :questions="questions"
      @refresh="refreshList"
    ></QuizzDialog>
  </v-card>
</template>

<script>
import api from "../api";
import QuizzDialog from "./QuizzDialog";

export default {
  name: "QuizzList",
  components: { QuizzDialog },
  data() {
    return {
      connected: false,
      expanded: [],
      quizzes: [],
      questions: [],
      singleExpand: false,
      search: "",
      headersExpanded: [
        {
          text: "Statement",
          align: "start",
          sortable: false,
          value: "statement",
        },
        { text: "Category", value: "category" },
      ],
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Description", value: "description" },
        { text: "", value: "data-table-expand" },
      ],
      quizzDialogVisible: false,
      selectedQuizz: {},
    };
  },
  methods: {
    editQuizz(quizz) {
      this.selectedQuizz = quizz;
      this.quizzDialogVisible = true;
    },
    addQuizz() {
      this.refreshQuestions();
      this.quizzDialogVisible = true;
    },
    async refreshList() {
      this.quizzDialogVisible = false;
      this.selectedQuizz = {};
      this.quizzes = await api.quizzes.allQuizzes();
      this.questions = await api.questions.allQuestions();
    },
    async refreshQuestions() {
      this.questions = await api.questions.allQuestions();
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
