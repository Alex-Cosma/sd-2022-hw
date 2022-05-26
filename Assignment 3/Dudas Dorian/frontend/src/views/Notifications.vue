<template>
  <div>
    <form class="form-inline">
      <table id="conversation" class="table table-striped">
        <thead>
          <tr>
            <th>Notifications</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in received_messages" :key="item">
            <td>{{ item }}</td>
          </tr>
        </tbody>
      </table>
    </form>
  </div>
</template>
<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
export default {
  name: "Notifications",
  data() {
    return {
      received_messages: [],
      send_message: null,
      connected: false,
    };
  },
  methods: {
    connect() {
      this.socket = new SockJS("http://localhost:8088/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          console.log(frame);
          this.stompClient.subscribe("/topic/notifications", (tick) => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body).content);
          });
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
  },
  created() {
    this.connect();
  },
};
</script>
<style scoped></style>
