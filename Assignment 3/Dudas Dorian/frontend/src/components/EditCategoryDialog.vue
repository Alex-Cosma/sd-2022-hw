<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar v-if="isAdmin" color="primary" dark> Edit </v-toolbar>
        <v-form v-if="isAdmin">
          <v-text-field v-model="category.name" label="Name" />
          <v-text-field v-model="category.description" label="Description" />
        </v-form>
        <v-card-actions>
          <v-btn v-if="isAdmin" @click="persist"> Save </v-btn>
          <v-btn v-if="isAdmin" @click="deleteCategory"> Delete </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";
import { auth as store } from "@/store/auth.module";

export default {
  name: "EditCategoryDialog",
  props: {
    category: Object,
    opened: Boolean,
  },
  methods: {
    deleteCategory() {
      api.categories.delete(this.category).then(
        () => this.$emit("refresh"),
        (error) => alert(error.response.data.message)
      );
    },
    persist() {
      api.categories
        .edit({
          id: this.category.id,
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
