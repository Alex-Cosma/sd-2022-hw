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
          <v-text-field v-model="item.title" label="Title" />
          <v-text-field v-model="item.author" label="Author" />
          <GenreDD @genre-change="setGenre($event)" />
          <v-text-field v-model="item.pages" label="Pages" />
          <v-text-field v-model="item.year" label="Year" />
          <v-text-field v-model="item.quantity" label="Quantity" />
          <v-text-field v-model="item.description" label="Description" />
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
import GenreDD from "@/components/GenreDD";

export default {
  name: "ItemDialog",
  components: { GenreDD },
  props: {
    item: Object,
    opened: Boolean,
  },
  data() {
    return {
      chosenGenre: "",
    };
  },
  methods: {
    setGenre(e) {
      console.log(e);
      this.item.genre = e;
    },
    persist() {
      if (this.isNew) {
        api.items
          .create({
            title: this.item.title,
            author: this.item.author,
            genre: this.item.genre,
            quantity: this.item.quantity,
            year: this.item.year,
            pages: this.item.pages,
            description: this.item.description,
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
            year: this.item.year,
            pages: this.item.pages,
            description: this.item.description,
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
