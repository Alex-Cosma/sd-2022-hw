<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create training" : "Edit training" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="training.title" label="Title" />
          <v-text-field v-model="training.type" label="Type" />
          <v-select v-model="training.days" label="Days" :items="days" />
          <v-select v-model="training.hours" label="Hour" :items="hours" />
          <v-select
            v-model="training.location"
            label="Location"
            :items="locations"
          />
          <v-select
            v-model="training.user"
            label="Trainer"
            :items="users"
            :item-text="'username'"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteB">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "TrainingDialog",
  props: {
    training: Object,
    opened: Boolean,
  },
  data() {
    return {
      users: [],
      locations: ["Calea Turzii 178", "Campului 87", "Observatorului 82"],
      days: [
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday",
      ],
      hours: [
        "12:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "17:00",
        "18:00",
        "19:00",
        "20:00",
      ],
    };
  },
  methods: {
    deleteB() {
      api.trainings.delete(this.training.id);
      this.$emit("refresh");
    },
    persist() {
      if (this.isNew) {
        this.connectAndSendNotification(this.training.title);
        api.trainings
          .create({
            title: this.training.title,
            type: this.training.type,
            date: this.training.days + " " + this.training.hours,
            location: this.training.location,
            user: this.training.user,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.trainings
          .edit(this.training.id, {
            id: this.training.id,
            title: this.training.title,
            type: this.training.type,
            date: this.training.days + " " + this.training.hours,
            location: this.training.location,
            user: this.training.user,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    async refreshDropdownList() {
      this.users = await api.users.allTrainers();
    },
    connectAndSendNotification(title) {
      this.socket = new SockJS("http://localhost:8088/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect({}, () => {
        console.log("Send message:");
        if (this.stompClient && this.stompClient.connected) {
          const msg = {
            contents: title,
          };
          console.log(JSON.stringify(msg));
          this.stompClient.send("/app/notify", JSON.stringify(msg), {});
        }
      });
    },
  },
  computed: {
    isNew: function () {
      return !this.training.id;
    },
  },
  watch: {
    opened(opened) {
      if (opened) {
        this.refreshDropdownList();
      }
    },
  },
};
</script>

<style scoped></style>
