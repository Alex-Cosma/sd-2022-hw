<template>
  <v-tabs background-color="teal lighten-2" center-active dark>
    <v-tab>Books</v-tab>
    <v-tab>Users</v-tab>
    <v-tab>Reports</v-tab>

    <v-tab-item>
      <v-card flat>
        <BookList></BookList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <UserList></UserList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <ReportList></ReportList>
      </v-card>
    </v-tab-item>
  </v-tabs>
</template>

<script>
import UserList from "../components/UserList";
import BookList from "../components/BookList";
import ReportList from "../components/ReportList";
export default {
  name: "TabBar",
  components: { UserList, BookList, ReportList },
};
</script>
<style scoped>

</style>