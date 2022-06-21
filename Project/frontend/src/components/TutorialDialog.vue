<template>
  <div class="container">
    <div class="large-12 medium-12 small-12 cell">
      <label>File
        <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
      </label>
      <button v-on:click="submitFile()">Upload file</button>
    </div>
  </div>
</template>

<script>

import api from "../api";
export default {
  name: "TutorialDialog",
  data(){
    return {
      file: ''
    }
  },

  methods: {

    submitFile(){

      let formData = new FormData();

      formData.append('title', this.file.name);
      formData.append('file', this.file);

      api.tutorials.create(formData);
    },

    handleFileUpload(){
      this.file = this.$refs.file.files[0];
    }
  }
}
</script>

<style scoped></style>