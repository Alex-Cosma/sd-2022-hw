<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create user" : "Edit user" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Username" />
          <v-text-field v-model="user.password" label="Password" />
          <v-text-field v-model="user.email" label="Email" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create user" : "Save user" }}
          </v-btn>
          <v-btn @click="deleteUser">Delete user</v-btn>
        </v-card-actions>
        <v-spacer></v-spacer>
        <v-data-table :headers="headers" :items="user.purchasedTracks">
        </v-data-table>
        <v-spacer></v-spacer>
        <v-data-table :headers="headers2" :items="user.playlistList">
        </v-data-table>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  audio: {},
  data() {
    return {
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "id", value: "id" },
        { text: "link", value: "link" },
        { text: "duration", value: "duration" },
        { text: "explicit_lyrics", value: "explicit_lyrics" },
        { text: "preview", value: "preview" },
        { text: "artist", value: "artist" },
        { text: "album", value: "album" },
      ],
      headers2: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Id", value: "id" },
        { text: "Duration", value: "duration" },
      ],
    };
  },
  methods: {
    persist() {
      this.errors = [];
      if (this.isNew) {
        api.users
          .create({
            name: this.user.username,
            password: this.user.password,
            email: this.user.email,
            purchasedTracks: this.user.purchasedTracks,
            playlistList: this.user.playlistList,
          })
          .catch((err) => {
            alert(err.response.data.message);
          })
          .then(() => {
            this.$emit("refresh");
          });
      } else {
        api.users
          .edit({
            id: this.user.id,
            name: this.user.username,
            password: this.user.password,
            email: this.user.email,
            purchasedTracks: this.user.purchasedTracks,
            playlistList: this.user.playlistList,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteUser() {
      api.users
        .delete({
          id: this.user.id,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.user.id;
    },
  },
};
</script>

<style scoped></style>
