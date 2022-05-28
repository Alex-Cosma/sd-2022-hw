<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Create post </v-toolbar>
        <v-form>
          <v-text-field v-model="post.content" label="Content" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Save </v-btn>
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
  name: "AddPostDialog",
  props: {
    post: Object,
    opened: Boolean,
    thread: String,
  },
  methods: {
    persist() {
      const user = JSON.parse(localStorage.getItem("user"));
      api.posts
        .create({
          topicTitle: this.thread,
          posterUsername: user.username,
          content: this.post.content,
          creationDate: new Date(),
        })
        .then(
          () => this.$emit("refresh"),
          (error) => alert(error.response.data.message)
        );
      this.connectAndSendNotification();
    },
    connectAndSendNotification() {
      this.socket = new SockJS("http://localhost:8088/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          console.log(frame);
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          contents: this.thread,
        };
        console.log(JSON.stringify(msg));
        this.stompClient.send("/app/notify", JSON.stringify(msg), {});
      }
    },
  },
};
</script>

<style scoped></style>
