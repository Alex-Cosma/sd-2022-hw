<template>
  <v-dialog max-width="500px" :value="opened">
    <v-card>
      <v-card-title class="text-h5"
        >Are you sure you want to delete this item?</v-card-title
      >
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
        <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
        <v-spacer></v-spacer>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "DeleteDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  selectedItem: {},
  methods: {
    deleteItemConfirm() {
      api.items
        .deleteItm({
          id: this.item.id,
          title: this.item.title,
          author: this.item.author,
          genre: this.item.genre,
          quantity: this.item.quantity,
        })
        .then(() => this.$emit("refresh"));
    },
    closeDelete() {
      this.$emit("refresh");
    },
  },
};
</script>

<style scoped></style>
