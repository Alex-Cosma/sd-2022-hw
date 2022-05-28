<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create fine" : "Edit fine" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="item.type" label="Type" />
          <v-text-field v-model="item.description" label="Description" />
          <v-text-field v-model="item.price" label="Price" />
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

export default {
  name: "ItemDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.fines
          .create({
            type: this.item.type,
            description: this.item.description,
            price:this.item.price,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.fines
          .edit({
            id: this.item.id,
            type: this.item.type,
            description: this.item.description,
            price:this.item.price,
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
