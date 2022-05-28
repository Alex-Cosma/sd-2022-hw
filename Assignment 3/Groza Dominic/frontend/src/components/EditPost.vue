<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          Edit post
        </v-toolbar>
        <v-form>
          <v-textarea v-model="post.body" label="Post"/>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ "Submit changes" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";

export default {
  name: "EditPost",
  props: {
    post: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      api.posts.edit(this.post)
          .then(() =>   window.location.reload());
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
