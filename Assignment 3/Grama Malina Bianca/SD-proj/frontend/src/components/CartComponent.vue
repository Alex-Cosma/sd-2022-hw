<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    persistent
    :value="opened"
  >
    <template>
      <v-card>
        <v-data-table
          :headers="headers"
          :items="selectedItems"
          class="elevation-1"
          hide-default-footer
          @click:row="onClickRow"
        >
        </v-data-table>
        <v-form>
          <v-text-field
            v-model="address"
            label="Address"
            v-show="setDisabled()"
          />
        </v-form>
        <v-card-actions class="justify-center">
          <v-btn @click="showItems" v-show="confirmButtonDisabled"
            >Confirm Order</v-btn
          >
          <v-btn @click="placeOrder()" v-show="buttonDisabled"
            >Place Order</v-btn
          >
          <v-btn
            icon
            v-show="deleteButtonDisabled"
            color="red"
            @click="removeItem"
          >
            <v-icon>delete</v-icon>
          </v-btn>
          <v-btn icon @click="close">
            <v-icon>close</v-icon>
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
const auth_user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "CartComponent",
  props: {
    selectedItems: Array,
    opened: Boolean,
  },
  data() {
    return {
      orders: [],
      disabled: false,
      buttonDisabled: false,
      confirmButtonDisabled: true,
      deleteButtonDisabled: true,
      address: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        {
          text: "Author",
          align: "start",
          sortable: false,
          value: "author",
        },
        {
          text: "Genre",
          align: "start",
          sortable: false,
          value: "genre",
        },
      ],
    };
  },
  methods: {
    showItems() {
      this.disabled = true;
      this.buttonDisabled = true;
      this.confirmButtonDisabled = false;
      this.deleteButtonDisabled = false;
      this.$emit("show-items", this.selectedItems);
    },
    setDisabled() {
      return this.disabled;
    },
    onClickRow(item) {
      console.log(item);
    },
    removeItem() {
      this.selectedItems.splice(0, 1);
    },
    close() {
      this.opened = false;
      this.disabled = false;
      this.buttonDisabled = false;
      this.confirmButtonDisabled = true;
      this.deleteButtonDisabled = true;
    },
    placeOrder() {
      api.orders.create({
        address: this.address,
        userId: auth_user.id,
        bookIds: this.selectedItems.map((item) => item.id),
      });
    },
  },
};
</script>

<style scoped></style>
