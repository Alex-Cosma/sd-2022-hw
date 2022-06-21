<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
    @click:outside="$emit('close')"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create property" : "Edit property" }}
        </v-toolbar>
        <v-form @submit="checkForm">
          <v-text-field v-model="property.name" label="Title" />
          <v-text-field v-model="property.owner" label="Owner" />
          <v-text-field v-model="property.address" label="Address" />
          <v-text-field v-model="property.price" label="Price" />
          <v-text-field v-model="property.description" label="Description" />
          <v-text-field v-model="property.numberOfRooms" label="NumberOfRooms" />
          <v-text-field v-model="property.numberOfBeds" label="NumberOfBeds" />
          <v-text-field v-model="property.numberOfBathrooms" label="NumberOfBathrooms" />
          <v-text-field v-model="property.kitchen" label="Kitchen" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
        </v-card-actions>
        <v-card-actions>
          <v-btn @click="deleteProperty">
            {{ isNew ? "" : "Delete" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "PropertyDialog",
  props: {
    property: Object,
    opened: Boolean,
  },
  methods: {
    checkForm: function () {

          return true //TODO
      //   this.title &&
      //   this.author &&
      //   this.price &&
      //   this.quantity &&
      //   this.description
      // )
      //   return true;
      // this.errors = [];
      // if (!this.title) this.errors.push("Name required.");
      // if (!this.author) this.errors.push("Age required.");
      // if (!this.price) this.errors.push("Age required.");
      // if (!this.quantity) this.errors.push("Age required.");
      // if (!this.description) this.errors.push("Age required.");
      // e.preventDefault();
    },
    persist() {
      if (this.isNew) {
        api.properties
          .create({
            name: this.property.name,
            owner: this.property.owner,
            address: this.property.address,
            picturesId: this.property.picturesId,
            price: this.property.price,
            rating: this.property.rating,
            description: this.property.description,
            numberOfRooms: this.property.numberOfRooms,
            numberOfBeds: this.property.numberOfBeds,
            numberOfBathrooms: this.property.numberOfBathrooms,
            kitchen: this.property.kitchen,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.properties
          .edit({
            id: this.property.id,
            name: this.property.name,
            owner: this.property.owner,
            address: this.property.address,
            picturesId: this.property.picturesId,
            price: this.property.price,
            rating: this.property.rating,
            description: this.property.description,
            numberOfRooms: this.property.numberOfRooms,
            numberOfBeds: this.property.numberOfBeds,
            numberOfBathrooms: this.property.numberOfBathrooms,
            kitchen: this.property.kitchen,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteProperty() {
      if (!this.isNew) {
        console.log("delete");
        api.properties
          .deleteProperty({
            id: this.property.id,
            name: this.property.name,
            owner: this.property.owner,
            address: this.property.address,
            picturesId: this.property.picturesId,
            price: this.property.price,
            rating: this.property.rating,
            description: this.property.description,
            numberOfRooms: this.property.numberOfRooms,
            numberOfBeds: this.property.numberOfBeds,
            numberOfBathrooms: this.property.numberOfBathrooms,
            kitchen: this.property.kitchen,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.property.id;
    },
  },
};
</script>

<style scoped></style>
