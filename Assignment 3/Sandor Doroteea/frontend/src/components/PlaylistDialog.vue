<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="1500"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create playlist" : "Edit playlist" }}
        </v-toolbar>
        <v-form>
          <v-card-title>
            <v-text-field v-model="playlist.id" label="User id" />
            <v-text-field v-model="playlist.title" label="Title" />
            <v-spacer></v-spacer>
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
            <v-data-table
              :headers="headers"
              :items="playlist.tracks"
              :search="search"
              @click:row="sing"
            >
            </v-data-table>
          </v-card-title>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Edit playlist" : "Save playlist" }}
          </v-btn>
          <v-btn @click="addPlaylist">Add Playlist</v-btn>
          <v-btn @click="deletePlaylist">Delete Playlist</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "PlaylistDialog",
  props: {
    playlist: Object,
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
    };
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.playlists
          .create({
            id: this.playlist.id,
            title: this.playlist.title,
            duration: 0,
          })
          .then(() => {
            this.$emit("refresh");
          });
      } else {
        api.playlists
          .edit({
            id: this.playlist.id,
            title: this.playlist.title,
            tracks: this.playlist.tracks,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    addPlaylist() {
      api.playlists
        .create({
          id: this.$store.getters["auth/getUserID"],
          title: this.playlist.title,
          duration: 0,
        })
        .then(() => {
          this.$emit("refresh");
        });
    },
    deletePlaylist() {
      api.playlists
        .delete({
          id: this.playlist.id,
        })
        .then(() => this.$emit("refresh"));
    },
    sing(track) {
      if (this.audio) {
        this.audio.pause();
      }
      this.audio = new Audio(track.preview);
      this.audio.play();
    },
  },
  computed: {
    isNew: function () {
      return !this.playlist.tracks && !this.playlist.id;
    },
  },
};
</script>

<style scoped></style>
