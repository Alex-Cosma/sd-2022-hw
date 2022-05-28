<template>
  <div>
    <title>Simple Music Player</title>
    <!-- Load FontAwesome icons -->
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css"
    />

    <!-- Load the custom CSS style file -->
    <link rel="stylesheet" type="text/css" href="src/views/MusicPlayer.css" />

    <div class="player">
      <!-- Define the section for displaying details -->
      <div class="details">
        <div class="now-playing">PLAYING x OF y</div>
        <div class="track-art"></div>
        <div class="track-name">Track Name</div>
        <div class="track-artist">Track Artist</div>
      </div>

      <!-- Define the section for displaying track buttons -->
      <div class="buttons">
        <div class="prev-track" @click="prevTrack">
          <i class="fa fa-step-backward fa-2x"></i>
        </div>
        <div class="playpause-track" @click="playpauseTrack">
          <i class="fa fa-play-circle fa-5x"></i>
        </div>
        <div class="next-track" @click="nextTrack">
          <i class="fa fa-step-forward fa-2x"></i>
        </div>
      </div>

      <!-- Define the section for displaying the seek slider-->
      <div class="slider_container">
        <div id="current-time" class="current-time">00:00</div>
        <input
          type="range"
          min="1"
          max="100"
          value="0"
          class="seek_slider"
          @change="seekTo"
        />
        <div class="total-duration">00:00</div>
      </div>

      <!-- Define the section for displaying the volume slider-->
      <div class="slider_container">
        <i class="fa fa-volume-down"></i>
        <input
          type="range"
          min="1"
          max="100"
          value="99"
          class="volume_slider"
          @change="setVolume"
        />
        <i class="fa fa-volume-up"></i>
      </div>
    </div>
  </div>
</template>

<script>
// Specify globally used values
let track_index = 0;
// eslint-disable-next-line no-unused-vars
let isPlaying = false;
let updateTimer;

// Create the audio element for the player
let curr_track = document.createElement("audio");

// Define the list of tracks that have to be played
let track_list = [
  {
    name: "All in all",
    artist: "Dexys Midnight Runners",
    image: "src/views/too_rye_ayy.png",
    path: "src/views/all_in_all.mp3",
  } /*,
  {
    name: "Come on Eileen",
    artist: "Tours",
    image: "too_rye_ayy.png",
    path: "come_on_eileen.mp3",
  },
  {
    name: "Otherside",
    artist: "RHCP",
    image: "too_rye_ayy.png",
    path: "otherside.wma",
  },*/,
];

function loadTrack(track_index) {
  console.log("in load track function");
  // Clear the previous seek timer
  clearInterval(updateTimer);
  console.log("in load track function2");
  resetValues();
  // Load a new track
  curr_track.src = track_list[track_index].path;
  curr_track.load();
  console.log(curr_track);

  console.log("Loaded!!");
  // Update details of the track
  document.querySelector(".track-art").style.backgroundImage =
    "url(" + track_list[track_index].image + ")";
  document.querySelector(".track-name").textContent =
    track_list[track_index].name;
  document.querySelector(".track-artist").textContent =
    track_list[track_index].artist;
  document.querySelector(".now-playing").textContent =
    "PLAYING " + (track_index + 1) + " OF " + track_list.length;

  // Set an interval of 1000 milliseconds
  // for updating the seek slider
  console.log("on!");
  updateTimer = setInterval(seekUpdate, 1000);

  // Move to the next track if the current finishes playing
  // using the 'ended' event
  console.log("on!");

  curr_track.addEventListener("ended", nextTrack);

  console.log("on!");

  // Apply a random background color
  random_bg_color();
  console.log("on!");
}

function random_bg_color() {
  // Get a random number between 64 to 256
  // (for getting lighter colors)
  let red = Math.floor(Math.random() * 256) + 64;
  let green = Math.floor(Math.random() * 256) + 64;
  let blue = Math.floor(Math.random() * 256) + 64;

  // Construct a color withe the given values
  let bgColor = "rgb(" + red + ", " + green + ", " + blue + ")";

  // Set the background to the new color
  document.body.style.background = bgColor;
}

