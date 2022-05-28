<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create Accommodation" : "Edit Accommodation" }}
        </v-toolbar>
        <v-form v-if="isNew">
          <v-text-field v-model="user.name" label="Name"/>
          <v-text-field v-model="user.description" label="Description"/>
          <v-text-field v-model="user.house_rules" label="House rules"/>
          <v-text-field v-model="user.property_type" label="Property type"/>
          <v-text-field v-model="user.room_type" label="Room type"/>
          <v-text-field v-model="user.bathrooms" label="Bathrooms"/>
          <v-text-field v-model="user.bedrooms" label="Bedrooms"/>
          <v-text-field v-model="user.beds" label="Beds"/>
          <v-text-field v-model="user.price" label="Price/night ($)"/>
          <v-text-field v-model="user.amenities" label="Amenities"/>
          <v-text-field v-model="user.imageURL" label="Image url"/>
          <v-text-field v-model="user.address_street" label="Street"/>
          <v-text-field v-model="user.address_city" label="City"/>
        </v-form>

        <v-form v-if="!isNew">
          <v-text-field v-model="user.name" label="Name"/>
          <label>Description</label>
          <textarea v-model="user.description" style="width: 600px; height: 200px"></textarea>
          <label>House rules</label>
          <textarea v-model="user.house_rules" style="width: 600px; height: 200px; margin-top: 20px"></textarea>
          <v-text-field v-model="user.property_type" label="Property type"/>
          <v-text-field v-model="user.room_type" label="Room type"/>
          <v-text-field v-model="user.bathrooms" label="Bathrooms"/>
          <v-text-field v-model="user.bedrooms" label="Bedrooms"/>
          <v-text-field v-model="user.beds" label="Beds"/>
          <v-text-field v-model="user.price" label="Price/night ($)"/>
          <v-text-field v-model="user.amenities" label="Amenities" value="user.amenities.amenity.join(', ')"/>
          <v-text-field v-model="user.imageURL.picture_url" label="Image url"/>
          <v-text-field v-model="user.address.city" label="Street"/>
          <v-text-field v-model="user.address.street" label="City"/>
        </v-form>

        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
const auth_user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.items
            .create({
              name: this.user.name,
              description: this.user.description,
              property_type: this.user.property_type,
              house_rules: this.house_rules,
              room_type: this.user.room_type,
              bathrooms: this.user.bathrooms,
              bedrooms: this.user.bedrooms,
              beds: this.user.beds,
              price: this.user.price,
              amenities: this.user.amenities,
              imageURL: this.user.imageURL,
              address_street: this.user.address_street,
              address_city: this.user.address_city,
              user_id: auth_user.id,
            })
            .then(() => this.$emit("refresh"));
      } else {
        let amenitiesStr = "";
        console.log(amenitiesStr);
        for(let i=0; i<this.user.amenities.length; i++){
          amenitiesStr += this.user.amenities[i].amenity;
        }
        api.items
            .edit({
              id: this.user.id,
              name: this.user.name,
              description: this.user.description,
              property_type: this.user.property_type,
              room_type: this.user.room_type,
              house_rules: this.house_rules,
              bathrooms: this.user.bathrooms,
              bedrooms: this.user.bedrooms,
              beds: this.user.beds,
              price: this.user.price,
              amenities: amenitiesStr,
              imageURL: this.user.imageURL.picture_url,
              address_street: this.user.address.street,
              address_city: this.user.address.city,
              user_id: auth_user.id,
            })
            .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.user.id;
    },
  },
};
</script>

<style scoped></style>