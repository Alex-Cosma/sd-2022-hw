<template>
  <v-card>
    <v-card-title>
      Tutorials
      <v-spacer></v-spacer>
      <v-btn @click="addTutorial">Add Tutorial</v-btn>
    </v-card-title>
    <TutorialDialog :opened="dialogVisible"></TutorialDialog>
  </v-card>
</template>

<script>
import TutorialDialog from "./TutorialDialog";
export default {
  name: "TutorialList",
  components: { TutorialDialog },
  data() {
    return {
      dialogVisible: false,
    };
  },
  methods: {
    addTutorial() {
      this.dialogVisible = true;
    },
  },
};
</script>

<style scoped></style>
