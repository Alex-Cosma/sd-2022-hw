<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
<!--        <Bar :chart-data="chartData" :chart-options="chartOptions" />-->
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create stock" : "Edit stock" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="stock.name" label="Name" />
          <v-text-field v-model="stock.symbol" label="Symbol" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="persist2" v-if="!isNew"> Delete </v-btn>
        </v-card-actions>
        <v-form v-if="!isNew">
            <v-text-field v-model="quantity" label="Quantity" />
        </v-form>
        <v-btn @click="persist3" v-if="!isNew"> Buy </v-btn>
        <v-btn @click="persist4" v-if="!isNew"> Sell </v-btn>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
// import {Bar} from "vue-chartjs";
// import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
// ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)
const user = JSON.parse(localStorage.getItem("user"));
export default {
  name: "StockDialog",

  props: {
    stock: Object,
    opened: Boolean,
  },
  data(){
    return{
      quantity: 0,
    }
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.stocks
          .create({
            name: this.stock.name,
            symbol: this.stock.symbol,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.stocks
          .edit({
            id: this.item.id,
            name: this.stock.name,
            symbol: this.stock.symbol,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    persist2() {
      if (!this.isNew) {
        api.stocks
          .delete({
            id: this.stock.id,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    persist3() {
      if(!this.isNew){
        api.users
            .buy(
                user,
                this.stock,
                this.quantity,
            )
      }
    },
    persist4() {
      if(!this.isNew){
        api.users
            .sell(
                user,
                this.stock,
                this.quantity,
            )
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.stock.id;
    },
  },
};
</script>

<style scoped></style>
