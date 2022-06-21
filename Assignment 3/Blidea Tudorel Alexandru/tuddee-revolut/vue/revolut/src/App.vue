<template>
  <v-app>
      <router-view/>
  </v-app>
</template>

<script>

export default {
  name: 'App',
  data() {
    return {
      collapseOnScroll: true
    }
  },
}
</script>

<style>
@import './assets/css/variables.css';
@import './assets/css/stylish.css';
@import './assets/css/containers.css';
@import './assets/css/scrollbar.css';
</style>
