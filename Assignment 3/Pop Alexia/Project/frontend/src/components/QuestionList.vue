<template>
  <v-card>
    <v-card-title>
      Questions
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
        @input="searchAllBy"
      ></v-text-field>
      <v-btn @click="addQuestion">Add Question</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="questions"
      @click:row="editQuestion"
      @refresh="refreshList"
      :single-expand="singleExpand"
      :expanded.sync="expanded"
      item-key="id"
      show-expand
      @update:expanded="itemExpanded"
    >
      <template v-slot:expanded-item="{ headers, item }">
        <td>
          <v-data-table
            :headers="headersExpanded"
            :items="item.answers"
            @click:row="editAnswer"
            @refresh="refreshList"
          ></v-data-table>
        </td>
        <td>
          <v-btn @click="addAnswer">Add Answer</v-btn>
        </td>
      </template>
    </v-data-table>
    <QuestionDialog
      :opened="questionDialogVisible"
      :question="selectedQuestion"
      @refresh="refreshList"
    ></QuestionDialog>
    <AnswerDialog
      :opened="answerDialogVisible"
      :answer="selectedAnswer"
      :question="selectedQuestion"
      @refresh="refreshLocal"
    ></AnswerDialog>
  </v-card>
</template>

<script>
import api from "../api";
import QuestionDialog from "./QuestionDialog";
import AnswerDialog from "./AnswerDialog";

export default {
  name: "QuestionList",
  components: { QuestionDialog, AnswerDialog },
  data() {
    return {
      expanded: [],
      questions: [],
      singleExpand: false,
      search: "",
      headersExpanded: [
        {
          text: "Answer",
          align: "start",
          sortable: false,
          value: "answer",
        },
        { text: "Correct", value: "correct" },
      ],
      headers: [
        {
          text: "Statement",
          align: "start",
          sortable: false,
          value: "statement",
        },
        { text: "Category", value: "category" },
        { text: "", value: "data-table-expand" },
      ],
      questionDialogVisible: false,
      answerDialogVisible: false,
      selectedAnswer: {},
      selectedQuestion: {},
    };
  },
  methods: {
    itemExpanded() {
      this.selectedQuestion = this.expanded[0];
    },
    editAnswer(answer) {
      this.selectedAnswer = answer;
      this.answerDialogVisible = true;
    },
    addAnswer() {
      this.answerDialogVisible = true;
    },
    editQuestion(question) {
      this.selectedQuestion = question;
      this.questionDialogVisible = true;
    },
    addQuestion() {
      this.questionDialogVisible = true;
    },
    async refreshLocal() {
      this.questionDialogVisible = false;
      this.answerDialogVisible = false;
      this.selectedAnswer = {};
      this.questions = await api.questions.allQuestions();
    },
    async refreshList() {
      this.questionDialogVisible = false;
      this.answerDialogVisible = false;
      this.selectedQuestion = {};
      this.selectedAnswer = {};
      this.questions = await api.questions.allQuestions();
    },
    async searchAllBy() {
      if (this.search !== "") {
        this.questions = await api.questions.filterAll(this.search);
      } else {
        this.search = "";
        this.questions = await api.questions.allQuestions();
      }
    },
  },

  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
