<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="800px"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{
            isNew
              ? "Create court"
              : "Please make sure this is the court you want to play at!"
          }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="court.name" label="Name" />
          <v-text-field v-model="court.availability" label="Availability" />
          <v-text-field v-model="court.sportType" label="Sport Type" />
        </v-form>
        <v-card-actions>
          <v-btn v-on:click.once="makeReservation">Make reservation</v-btn>
          <v-snackbar v-model="snackbar">
            {{ snackbarText }}
            <v-btn color="green" text @click="snackbar = false">Close</v-btn>
          </v-snackbar>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  data() {
    return {
      snackbar: false,
      snackbarText:
        "You have successfully made a reservation! Please check your email for confirmation.",
    };
  },
  name: "CourtDialog",
  props: {
    court: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.courts
          .create({
            name: this.court.name,
            availability: this.court.availability,
            sportType: this.court.sportType,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    makeReservation() {
      if (this.court.availability === "BUSY") {
        this.btn = false;
        alert("Court is busy");
      } else {
        //console.log(localStorage.getItem("username"));
        var userObj = {};
        userObj.username = localStorage.getItem("username");
        api.courts
          .reserve(this.court, localStorage.getItem("username"))
          .then(() => this.$emit("refresh"));
        this.snackbar = true;
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.court.id;
    },
  },
};
</script>

<style scoped></style>
