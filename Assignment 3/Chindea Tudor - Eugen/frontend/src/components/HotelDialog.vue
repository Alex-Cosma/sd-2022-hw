<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create hotel" : "Edit hotel" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="hotel.name" label="Name" />
          <v-text-field v-model="hotel.places" label="Places" />
          <v-text-field v-model="hotel.price" label="Price" />
          <v-text-field v-model="hotel.cityName" label="City" />

        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deletehotel" v-if="!isNew"> Delete </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "hotelDialog",
  props: {
    hotel: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.hotels
          .create({
            name: this.hotel.name,
            places: this.hotel.places,
            cityName: this.hotel.cityName,
            price: this.hotel.price,
          })
          .then(() => this.$emit("refresh"));
      } else {
        console.log(this.hotel.cityName);
        api.hotels
          .edit({
            id: this.hotel.id,
            name: this.hotel.name,
            places: this.hotel.places,
            cityName: this.hotel.cityName,
            price: this.hotel.price,
          })
          .then(() => this.$emit("refresh"));
      }
    },
     deletehotel() {
      if (!this.isNew) {
        api.hotels
          .delete({
            id: this.hotel.id,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    
  },
  computed: {
    isNew: function () {
      return !this.hotel.id;
    },
  },
};
</script>

<style scoped></style>
