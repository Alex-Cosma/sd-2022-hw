<template>
  <v-card>
    <v-card-title>
      TracksAPI
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      yarn
      :items="tracks"
      :search="search"
      @click:row="addToPlaylist"
    >
    </v-data-table>

    <TracksApiDialog
      :opened="dialogVisible"
      :track="selectedTrack"
      @refresh="refreshList"
    ></TracksApiDialog>
  </v-card>
</template>

<script>
import api from "../api";
import TracksApiDialog from "../components/TracksApiDialog";
export default {
  name: "Track",
  components: { TracksApiDialog },
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
      this.tracks = await api.tracksApi.allTracks();
    },
    addToPlaylist(track) {
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
