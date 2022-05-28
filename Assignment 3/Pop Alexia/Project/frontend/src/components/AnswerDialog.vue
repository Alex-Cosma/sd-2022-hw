<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="black" dark>
          {{ isNew ? "Create answer" : "Edit answer" }}
          <v-alert v-if="showAlert" type="error" value="errors">{{
            errors
          }}</v-alert>
        </v-toolbar>
        <v-form>
          <v-text-field v-model="answer.answer" label="Answer" />
          <input type="checkbox" id="checkbox1" v-model="answer.correct" />
          <label for="checkbox1">{{ answer.correct }} </label>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteA">Delete</v-btn>
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
    };
  },
  name: "AnswerDialog",
  props: {
    answer: Object,
    question: Object,
    opened: Boolean,
  },
  methods: {
    deleteA() {
      api.answers.delete(this.answer.id).then(() => {
        this.$emit("refresh");
      });
    },

    persist: function () {
      this.showAlert = false;
      this.errors = [];
      if (this.isNew) {
        if (this.answer.correct !== true) {
          this.answer.correct = false;
        }
        this.answer.questionId = this.question.id;

        api.answers
          .create(this.answer)
          .catch((err) => {
            this.showAlert = true;
            this.errors = err.response.data.message;
          })
          .then(() => {
            if (this.errors.length === 0) {
              this.$emit("refresh");
              this.showAlert = false;
            }
          });
      } else {
        api.answers
          .edit(this.answer.id, this.answer)
          .catch((err) => {
            this.showAlert = true;
            this.errors = err.response.data.message;
          })
          .then(() => {
            if (this.errors.length === 0) {
              this.$emit("refresh");
              this.showAlert = false;
            }
          });
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.answer.id;
    },
  },
};
</script>

<style scoped></style>
