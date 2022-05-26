<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create driver" : "Edit driver" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="driver.name" label="Name" />
          <v-text-field v-model="driver.email" label="Email" />
          <v-text-field v-model="driver.sectionNumber" label="Section Number" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
        </v-card-actions>
        <v-text-field v-model="type" label="Name" />
        <v-btn @click="handleFine">
          {{ this.$store.getters["auth/isAdmin"] ? "Pay" : "Give" }}
        </v-btn>

      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "DriverDialog",
  props: {
    driver: Object,
    fine:Object,
    type:String,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.drivers
            .create({
              name: this.driver.name,
              email: this.driver.email,
              sectionNumber: this.driver.sectionNumber,
            })
            .then(() => this.$emit("refresh"));
      } else {
        api.drivers
            .edit({
              id: this.driver.id,
              name: this.driver.name,
              email: this.driver.email,
              sectionNumber: this.driver.sectionNumber,
            })
            .then(() => this.$emit("refresh"));
      }
    },
    handleFine() {
      if (this.$store.getters["auth/isAdmin"]) {
        api.drivers
            .payFine({
              id: this.driver.id,
              name: this.driver.name,
              email: this.driver.email,
              sectionNumber: this.driver.sectionNumber,
            },{type:this.type})
            .then(() => this.$emit("refresh"));
      } else {
        api.drivers
            .giveFine({
              id: this.driver.id,
              name: this.driver.name,
              email: this.driver.email,
              sectionNumber: this.driver.sectionNumber,
            },{type:this.type})
            .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.driver.id;
    },
  },
};
</script>

<style scoped></style>