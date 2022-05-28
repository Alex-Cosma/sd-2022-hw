<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-data-table
          :headers="headers"
          :items="orders"
          class="elevation-1"
          hide-default-footer
          @click:row="onClickRow"
        >
          <template v-slot:item="row">
            <tr>
              <td>{{ row.item.id }}</td>
              <td>{{ row.item.deliveryDate }}</td>
              <td>{{ row.item.returnDate }}</td>
              <td>
                <v-btn
                  color="light-blue"
                  depressed
                  small
                  rounded
                  @click="deleteOrder(row.item.id)"
                >
                  Return Order
                </v-btn>
              </td>
            </tr>
          </template>
        </v-data-table>
        <v-btn @click="showReport"> View Report with All Orders </v-btn>
        <v-snackbar v-model="snackbar">
          {{ "Order has been returned." }}

          <template v-slot:action="{ attrs }">
            <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">
              Close
            </v-btn>
          </template>
        </v-snackbar>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
const auth_user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "OrderListComponent",
  props: {
    orders: Array,
    opened: Boolean,
  },
  data() {
    return {
      headers: [
        {
          text: "Id",
          align: "start",
          sortable: false,
          value: "id",
        },
        {
          text: "Delivery Date",
          align: "start",
          sortable: false,
          value: "deliveryDate",
        },
        {
          text: "Return Date",
          align: "start",
          sortable: false,
          value: "returnDate",
        },
      ],
      snackbar: false,
    };
  },
  methods: {
    onClickRow(item) {
      console.log(item);
    },
    deleteOrder(id) {
      this.snackbar = true;
      api.orders.delete(id);
    },
    showReport() {
      api.orders.showReport("pdf", auth_user.id);
    },
  },
};
</script>

<style scoped></style>
