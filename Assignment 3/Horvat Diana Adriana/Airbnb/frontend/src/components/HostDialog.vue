<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  ><template>
    <v-card>
      <v-toolbar color="primary" dark>
        {{ isNew ? "Create item" : "Accommodation details" }}
      </v-toolbar>
      <v-form>
        <v-text-field v-model="item.name" value="item.name" readonly />
      </v-form>
      <v-card-actions>
        <v-btn @click="persist">
          Save Account
        </v-btn>
      </v-card-actions>
    </v-card>
  </template>

  </v-dialog>
</template>

<script>
export default {
  name: "HostDialog",
  props: {
    items: Object,
    opened: Boolean,
  },
}
</script>

<style scoped>

</style>