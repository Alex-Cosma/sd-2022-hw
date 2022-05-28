<template>
  <v-tabs background-color="black accent-4 elevation" center-active dark>
    <v-tab>Quizzes</v-tab>
    <v-tab>Questions</v-tab>
    <v-tab>Users</v-tab>
    <v-tab>Reviews</v-tab>

    <v-tab-item>
      <v-card flat>
        <QuizzList></QuizzList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <QuestionList></QuestionList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <UsersList></UsersList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <ReviewListAdmin></ReviewListAdmin>
      </v-card>
    </v-tab-item>
  </v-tabs>
</template>

<script>
import UsersList from "../components/UserList";
import QuestionList from "../components/QuestionList";
import QuizzList from "./QuizzList";
import ReviewListAdmin from "@/components/ReviewListAdmin";

export default {
  name: "TabBar",
  components: { UsersList, QuestionList, QuizzList, ReviewListAdmin },
  methods: {
    connect() {},
  },
};
</script>

<style scoped></style>
