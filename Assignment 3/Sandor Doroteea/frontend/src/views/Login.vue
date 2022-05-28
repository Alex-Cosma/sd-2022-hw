<template>
  <v-container>
    <v-layout row justify-center pt-3>
      <v-flex xs4 class="grey lighten-4">
        <v-container class="text-xs-center">
          <v-card flat>
            <v-card-title primary-title>
              <h4 v-if="inLoginMode">Login</h4>
              <h4 v-else>Register</h4>
            </v-card-title>
            <v-form>
              <v-text-field
                v-if="mode === 'register'"
                prepend-icon="email"
                name="Email"
                label="Email"
                v-model="login.email"
                validate-on-blur
              >
              </v-text-field>
              <v-text-field
                prepend-icon="person"
                name="Username"
                label="Username"
                v-model="login.username"
                validate-on-blur
              ></v-text-field>
              <v-text-field
                prepend-icon="lock"
                name="Password"
                label="Password"
                type="password"
                v-model="login.password"
                validate-on-blur
              ></v-text-field>
              <v-card-actions>
                <v-container>
                  <v-layout v-if="inLoginMode" row justify-center>
                    <v-btn primary large block @click="attemptLogin"
                      >Login</v-btn
                    >
                    <v-btn plain @click="toggleMode">
                      No account? Click to register!
                    </v-btn>
                  </v-layout>

                  <v-layout v-else row justify-center>
                    <v-btn primary large block @click="attemptRegister"
                      >Register</v-btn
                    >
                    <v-btn plain @click="toggleMode">
                      Already registered? Click to login!
                    </v-btn>
                  </v-layout>

                  <v-layout v-if="isLoggedIn" row justify-center>
                    <v-btn @click="logout"> Logout </v-btn>
                  </v-layout>
                </v-container>
              </v-card-actions>
            </v-form>
          </v-card>
        </v-container>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import router from "../router";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "MusicPlayer",

  data: () => ({
    mode: "login",
    login: {
      email: "",
      username: "alex",
      password: "WooHoo1!",
    },
  }),
  methods: {
    sendMessage(username) {
      this.socket = new SockJS("http://localhost:8088/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect({}, () => {
        console.log("here bro");
        if (this.stompClient && this.stompClient.connected) {
          const msg = { name: username };
          console.log(JSON.stringify(msg));
          this.stompClient.send("/app/hello", JSON.stringify(msg), {});
        }
      });
    },

    attemptLogin() {
      this.$store.dispatch("auth/login", this.login).then(() => {
        if (this.$store.state.auth.status.loggedIn) {
          this.sendMessage(this.login.username);

          if (this.$store.getters["auth/isAdmin"]) {
            router.push("/users");
          } else {
            router.push("/tracks");
          }
        } else {
          alert("Invalid credentials!");
        }
      });
    },
    async attemptRegister() {
      await this.$store.dispatch("auth/register", this.login);
    },
    toggleMode() {
      this.mode = this.mode === "login" ? "register" : "login";
    },
    logout() {
      this.$store.dispatch("auth/logout");
    },
  },
  computed: {
    isLoggedIn: function () {
      return this.$store.state.auth.status.loggedIn;
    },
    inLoginMode: function () {
      return this.mode === "login";
    },
  },
};
</script>
