<template>
  <v-app>
    <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
    >
      <template>
        <v-card>
          <v-toolbar color="black" dark>
            {{ isNew ? "Create quizz" : "Edit quizz" }}
            <v-alert v-if="showAlert" type="error" value="errors">{{
              errorMessage
            }}</v-alert>
          </v-toolbar>
          <v-form>
            <v-text-field v-model="quizz.title" label="Title" />
            <v-text-field v-model="quizz.description" label="Description" />
            <v-data-table
              :headers="headers"
              :items="questions"
              item-key="id"
              v-model="selectedRows"
              class="elevation-1"
            >
              <template v-slot:item="{ item }">
                <tr
                  :class="selectedRows.indexOf(item) > -1 ? 'cyan' : ''"
                  @click="toggleSelection(item)"
                >
                  <td>{{ item.statement }}</td>
                  <td>{{ item.category }}</td>
                </tr>
              </template>
            </v-data-table>
          </v-form>
          <v-card-actions>
            <v-btn @click="persist">
              {{ isNew ? "Create" : "Save" }}
            </v-btn>
            <v-btn v-if="!isNew" @click="deleteQ">Delete</v-btn>
          </v-card-actions>
        </v-card>
      </template>
    </v-dialog>
  </v-app>
</template>

<script>
import api from "../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  data() {
    return {
      show: false,
      showAlert: false,
      errorMessage: [],
      selectedRows: [],
      headers: [
        {
          text: "Statement",
          align: "start",
          sortable: false,
          value: "statement",
        },
        { text: "Category", value: "category" },
      ],
    };
  },
  name: "QuizzDialog",
  props: {
    quizz: Object,
    opened: Boolean,
    questions: Array,
  },
  methods: {
    toggleSelection(row) {
      if (this.selectedRows.includes(row)) {
        this.selectedRows = this.selectedRows.filter(
          (selectedRow) => selectedRow.id !== row.id
        );
      } else {
        this.selectedRows.push(row);
      }
    },
    deleteQ() {
      api.quizzes.deleteQuizz(this.quizz.id);
      this.$emit("refresh");
    },

    persist: function () {
      this.showAlert = false;
      this.errors = [];
      if (this.isNew) {
        this.connectAndSendNotification(this.quizz.title);
        api.quizzes
          .createQuizz({
            title: this.quizz.title,
            description: this.quizz.description,
            questions: this.selectedRows,
          })
          .catch((err) => {
            this.showAlert = true;
            this.errorMessage = err.response.data.message;
          })
          .then(() => {
            if (this.errorMessage.length === 0) {
              this.$emit("refresh");
              this.selectedRows = [];
              this.showAlert = false;
            }
          });
      } else {
        if (this.selectedRows.length === 0) {
          this.selectedRows = this.quizz.questions;
        }
        api.quizzes
          .editQuizz(this.quizz.id, {
            id: this.quizz.id,
            title: this.quizz.title,
            description: this.quizz.description,
            questions: this.selectedRows,
          })
          .catch((err2) => {
            this.showAlert = true;
            this.errorMessage = err2.response.data.message;
          })
          .then(() => {
            if (this.errorMessage.length === 0) {
              this.selectedRows = [];
              this.$emit("refresh");
              this.showAlert = false;
            }
          });
      }
    },
    connectAndSendNotification(title) {
      this.socket = new SockJS("http://localhost:8088/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect({}, () => {
        console.log("Send message:");
        if (this.stompClient && this.stompClient.connected) {
          const msg = {
            content:
              " New quizz added , come and boost up your scores with the " +
              title +
              " quizz",
          };
          console.log(JSON.stringify(msg));
          this.stompClient.send("/app/notify", JSON.stringify(msg), {});
        }
        api.mail.sendMail();
      });
    },
  },
  computed: {
    isNew: function () {
      return !this.quizz.id;
    },
  },
};
</script>

<style scoped></style>
