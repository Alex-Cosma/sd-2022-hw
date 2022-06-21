<template>
  <v-img
      src="http://localhost:3077/images/logo.png"
      @click="$router.push({name: 'main'})"
  ></v-img>
</template>

<script>
export default {
  name: "SiteLogo"
}
</script>

<style scoped>
  .v-img:hover {
    cursor: pointer;
  }
</style>