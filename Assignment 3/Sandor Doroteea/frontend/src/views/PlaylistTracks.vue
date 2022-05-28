<template>
  <v-card>
    <v-card-title>
      PlaylistTracks
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
      >
      </v-data-table>
    </v-card-title>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "PlaylistTracks",
  props: {
    playlist: Object,
  },
  data() {
    return {
      tracks: [],
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
      dialogVisible: false,
    };
  },
  methods: {
    async refreshList() {
      this.tracks = await api.tracks.allPlaylistTracks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
