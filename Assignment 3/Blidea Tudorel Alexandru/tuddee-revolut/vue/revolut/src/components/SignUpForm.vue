<template>

      <v-overlay :absolute="true" v-model="display" @click:outside="$emit('close')"
      >
        <div class="holder" style="width: 100vw; height: 100vh">
        <v-card class="pa-4 ma-auto" min-width="500px">
          <div class="close-container" style="margin: -15px">
            <div>
              <v-btn fab
                     small
                     class="close"
                     @click="$emit('close')">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </div>
          </div>
          <v-card-title>Sign Up</v-card-title>
          <v-card-content>
            <v-scale-transition>
              <p v-if="!!errorMessage" class="text-red-accent-2"> {{ errorMessage }} </p>
            </v-scale-transition>
          <v-container>
            <v-row>
              <v-col>
                <v-text-field
                    label="First Name:"
                    variant="underlined"
                    style="color: var(--primary)"
                    v-model="form.firstName"
                    :rules="[
                        (value) => !!value || 'First Name is required!',
                        (value) => value.length <= 64 || 'First Name should have maximum 64 characters'
                    ]"
                ></v-text-field>
              </v-col>
              <v-col>
                <v-text-field
                    label="Last Name:"
                    variant="underlined"
                    style="color: var(--primary)"
                    v-model="form.lastName"
                    :rules="[
                        (value) => !!value || 'Last Name is required!',
                        (value) => value.length <= 64 || 'Last Name should have maximum 64 characters'
                    ]"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
              <v-text-field
                  label="Email:"
                  variant="underlined"
                  style="color: var(--primary)"
                  v-model="form.email"
                  type="email"
                  :rules="[
                        (value) => !!value || 'e-mail required!',
                        (value) => this.isEmail(value) || 'Not a valid e-mail!'
                    ]"
              ></v-text-field>
              </v-col>
              <v-col>
                <v-text-field
                    label="Repeat Email:"
                    variant="underlined"
                    style="color: var(--primary)"
                    v-model="form.repeatedEmail"
                    type="email"
                    :rules="[
                        (value) => !!value || 'e-mail required',
                        (value) => this.isEmail(value) || 'Not a valid e-mail!',
                        () => this.emailsMatching || 'Email doesn\'t match!'
                    ]"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field
                    label="Username:"
                    variant="underlined"
                    style="color: var(--primary)"
                    v-model="form.username"
                    :rules="[
                        (value) => !!value || 'Username is required!',
                        (value) => value.length <= 16 || 'Username should have maximum 16 characters'
                    ]"
                ></v-text-field>
              </v-col>
              <v-col>
                <v-text-field
                    label="Birth Date(dd/mm/yyyy):"
                    variant="underlined"
                    style="color: var(--primary)"
                    v-model="form.birthDate"
                    :rules="[() => validBirthDate || 'Birth date should have the following format: dd/mm/yyyy']"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field
                    label="Password:"
                    variant="underlined"
                    style="color: var(--primary)"
                    v-model="form.password"
                    type="password"
                    :rules="passwordRules"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row class="my-2"><v-btn block="true" color="#1DA1F2"><v-icon>mdi-twitter</v-icon>Sign-Up with Twitter</v-btn> </v-row>
            <v-row class="my-2"><v-btn block="true" color="#DE5246"><v-icon>mdi-google</v-icon>Sign-Up with Google</v-btn> </v-row>
            <v-row class="my-2"><v-btn block="true" color="#3b5998"><v-icon>mdi-facebook</v-icon>Sign-Up with Facebook</v-btn> </v-row>
          </v-container>
          </v-card-content>
          <div class="text-right">
            <v-btn
                :disabled="!submitEnable"
                style="background-color: var(--secondary)"
                @click="signUp"
            >
              Sign Up
            </v-btn>
          </div>
        </v-card>
        </div>
      </v-overlay>
</template>

<script>
import AuthService from "@/service/AuthService";

export default {
  name: "SignUpForm",
  props: {
    display: {
      type: Boolean,
      required: true,
    }
  },
  emits: ["close", "successfully"],
  data() {
    return {
      rulesAreActive: {
        password: false,
      },
      errorMessage: "",
      form: {
        firstName: "",
        lastName: "",
        email: "",
        repeatedEmail: "",
        username: "",
        birthDate: "",
        password: "",
      },
      emailPattern: /[\w\d]+@([\w\d]+\.)*[A-Za-z]{2,4}/,
    }
  },
  computed: {
    emailsMatching() {
      return this.form.email !== "" && this.form.email === this.form.repeatedEmail && this.isEmail(this.form.email);
    },

    validBirthDate() {
      const splitted = this.form.birthDate.split("/");
      if (splitted.length === 3) {
        return +splitted[0] < 30 && +splitted[0] > 0 && +splitted[1] > 0 && +splitted[1] < 13 && +splitted[2] > 1900 && +splitted[2] < 2030;
      }
      return false;
    },

    submitEnable() {
      return this.emailsMatching && !!this.form.firstName && !!this.form.lastName && !!this.form.username && this.validBirthDate;
    },

    passwordRules() {
      if (this.rulesAreActive.password) {
        return [
          (value) => !!value || 'password required!',
          (value) => value.length > 8 && value.length < 32 || 'The password should have at least 8 characters and at most 32 characters',
          (value) => this.passwordValidator(value)
        ];
      }
      return [];
    }
  },
  methods: {
    isEmail(str) {
      return str.match(this.emailPattern) !== null;
    },

    signUp() {
      AuthService.signUp(this.form)
          .then(() => {
            this.$emit('successfully', {
              "username": this.form.username,
              "password": this.form.password
            });
          })
          .catch((err) => {
            this.errorMessage = err.response.data.message;
          })
    },

    passwordValidator(password) {
      const specialCharacters = "@#!$%^&*;[]()-".split("");
      const smallLetters = "qwertyuiopasdfghjklzxcvbnm".split("");
      const digits = "0123456789".split("");
      const bigLetters = smallLetters.map(letter => letter.toUpperCase());

      if (specialCharacters.filter(character => password.includes(character)).length === 0) {
        return "Password should include at least one special character: " + specialCharacters.join("");
      }

      if (smallLetters.filter(character => password.includes(character)).length === 0) {
        return "Password should include at least one smallLetter: " + smallLetters.sort().join("");
      }

      if (bigLetters.filter(character => password.includes(character)).length === 0) {
        return "Password should include at least one bigLetter: " + bigLetters.sort().join("");
      }

      if (digits.filter(digit => password.includes(digit)).length === 0) {
        return "Password should include at least one digit: " + digits.join("");
      }

      return true;
    }
  }
}
</script>

<style scoped>
.holder {
  display: flex;
  justify-content: center;
  align-items: center;
}
.close {
  float: right;
}

.close:hover {
  cursor: pointer;
  background-color: crimson;
  color: white;
}

.close-container {
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
}

</style>