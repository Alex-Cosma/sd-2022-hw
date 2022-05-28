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
        <vue-google-autocomplete
          ref="address"
          id="map"
          classname="form-control"
          placeholder="Please type your address"
          v-on:placechanged="getAddressData"
          v-show="setDisabled()"
        >
        </vue-google-autocomplete>
        <v-form>
          <v-text-field
            v-model="address"
            label="Address"
            v-show="setDisabled()"
          />
        </v-form>
        <v-card-actions class="justify-center">
          <v-btn
            @click="showItems"
            v-show="confirmButtonDisabled"
            :disabled="confirmOrder()"
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
          <v-snackbar v-model="snackbar">
            {{ text }}

            <template v-slot:action="{ attrs }">
              <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">
                Close
              </v-btn>
            </template>
          </v-snackbar>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
const auth_user = JSON.parse(localStorage.getItem("user"));
import VueGoogleAutocomplete from "vue-google-autocomplete";

export default {
  name: "CartComponent",
  components: {
    VueGoogleAutocomplete,
  },
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
      googleAddress: "",
      snackbar: false,
      text: `Order has been placed`,
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
  mounted() {
    // To demonstrate functionality of exposed component functions
    // Here we make focus on the user input
    this.$refs.address.focus();
  },
  methods: {
    getAddressData: function (addressData) {
      this.googleAddress = addressData;
      this.address =
        addressData.route +
        ", " +
        addressData.locality +
        ", " +
        addressData.administrative_area_level_1 +
        ", " +
        addressData.country;
    },
    confirmOrder() {
      return this.selectedItems.length <= 0;
    },
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
      this.snackbar = true;
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
