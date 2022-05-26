<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Create category </v-toolbar>
        <v-form>
          <v-text-field v-model="category.name" label="Name" />
          <v-text-field v-model="category.description" label="Description" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Create </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";
import { auth as store } from "@/store/auth.module";

export default {
  name: "AddThreadDialog",
  props: {
    category: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      api.categories
        .create({
          name: this.category.name,
          description: this.category.description,
        })
        .then(
          () => this.$emit("refresh"),
          (error) => alert(error.response.data.message)
        );
    },
  },
  computed: {
    isAdmin: function () {
      return store.getters.isAdmin(store.state);
    },
  },
};
</script>

<style scoped></style>