// Function to reset all values to their default
function resetValues() {
  console.log("here!");
  document.querySelector("#current-time").textContent = "00:00";
  console.log("here!2");
  document.querySelector(".total-duration").textContent = "00:00";
  console.log("here!3");
  document.querySelector(".seek_slider").value = 0;
  console.log("here!");
}

function seekUpdate() {
  let seekPosition = 0;

  // Check if the current track duration is a legible number
  if (!isNaN(curr_track.duration)) {
    seekPosition = curr_track.currentTime * (100 / curr_track.duration);
    document.querySelector(".seek_slider").value = seekPosition;

    // Calculate the time left and the total duration
    let currentMinutes = Math.floor(curr_track.currentTime / 60);
    let currentSeconds = Math.floor(
      curr_track.currentTime - currentMinutes * 60
    );
    let durationMinutes = Math.floor(curr_track.duration / 60);
    let durationSeconds = Math.floor(
      curr_track.duration - durationMinutes * 60
    );

    // Add a zero to the single digit time values
    if (currentSeconds < 10) {
      currentSeconds = "0" + currentSeconds;
    }
    if (durationSeconds < 10) {
      durationSeconds = "0" + durationSeconds;
    }
    if (currentMinutes < 10) {
      currentMinutes = "0" + currentMinutes;
    }
    if (durationMinutes < 10) {
      durationMinutes = "0" + durationMinutes;
    }

    // Display the updated duration
    document.querySelector("#current-time").textContent =
      currentMinutes + ":" + currentSeconds;
    document.querySelector(".total-duration").textContent =
      durationMinutes + ":" + durationSeconds;
  }
}

function nextTrack() {
  // Go back to the first track if the
  // current one is the last in the track list
  if (track_index < track_list.length - 1) track_index += 1;
  else track_index = 0;

  // Load and play the new track
  loadTrack(track_index);
  playTrack();
}

function playTrack() {
  // Play the loaded track
  console.log("play1");
  curr_track.play();
  isPlaying = true;

  console.log("play2");
  // Replace icon with the pause icon
  document.querySelector(".playpause-track").innerHTML =
    '<i class="fa fa-pause-circle fa-5x"></i>';
  console.log("play3");
}

function pauseTrack() {
  // Pause the loaded track
  curr_track.pause();
  isPlaying = false;

  // Replace icon with the play icon
  document.querySelector(".playpause-track").innerHTML =
    '<i class="fa fa-play-circle fa-5x"></i>';
}

export default {
  name: "MusicPlayer",
  methods: {
    prevTrack() {
      console.log("THIS!!!!!!!!!!!!!");
      if (track_index > 0) track_index -= 1;
      else track_index = track_list.length - 1;

      // Load and play the new track
      loadTrack(track_index);
      playTrack();
    },
    playpauseTrack() {
      if (!isPlaying) {
        playTrack();
      } else pauseTrack();
    },
    nextTrack() {
      // Go back to the first track if the
      // current one is the last in the track list
      if (track_index < track_list.length - 1) track_index += 1;
      else track_index = 0;

      // Load and play the new track
      loadTrack(track_index);
      playTrack();
    },
    seekTo() {
      // Calculate the seek position by the
      // percentage of the seek slider
      // and get the relative duration to the track
      let seekto;
      seekto =
        curr_track.duration *
        (document.querySelector(".seek_slider").value / 100);

      // Set the current track position to the calculated seek position
      curr_track.currentTime = seekto;
    },
    setVolume() {
      // Set the volume according to the
      // percentage of the volume slider set
      curr_track.volume = document.querySelector(".volume_slider").value / 100;
    },
  },
};
</script>

<style scoped>
@import "./MusicPlayer.css";
</style>
