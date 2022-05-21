<template>
  <v-card>
    <v-card-title>
      Tracks
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addTrack">Add Track</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      yarn
      :items="tracks"
      :search="search"
      @click:row="editTrack"
    >
    </v-data-table>
    <TrackDialog
      :opened="dialogVisible"
      :track="selectedTrack"
      @refresh="refreshList"
    ></TrackDialog>
  </v-card>
</template>

<script>
import api from "../api";
import TrackDialog from "../components/TrackDialog";

export default {
  name: "Track",
  components: { TrackDialog },
  audio: {},
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
      selectedTrack: {},
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.selectedTrack = {};
      this.tracks = await api.tracks.allTracks();
    },
    addTrack() {
      this.dialogVisible = true;
    },
    editTrack(track) {
      if (this.audio) {
        this.audio.pause();
      }
      this.dialogVisible = true;
      this.selectedTrack = track;
      this.audio = new Audio(this.selectedTrack.preview);
      this.audio.play();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
