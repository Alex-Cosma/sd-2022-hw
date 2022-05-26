<template>
  <v-tabs background-color="teal lighten-2" center-active dark>
    <v-tab>Trainings</v-tab>
    <v-tab>Users</v-tab>

    <v-tab-item>
      <v-card flat>
        <TrainingList></TrainingList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <UserList></UserList>
      </v-card>
    </v-tab-item>
  </v-tabs>
</template>

<script>
import UserList from "../components/UserList";
import TrainingList from "./TrainingList";
export default {
  name: "TabBar",
  components: { UserList, TrainingList },
};
</script>
<style scoped>

</style>