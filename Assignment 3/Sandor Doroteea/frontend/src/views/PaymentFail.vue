<template>
  <div class="text-center">
    <div class="spinner-grow text-danger" role="status">
      <span class="sr-only">Loading...</span>
      <span class="sr-only">Payment not successful...</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Failure",
  methods: {
    goBack() {
      this.$router.push("/tracksApi");
    },
  },
  mounted() {
    localStorage.removeItem("sessionId");
    window.location.href = "/tracksAPI";
  },
};
</script>
