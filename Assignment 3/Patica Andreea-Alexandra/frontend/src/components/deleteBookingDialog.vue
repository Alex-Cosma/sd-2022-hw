<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
      @click:outside="$emit('close')"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "What do you want to do?" }}
        </v-toolbar>
        <!--        <v-form @submit="checkForm">-->
        <!--        </v-form>-->
        <v-card-actions>
          <v-btn @click="deleteBooking">
            {{ "Delete!" }}
          </v-btn>
          <v-btn @click="downloadBooking">
            {{ "Download PDF!" }}
          </v-btn>
        </v-card-actions>
        <!--        <v-card-actions>-->
        <!--          <v-btn @click="deleteProperty">-->
        <!--            {{ isNew ? "" : "Delete" }}-->
        <!--          </v-btn>-->
        <!--        </v-card-actions>-->
      </v-card>
    </template>
  </v-dialog>

</template>

<script>
import api from "../api";

export default {
  name: "DeleteBookingDialog",
  props: {
    booking: Object,
    opened: Boolean,
  },
  methods: {
    checkForm: function () {

      return true //TODO
      //   this.title &&
      //   this.author &&
      //   this.price &&
      //   this.quantity &&
      //   this.description
      // )
      //   return true;
      // this.errors = [];
      // if (!this.title) this.errors.push("Name required.");
      // if (!this.author) this.errors.push("Age required.");
      // if (!this.price) this.errors.push("Age required.");
      // if (!this.quantity) this.errors.push("Age required.");
      // if (!this.description) this.errors.push("Age required.");
      // e.preventDefault();
    },
    deleteBooking() {
      console.log("delete");
      console.log(this.booking);
      api.book
          .deleteBooking({
            id: this.booking.id,
            property: this.booking.property,
            user: this.booking.user,
            date: this.booking.date,
          })
          .then(() => this.$emit("refresh"));
    },
    downloadBooking() {
      api.book
          .downloadBooking({
            property: this.booking.property,
            user: this.booking.user,
            date: this.booking.date,
          })
          .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.booking.id;
    },
  },
};
</script>

<style scoped></style>
