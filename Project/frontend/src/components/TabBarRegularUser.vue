<template>
  <v-tabs background-color="teal lighten-2" center-active dark>
    <v-tab>Trainings</v-tab>
    <v-tab>Trainings enrolled</v-tab>
    <v-tab>Notifications</v-tab>
    <v-tab>Tutorials</v-tab>

    <v-tab-item>
      <v-card flat>
        <RegularTrainingList></RegularTrainingList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <ReportList></ReportList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <Notifications></Notifications>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <TutorialListRegularUser></TutorialListRegularUser>
      </v-card>
    </v-tab-item>
  </v-tabs>
</template>

<script>
import RegularTrainingList from "./RegularTrainingList";
import ReportList from "./ReportList";
import Notifications from "./Notifications";
import TutorialListRegularUser from "./TutorialListRegularUser";
export default {
  name: "TabBarRegularUser",
  components: { RegularTrainingList, ReportList, Notifications, TutorialListRegularUser},
};
</script>
<style scoped></style>