<template>
  <v-card>
    <v-card-title>
      Ranking
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
        @input="searchAllBy"
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      sort-desc
      prop="score"
      @refresh="refreshList"
    ></v-data-table>
  </v-card>
</template>

<script>
import api from "../api";
export default {
  name: "UserRankingList",
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "username",
        },
        { text: "Ranking", value: "rankingPoints" },
      ],
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
    async searchAllBy() {
      if (this.search !== "") {
        this.users = await api.users.filterAll(this.search);
      } else {
        this.search = "";
        this.users = await api.users.allUsers();
      }
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
