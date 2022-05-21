<template>
  <v-card>
    <v-card-title>
      Settings
    </v-card-title>
    <v-spacer></v-spacer>
    <v-form v-model="valid" ref="form" class="name-form">
      <v-text-field
          v-model="user.name"
          label="Username"
          :rules="[v => !!v || 'Name is required']"
          required
          readonly
      ></v-text-field>
      <v-text-field
          v-model="user.firstName"
          label="First Name"
      ></v-text-field>
      <v-text-field
          v-model="user.lastName"
          label="Last Name"
      ></v-text-field>

      <v-btn
          color="primary"
          type="submit"
          :disabled="!valid"
          @click="updateUser"
      >
        Save
      </v-btn>
    </v-form>
  </v-card>
</template>

<script>
import {auth as store} from "@/store/auth.module";
import api from "@/api";
import router from "@/router";

export default {
  name: "UserSettings",
  data() {
    return {
      user: store.state.user,
      errors: {},
      loading: false
    };
  },
  // computed: {
  //   valid() {
  //     return this.$refs.form.validate();
  //   }
  // },
  methods: {
    updateUser() {
      this.loading = true;
      api.users.update(this.user).then(() => {
        this.$router.push("/");
      }).catch(err => {
        this.errors = err.response.data.errors;
      }).finally(() => {
        router.push("/my-flights");
        this.loading = false;
      });
    }
  },
  async created() {
    this.user = await api.users.userDetails(this.user.id);
  },
}
</script>

<style scoped>
.name-form {
  max-width: 500px;
  padding: 1rem;
}
</style>