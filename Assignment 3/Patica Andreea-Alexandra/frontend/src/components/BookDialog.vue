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
          {{ "Want to book?" }}
        </v-toolbar>
<!--        <v-form @submit="checkForm">-->
          <p>Name: {{ property.name }}</p>
<!--        </v-form>-->
        <v-card-actions>
          <v-btn @click="persist">
            {{ "Book!" }}
          </v-btn>
        </v-card-actions>
<!--        <v-card-actions>-->
<!--          <v-btn @click="deleteProperty">-->
<!--            {{ isNew ? "" : "Delete" }}-->
<!--          </v-btn>-->
<!--        </v-card-actions>-->
      </v-card>
    </template>
  </v-dialog>

</template>

<script>
import api from "../api";

export default {
  name: "BookDialog",
  props: {
    username: String,
    property: Object,
    arrivalDate: Date,
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
      api.book
          .create({
            username: this.username,
            property: this.property,
            date: this.arrivalDate,
          })
          .then(() => this.$emit("refresh"));
    },
    deleteProperty() {
      if (!this.isNew) {
        console.log("delete");
        api.properties
            .deleteProperty({
              id: this.property.id,
              title: this.property.title,
              author: this.property.author,
              price: this.property.price,
              quantity: this.property.quantity,
              description: this.property.description,
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
