<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="black" dark>
          {{ isNew ? "Create question" : "Edit question" }}
          <v-alert v-if="showAlert" type="error" value="errors">{{
            errors
          }}</v-alert>
        </v-toolbar>
        <v-form>
          <v-text-field v-model="question.statement" label="Statement" />
          <v-text-field v-model="question.category" label="Category" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteQ">Delete</v-btn>
        </v-card-actions>
      </v-card>

      <QuestionAnswersDialog
        :opened="showCreateAnswersDialog"
        :question="createdQuestion"
        @close="closeCreateAnswersDialog"
      >
      </QuestionAnswersDialog>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import QuestionAnswersDialog from "@/components/QuestionAnswersDialog";

export default {
  components: {
    QuestionAnswersDialog,
  },
  data() {
    return {
      show: false,
      showAlert: false,
      errors: [],
      ans: [],
      createdQuestion: null,
      showCreateAnswersDialog: false,
    };
  },
  name: "QuestionDialog",
  props: {
    question: Object,
    opened: Boolean,
  },
  methods: {
    deleteQ() {
      api.questions
        .deleteQuestion(this.question.id)
        .catch((err) => {
          this.showAlert = true;
          this.errors = err.response.data.message;
        })
        .then(() => {
          if (this.errors.length === 0) {
            this.showAlert = false;
            this.$emit("refresh");
          }
        });
    },

    persist: function () {
      this.showAlert = false;
      this.errors = [];
      if (this.isNew) {
        api.questions
          .createQuestion({
            statement: this.question.statement,
            category: this.question.category,
          })
          .catch((err) => {
            this.showAlert = true;
            this.errors = err.response.data.message;
          })
          .then((response) => {
            this.createdQuestion = response;
            this.showCreateAnswersDialog = true;
            if (this.errors.length === 0) {
              this.showAlert = false;
            }
          });
      } else {
        api.questions
          .editQuestion(this.question.id, {
            id: this.question.id,
            statement: this.question.statement,
            category: this.question.category,
          })
          .catch((err2) => {
            this.showAlert = true;
            this.errors = err2.response.data.message;
          })
          .then(() => {
            if (this.errors.length === 0) {
              this.$emit("refresh");
              this.showAlert = false;
            }
          });
      }
    },
    closeCreateAnswersDialog: function () {
      this.showCreateAnswersDialog = false;
      this.$emit("refresh");
    },
  },
  computed: {
    isNew: function () {
      return !this.question.id;
    },
  },
};
</script>

<style scoped></style>
