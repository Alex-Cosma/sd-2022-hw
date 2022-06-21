<template>
  <v-tabs background-color="teal lighten-2" center-active dark>
    <v-tab>Trainings</v-tab>
    <v-tab>News</v-tab>
    <v-tab>Tutorials</v-tab>

    <v-tab-item>
      <v-card flat>
        <TrainingList></TrainingList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <News></News>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <TutorialList></TutorialList>
      </v-card>
    </v-tab-item>
  </v-tabs>
</template>

<script>
import TrainingList from "./TrainingList";
import News from "./News";
import TutorialList from "./TutorialList";
export default {
  name: "TabBarTrainer",
  components: { TrainingList, News, TutorialList },
};
</script>
<style scoped></style>
