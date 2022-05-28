<template>
  <v-app>
    <v-dialog
      transition="dialog-bottom-transition"
      max-width="1500"
      :value="opened"
    >
      <template>
        <v-card v-if="!started && opened">
          <v-toolbar color="black" dark>
            {{ "Take the quizz" }}
            <v-alert v-if="showAlert" type="error" value="errors">{{
              errors
            }}</v-alert>
          </v-toolbar>
          <v-card-subtitle><b>Quizz Information</b> </v-card-subtitle>
          <v-card-text
            label="Quizz info"
            auto-grow
            outlined
            rows="10"
            row-height="25"
            shaped
            disabled
            color="black"
            font-weight="bold"
            ><b
              >This quiz consists of
              {{ this.quizz.questions.length }} multiple-choice or single-choice
              questions.The questions test basic technical knowledge about
              programming languages and technical concepts. You may review your
              answer-choices and compare them to the correct answers in the
              report section. Also, in the Ranking section you can see your
              progress compared to other users. Feel free to leave us a review
              in the Reviews section, or to read other users reviews about our
              site. To start, click the "Start" button. Click "Submit" after
              each answer to go to the next question.
            </b>
          </v-card-text>
          <v-card-actions>
            <v-btn @click="start">
              {{ "Start" }}
            </v-btn>
          </v-card-actions>
        </v-card>
        <v-card v-if="started && opened">
          <v-toolbar color="black" dark>
            {{ quizz.title }}
            <v-alert v-if="showAlert" type="error" value="errors">{{
              errors
            }}</v-alert>
          </v-toolbar>
          <v-card v-for="(question, indx) in quizz.questions" :key="indx">
            <v-card-title padding="10">
              <b> {{ question.statement }}</b>
            </v-card-title>
            <v-card v-for="(answer, idx) in question.answers" :key="idx">
              <input
                type="checkbox"
                id="answer.id"
                v-model="picked[idx + indx * question.answers.length]"
              />
              <label for="answer.id">{{ answer.answer }}</label>
            </v-card>
          </v-card>
          <v-card-actions>
            <v-btn @click="persist">
              {{ "Submit" }}
            </v-btn>
          </v-card-actions>
        </v-card>
      </template>
    </v-dialog>
  </v-app>
</template>

<script>
import api from "../api";

export default {
  data() {
    return {
      show: false,
      showAlert: false,
      errors: [],
      answers: [],
      picked: [],
      loggedUser: {},
      started: false,
    };
  },
  name: "TakeQuizzDialog",
  props: {
    quizz: Object,
    opened: Boolean,
    correctAnswers: Array,
  },
  methods: {
    start: function () {
      this.started = true;
    },
    computeScore(answers) {
      let score = 0;
      for (let i = 0; i < answers.length; i++) {
        if (
          answers[i].correct === true &&
          this.correctAnswers[i].correct === true
        ) {
          score++;
        } else if (answers[i].correct !== this.correctAnswers[i].correct) {
          score--;
        }
      }
      if (score < 0) {
        return 0;
      }
      return score;
    },
    checkIfPicked(pick) {
      return pick === true;
    },
    persist: function () {
      this.showAlert = false;
      this.errors = [];
      let answers = [];
      for (let i = 0; i < this.quizz.questions.length; i++) {
        for (let j = 0; j < this.quizz.questions[i].answers.length; j++) {
          answers.push({
            answer: this.quizz.questions[i].answers[j].answer,
            correct: this.checkIfPicked(
              this.picked[j + i * this.quizz.questions[i].answers.length]
            ),
          });
        }
      }
      this.loggedUser = JSON.parse(localStorage.getItem("user"));

      let quizzSession = {
        quizzId: this.quizz.id,
        answerSequence: answers,
        score: this.computeScore(answers),
        userId: this.loggedUser.id,
      };
      api.quizzSession
        .createQuizzSession(quizzSession)
        .catch((err) => {
          this.showAlert = true;
          this.errors = err.response.data.message;
        })
        .then(() => {
          if (this.errors.length === 0) {
            this.started = false;
            this.$emit("refresh");
            this.showAlert = false;
          }
        });
    },
  },
};
</script>

<style scoped></style>
