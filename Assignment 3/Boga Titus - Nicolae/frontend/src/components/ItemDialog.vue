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
          <v-text-field v-model="item.artist" label="Artist" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteItem">
            {{ " Delete " }}
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
    persist() {
      if (this.isNew) {
        api.items
          .create({
            title: this.item.title,
            artist: this.item.artist,
            album: "Unknown",
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.items
          .edit({
            id: this.item.id,
            title: this.item.title,
            artist: this.item.artist,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteItem() {
      api.items.delete(this.item).then(() => this.$emit("refresh"));
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
