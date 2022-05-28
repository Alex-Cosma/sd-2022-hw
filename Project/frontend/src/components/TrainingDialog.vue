<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create training" : "Edit training" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="training.title" label="Title" />
          <v-text-field v-model="training.type" label="Type" />
          <v-text-field v-model="training.date" label="Date" />
          <v-select v-model="training.location" label="Location" :items="locations" />
          <v-select v-model="training.user" label="User" :items="users" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteB">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "TrainingDialog",
  props: {
    training: Object,
    opened: Boolean,
  },
  data() {
    return {
      users: ["admin", "trainer", "regular_user"],
      locations: ["Calea Turzii 178", "Campului 87", "Observatorului 82"],
    };
  },
  methods: {
    deleteB() {
      api.trainings.delete(this.training.id);
      this.$emit("refresh");
    },
    persist() {
      if (this.isNew) {
        api.trainings
          .create({
            title: this.training.title,
            type: this.training.type,
            date: this.training.date,
            location: this.training.location,
            user: this.training.user,
          })
          .then(() => this.$emit("refresh"));
        this.users = api.users.allUsers();
      } else {
        api.trainings
          .edit(this.training.id, {
            id: this.training.id,
            title: this.training.title,
            type: this.training.type,
            date: this.training.date,
            location: this.training.location,
            user: this.training.user,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.training.id;
    },
  },
};
</script>

<style scoped></style>
