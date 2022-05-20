<template>
  <v-container fluid>
    <v-row align="center">
      <v-select
        @change="onChange($event)"
        v-model="selectedRole"
        :items="roles"
        label="Role"
      ></v-select>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "RoleDD.vue",
  data() {
    return {
      roles: [
        { text: "MANAGER", value: "MANAGER" },
        { text: "CUSTOMER", value: "CUSTOMER" },
      ],
      selectedRole: "",
    };
  },
  methods: {
    onChange(event) {
      this.$emit("role-change", event);
    },
  },
};
</script>

<style scoped></style>
