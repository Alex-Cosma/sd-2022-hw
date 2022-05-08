<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
        </v-toolbar>
        <v-card-actions>
          <v-btn @click="persist">
            Sell Item
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ItemDialogSell",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if(this.item.quantity > 0){
        api.items
          .edit({
            id: this.item.id,
            name: this.item.name,
            author: this.item.author,
            genre: this.item.genre,
            description: this.item.description,
            quantity: this.item.quantity - 1,
            price: this.item.price,
          })
          .then(() => this.$emit("refresh"));
      }
      else {
        console.log("Insufficient quantity")
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