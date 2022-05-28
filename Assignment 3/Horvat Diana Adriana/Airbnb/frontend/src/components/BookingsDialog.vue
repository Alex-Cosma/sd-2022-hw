<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          Bookings
        </v-toolbar>
        <v-data-table
            :headers="headers"
            :items="items">
          <template v-slot:item="row">
            <tr>
              <td>{{ row.item.start_date }}</td>
              <td>{{ row.item.end_date }}</td>
              <td>
                <v-btn color="red" @click="cancelBooking(row.item.id)">
                  Cancel
                </v-btn>
              </td>
            </tr>
          </template>

        </v-data-table>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookingsDialog",
  props: {
    items: Array,
    opened: Boolean,
  },
  data() {
    return {
      headers: [
        {
          text: "Start date",
          align: "start",
          sortable: false,
          value: "title",
        },
        {
          text: "End date",
          align: "start",
          sortable: false,
          value: "title",
        },
        {
          text: "",
          align: "start",
          sortable: false,
          value: "title",
        },
      ],
    };

  },
  methods: {
    cancelBooking(booking_id) {
      api.items.cancelBooking(booking_id)
          .then(() => this.$emit("refresh"));
    },

  },
}
</script>

<style scoped>

</style>