<template>
  <v-container fluid>
    <v-row align="center">
      <v-select
        @change="onChange($event)"
        v-model="selectedGenre"
        :items="genres"
        label="Genre"
      ></v-select>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "GenreDD",
  data() {
    return {
      genres: [
        { text: "Bildungsroman", value: "Bildungsroman" },
        { text: "Biography", value: "Biography" },
        { text: "Dystopian", value: "Dystopian" },
        { text: "Fantasy", value: "Fantasy" },
        { text: "Fiction", value: "Fiction" },
        { text: "Historical", value: "Historical" },
        { text: "HistoricalFiction", value: "HistoricalFiction" },
        { text: "Horror", value: "Horror" },
        { text: "MagicalRealism", value: "MagicalRealism" },
        { text: "Mystery", value: "Mystery" },
        { text: "NonFiction", value: "NonFiction" },
        { text: "Poetry", value: "Poetry" },
        { text: "RealistLiterature", value: "RealistLiterature" },
        { text: "Romance", value: "Romance" },
        { text: "ScienceFiction", value: "ScienceFiction" },
        { text: "Thriller", value: "Thriller" },
        { text: "Western", value: "Western" },
        { text: "YoungAdult", value: "YoungAdult" },
      ],
      selectedGenre: "",
    };
  },
  methods: {
    onChange(event) {
      this.$emit("genre-change", event);
    },
  },
};
</script>

<style scoped></style>
