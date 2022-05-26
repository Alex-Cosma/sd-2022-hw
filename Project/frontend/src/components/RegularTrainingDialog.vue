<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-card-actions>
          <v-btn @click="addRegularTraining">Join Training</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "RegularTrainingDialog",
  props: {
    training: Object,
    opened: Boolean,
  },
  methods: {
    addRegularTraining() {
      const user = JSON.parse(localStorage.getItem("user"));
      api.users.addRegularTraining(user.id, this.training.id);
      this.$emit("refresh");
    },
  },
};
</script>

<style scoped></style>
