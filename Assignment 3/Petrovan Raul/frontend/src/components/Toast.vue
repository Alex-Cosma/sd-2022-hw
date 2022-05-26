<template>

</template>

<script>
import { mapActions } from 'vuex'
import { ADD_TOAST_MESSAGE } from 'vuex-toast'


export default {
  name: "Toast",
  data() {
    return {
      connected: false,
    };
  },
  methods: {
    ...mapActions({
                 addToast: ADD_TOAST_MESSAGE
               }),
    sendNotification(text) {
      this.addToast({
        text,
        type: 'success',
        dismissAfter: 10000
      })
    }
  },
}
</script>

<style scoped>
</style>