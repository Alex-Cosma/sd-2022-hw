<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          Share something to your friends (that is, if you have any)
        </v-toolbar>
        <v-form>
          <v-textarea v-model="textAreaPost" label="Post"/>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ "Submit post!" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
// import api from "../api";
const user1 = JSON.parse(localStorage.getItem("user"));

import api from "@/api";

export default {
  name: "NewPost",
  props: {
    opened: Boolean,
  },
  data(){
    return {
      textAreaPost: "",
    }
  },
  methods: {
    persist() {
      api.posts.create({
            body: this.textAreaPost,
            user:user1,
          })
          .then(() => this.$emit("refresh"));
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
