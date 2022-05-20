<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Create User </v-toolbar>
        <v-form>
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field v-model="user.name" label="Username" />
          <v-text-field
            :disabled="edit"
            v-model="user.password"
            label="Password"
            type="password"
          />
          <v-text-field
            :disabled="edit"
            v-model="confirmPassword"
            label="Confirm password"
            type="password"
          />
          <RoleDD @role-change="setRole($event)" :disabled="edit" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist" :disabled="passwordsMatch()">
            Create
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import RoleDD from "@/components/RoleDD";

export default {
  name: "UserDialog",
  components: { RoleDD },
  props: {
    user: Object,
    opened: Boolean,
    edit: Boolean,
  },
  data() {
    return {
      chosenRole: "",
      confirmPassword: "",
    };
  },

  methods: {
    setRole(role) {
      console.log(role);
      this.user.role = role;
      console.log(this.user.role);
    },
    passwordsMatch() {
      return this.user.password !== this.confirmPassword;
    },
    persist() {
      api.users
        .create({
          email: this.user.email,
          name: this.user.name,
          password: this.user.password,
          role: this.user.role,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
