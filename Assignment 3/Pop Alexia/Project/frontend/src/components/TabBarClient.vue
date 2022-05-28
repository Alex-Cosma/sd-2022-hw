<template>
  <v-tabs background-color="black accent-4 elevation" center-active dark>
    <v-tab>Quizzes</v-tab>
    <v-tab>Ranking</v-tab>
    <v-tab>Reviews</v-tab>
    <v-tab>Reports</v-tab>
    <v-tab>Notifications</v-tab>

    <v-tab-item>
      <v-card flat>
        <TakeQuizzList></TakeQuizzList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <UserRankingList></UserRankingList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <ReviewListClient></ReviewListClient>
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
  </v-tabs>
</template>

<script>
import TakeQuizzList from "../components/TakeQuizzList";
import ReportList from "../components/ReportList";
import ReviewListClient from "@/components/ReviewListClient";
import UserRankingList from "@/components/UserRankingList";
import Notifications from "@/components/Notifications";

export default {
  name: "TabBarClient",
  components: {
    TakeQuizzList,
    UserRankingList,
    ReviewListClient,
    ReportList,
    Notifications,
  },
};
</script>

<style scoped></style>
