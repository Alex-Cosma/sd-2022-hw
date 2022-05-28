<template>
  <div>
    <audio id="myAudio" controls></audio>
    <v-btn @click="getSongs">Get Songs</v-btn>
    <v-btn @click="myDownload">Download</v-btn>
    <v-container>
      <v-layout row wrap>
        <v-card class="mx-auto" max-width="500">
          <v-toolbar color="dark" dark>
            <v-app-bar-nav-icon></v-app-bar-nav-icon>

            <v-toolbar-title>Online Music</v-toolbar-title>

            <v-spacer></v-spacer>

            <v-btn icon>
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </v-toolbar>

          <v-list>
            <v-list-group
              v-for="item in items"
              :key="item.title"
              v-model="item.active"
              :prepend-icon="item.action"
              no-action
            >
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title v-text="item.title"></v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item v-for="child in item.items" :key="child.title">
                <v-list-item-content>
                  <v-list-item-title v-text="child.title"></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-group>
          </v-list>
        </v-card>
        <v-card class="mx-auto" max-width="500">
          <v-toolbar color="dark" dark>
            <v-app-bar-nav-icon></v-app-bar-nav-icon>

            <v-toolbar-title>Local Music</v-toolbar-title>

            <v-spacer></v-spacer>

            <v-btn icon>
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </v-toolbar>

          <v-list>
            <v-list-group
              v-for="item in items2"
              :key="item.title"
              v-model="item.active"
              :prepend-icon="item.action"
              no-action
            >
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title
                    @click="selectSong"
                    v-text="item.title"
                    v-bind:id="item.id"
                  ></v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item v-for="child in item.items" :key="child.title">
                <v-list-item-content @click="clickedOption">
                  <v-list-item-title v-text="child.title"></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-group>
          </v-list>
        </v-card>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import api from "../api";
import router from "@/router";

var selected_song;
var blop;

export default {
  name: "MusicList",
  data: () => ({
    items2: [],
    items: [],
  }),
  methods: {
    getSongs() {
      this.refreshSongs();
    },
    selectSong(item) {
      console.log(item.srcElement.id);
      selected_song = item.srcElement;
    },
    clickedOption(option) {
      console.log(option.srcElement.innerHTML);
      if (option.srcElement.innerHTML === "Go to player (Beta Version)")
        router.push("/musiclist/musicplayer");
      else if (option.srcElement.innerHTML === "Play") {
        this.doTheThing();
        console.log(blop);
        var mp3 = new Blob([blop], {
          type: "application/octet-stream",
        });

        console.log(mp3);
        var url = (window.URL || window.webkitURL).createObjectURL(mp3);

        document.getElementById("myAudio").setAttribute("src", url); //# updates the source of the Audio tag
        document.getElementById("myAudio").play();
      } else if (option.srcElement.innerHTML === "Pause") {
        document.getElementById("myAudio").pause();
      }
    },
    myDownload() {
      var sampleBytes = blop;

      var saveByteArray = (function () {
        var a = document.createElement("a");
        document.body.appendChild(a);
        a.style = "display: none";
        return function (data, name) {
          var blob = new Blob(data, { type: "octet/stream" }),
            url = window.URL.createObjectURL(blob);
          a.href = url;
          a.download = name;
          a.click();
          window.URL.revokeObjectURL(url);
        };
      })();

      saveByteArray([sampleBytes], "music.mp3");
    },
    async doTheThing() {
      blop = await api.songs.getMp3(selected_song);
    },
    async refreshSongs() {
      this.items2 = await api.songs.getSongs();
      this.items = await api.songs.getSongsOnline();
    },
  },
};
</script>

<style scoped></style>
