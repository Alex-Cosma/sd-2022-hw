<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isEnough ? "Book sold!" : "You cannot sell this book" }}
        </v-toolbar>
        <v-card-actions>
          <v-btn @click="sellItem">
            {{ isEnough ? "Cancel" : "Cancel" }}
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
    sellItem() {
      if (this.isEnough) {
        api.items
          .edit({
            id: this.item.id,
            title: this.item.title,
            author: this.item.author,
            genre: this.item.genre,
            quantity: this.item.quantity - 1,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.items
          .edit({
            id: this.item.id,
            title: this.item.title,
            author: this.item.author,
            genre: this.item.genre,
            quantity: this.item.quantity,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isEnough: function () {
      return this.item.quantity > 0;
    },
  },
};
</script>

<style scoped></style>
