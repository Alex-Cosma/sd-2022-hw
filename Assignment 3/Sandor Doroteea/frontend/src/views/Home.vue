<template>
  <div class="home">
    <websocket-greetings></websocket-greetings>
    <img alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="Welcome to Your Vue.js App" />
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/Login.vue";
import WebsocketGreetings from "@/components/WebsocketGreetings.vue";

export default {
  name: "Home",
  components: {
    HelloWorld,
    WebsocketGreetings,
  },
};
</script>
