<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create track" : "Edit track" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="track.title" label="Title" />
          <v-text-field v-model="track.link" label="Link" />
          <v-text-field v-model="track.duration" label="Duration" />
          <v-text-field
            v-model="track.explicit_lyrics"
            label="Explicit Lyrics"
          />
          <v-text-field v-model="track.preview" label="Preview" />
          <v-text-field v-model="track.artist" label="Artist" />
          <v-text-field v-model="track.album" label="Album" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create track" : "Save track" }}
          </v-btn>
          <v-btn @click="deleteTrack">Delete Track</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "TrackDialog",
  props: {
    track: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.tracks
          .create({
            title: this.track.title,
            link: this.track.link,
            duration: this.track.duration,
            explicit_lyrics: this.track.explicit_lyrics,
            preview: this.track.preview,
            artist: this.track.artist,
            album: this.track.album,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.tracks
          .update({
            id: this.track.id,
            title: this.track.title,
            link: this.track.link,
            duration: this.track.duration,
            explicit_lyrics: this.track.explicit_lyrics,
            preview: this.track.preview,
            artist: this.track.artist,
            album: this.track.album,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteTrack() {
      api.tracks
        .delete({
          id: this.track.id,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.track.id;
    },
  },
};
</script>

<style scoped></style>
