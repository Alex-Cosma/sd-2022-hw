<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create item" : "Edit item" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="item.name" label="Name" />
          <v-text-field v-model="item.author" label="Author" />
          <v-text-field v-model="item.genre" label="Genre" />
          <v-text-field v-model="item.description" label="Description" />
          <v-text-field v-model="item.quantity" label="Quantity" />
          <v-text-field v-model="item.price" label="Price" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="remove" v-if = "isNew === false">
            {{ "Delete" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ItemDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    remove() {
        api.items
            .remove({
                id: this.item.id,
                name: this.item.name,
                author: this.item.author,
                genre: this.item.genre,
                description: this.item.description,
                quantity: this.item.quantity,
                price: this.item.price
            })
            .then(() => this.$emit("refresh"));
    },
    persist() {
      if (this.isNew) {
        api.items
          .create({
            name: this.item.name,
            author: this.item.author,
            genre: this.item.genre,
            description: this.item.description,
            quantity: this.item.quantity,
            price: this.item.price,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.items
          .edit({
            id: this.item.id,
            name: this.item.name,
            author: this.item.author,
            genre: this.item.genre,
            description: this.item.description,
            quantity: this.item.quantity,
            price: this.item.price,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
  },
};
</script>

<style scoped></style>
