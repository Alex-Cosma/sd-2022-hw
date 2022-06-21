<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-card-actions>
          <v-btn @click="downloadTutorial">Download image</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "TutorialDialogRegularUser",
  props: {
    tutorial: Object,
    opened: Boolean,
  },
  methods: {
    downloadTutorial() {
      api.tutorials.downloadTutorial(this.tutorial.title);
      this.$emit("refresh");
    },
  },
};
</script>

<style scoped></style>
