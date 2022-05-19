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
            <v-form v-if="inLoginMode">
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
            <v-form v-if="!inLoginMode">
              <v-text-field
                  prepend-icon="email"
                  name="Email"
                  label="Email"
                  v-model="register.email"
                  validate-on-blur
              >
              </v-text-field>
              <v-text-field
                  prepend-icon="person"
                  name="Username"
                  label="Username"
                  v-model="register.username"
                  validate-on-blur
              ></v-text-field>
              <v-text-field
                  prepend-icon="lock"
                  name="Password"
                  label="Password"
                  type="password"
                  v-model="register.password"
                  validate-on-blur
              ></v-text-field>
              <v-text-field
                  prepend-icon="person"
                  name="First Name"
                  label="First Name"
                  v-model="register.firstName"
                  validate-on-blur
              ></v-text-field>
              <v-text-field
                  prepend-icon="person"
                  name="Last Name"
                  label="Last Name"
                  v-model="register.lastName"
                  validate-on-blur
              ></v-text-field>
              <v-icon>house</v-icon>
                <vue-google-autocomplete
                    style="margin-left: 0.5em;border-bottom: 1px solid black"
                    prepend-icon="location_city"
                    ref="address"
                    id="address"
                    v-model="register.address"
                    placeholder="Please type your address"
                    v-on:placechanged="getAddressData"

                >
                </vue-google-autocomplete>
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
import VueGoogleAutocomplete from "vue-google-autocomplete";

export default {
  name: "HelloWorld",
  components: {
    VueGoogleAutocomplete
  },
  data: () => ({
    mode: "login",
    login: {
      email: "",
      username: "dominic",
      password: "@Dominic2",
    },
    register: {
      email: "",
      username: "",
      password: "",
      firstName: "",
      lastName: "",
      address: "",
    },
  }),
  methods: {
    attemptLogin() {
      this.$store
        .dispatch("auth/login", this.login)

        .then(() => {
          let user_id = this.$store.state.auth.user.id;
          if (this.$store.state.auth.status.loggedIn) {
            if (this.$store.getters["auth/isAdmin"]) {
              router.push("/users");
            } else {
              router.push("/posts/"+user_id);
            }
          } else {
            alert("Invalid credentials!");
          }
        });
    },
    getAddressData: function (addressData) {
      this.address = addressData;
    },
    async attemptRegister() {
      this.register.address= Object.values(this.address).join(",");
      await this.$store.dispatch("auth/register", this.register);
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
