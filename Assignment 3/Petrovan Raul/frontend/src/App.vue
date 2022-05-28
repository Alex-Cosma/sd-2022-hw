<template>
  <v-app>
    <v-main>
      <v-container fluid>
        <NavBar v-if="drawer"></NavBar>
        <TopBar v-if="isLoggedIn"></TopBar>
        <toast position="ne"></toast>

        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import TopBar from "./components/TopBar";
import NavBar from "@/components/NavBar";
import {auth as store} from "@/store/auth.module";
import {ADD_TOAST_MESSAGE, Toast} from "vuex-toast";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
// import WSGREET from "@/components/WSGREET";


export default {
  name: "App",
  components: { NavBar, TopBar, Toast },
  data: () => ({
    //
  }),
  computed: {
    isLoggedIn: function () {
      return this.$store.state.auth.status.loggedIn;
    },
    drawer: function () {
      return store.getters.drawer(store.state);
    },
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
            this.stompClient.subscribe("/topic/notifications", (message) => {
              const messageObj = JSON.parse(message.body);
              console.log(messageObj, this.$store.state.auth.user.id);
              if(messageObj.userIds?.includes(this.$store.state.auth.user.id.toString())) {
                this.$store.dispatch(ADD_TOAST_MESSAGE, {
                  text: messageObj.message,
                  type: 'success',
                });
              }

            });
          },
          (error) => {
            console.log(error);
          }
      );
    },

  },
  created() {
    this.connect();
  },
  beforeDestroy() {
    this.stompClient.disconnect();
  },
};
</script>
