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
        <v-card-title>Sign In</v-card-title>
        <v-card-content>
          <v-container>
                <v-text-field
                    label="Username:"
                    variant="underlined"
                    style="color: var(--primary)"
                    v-model="form.username"
                    :rules="[
                        (value) => !!value || 'Username is required!',
                        (value) => value.length <= 16 || 'Username should have at most 16 characters'
                    ]"
                ></v-text-field>
                <v-text-field
                    label="Password:"
                    variant="underlined"
                    style="color: var(--primary)"
                    v-model="form.password"
                    :rules="[
                        (value) => !!value || 'Password is required!',
                    ]"
                ></v-text-field>
            <v-row class="my-2"><v-btn block="true" color="#1DA1F2"><v-icon>mdi-twitter</v-icon>Sign-In with Twitter</v-btn> </v-row>
            <v-row class="my-2"><v-btn block="true" color="#DE5246"><v-icon>mdi-google</v-icon>Sign-In with Google</v-btn> </v-row>
            <v-row class="my-2"><v-btn block="true" color="#3b5998"><v-icon>mdi-facebook</v-icon>Sign-In with Facebook</v-btn> </v-row>
          </v-container>
        </v-card-content>
        <div class="text-right">
          <v-btn
              :disabled="!submitEnable"
              style="background-color: var(--secondary)"
              @click="submit"
          >
            Sign In
          </v-btn>
        </div>
      </v-card>
    </div>
  </v-overlay>
</template>

<script>
export default {
  name: "SignInForm",
  props: {
    display: {
      type: Boolean,
      required: true,
    }
  },
  emits: ["close", "successfully"],
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      emailPattern: /[\w\d]+@([\w\d]+\.)*[A-Za-z]{2,4}/,
    }
  },
  computed: {
    submitEnable() {
      return !!this.form.username && !!this.form.password && this.form.username.length <= 16;
    }
  },
  methods: {
    submit() {
      this.$emit('successfully', this.form);
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