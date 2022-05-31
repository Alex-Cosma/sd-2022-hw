<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="800px"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create court" : "Edit court" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="court.name" label="Name" />
          <v-text-field v-model="court.availability" label="Availability" />
          <v-text-field v-model="court.sportType" label="Sport Type" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteCourt">Delete Court</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "CourtDialogForAdmin",
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
      } else {
        api.courts
          .edit({
            id: this.court.id,
            name: this.court.name,
            availability: this.court.availability,
            sportType: this.court.sportType,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteCourt() {
      api.courts
        .delete({ id: this.court.id })
        .then(() => this.$emit("refresh"));
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
