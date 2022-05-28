<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="black" dark>
          {{ "Add answers" }}
          <v-alert v-if="showAlert" type="error" value="errors">{{
            errors
          }}</v-alert>
        </v-toolbar>
        <v-form>
          <v-text-field v-model="ans[0]" label="Answer1" />
          <input type="checkbox" id="checkbox1" v-model="corr[0]" />
          <label for="checkbox1">{{ corr[0] }}</label>
          <v-text-field v-model="ans[1]" label="Answer2" />
          <input type="checkbox" id="checkbox2" v-model="corr[1]" />
          <label for="checkbox2">{{ corr[1] }}</label>
          <v-text-field v-model="ans[2]" label="Answer3" />
          <input type="checkbox" id="checkbox3" v-model="corr[2]" />
          <label for="checkbox3">{{ corr[2] }}</label>
          <v-text-field v-model="ans[3]" label="Answer4" />
          <input type="checkbox" id="checkbox4" v-model="corr[3]" />
          <label for="checkbox4">{{ corr[3] }}</label>
          <v-text-field v-model="ans[4]" label="Answer5" />
          <input type="checkbox" id="checkbox5" v-model="corr[4]" />
          <label for="checkbox5">{{ corr[4] }}</label>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ "Add answers" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  data() {
    return {
      show: false,
      showAlert: false,
      errors: [],
      ans: [],
      corr: [false, false, false, false, false],
      createdAnswers: [],
    };
  },
  name: "QuestionAnswersDialog",
  props: {
    question: Object,
    opened: Boolean,
  },
  methods: {
    persist: function () {
      this.showAlert = false;
      this.errors = [];
      for (let i = 0; i < this.ans.length; i++) {
        this.createdAnswers.push({
          answer: this.ans[i],
          correct: this.corr[i],
          questionId: this.question.id,
        });
      }
      for (let i = 0; i < this.createdAnswers.length; i++) {
        api.answers.create(this.createdAnswers[i]);
      }
      if (this.errors.length === 0) {
        this.ans = [];
        this.corr = [false, false, false, false, false];
        this.createdAnswers = [];
        this.$emit("close");
        this.showAlert = false;
      }
    },
  },
};
</script>

<style scoped></style>
