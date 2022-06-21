<template>
  <div style="display: flex; justify-content: center; align-items: center;">
    <div style="padding-top: 1rem; width: 700px;">
    <v-combobox
        v-model="selectedCurrencies"
        :items="availableCurrencies"
        label="Currencies"
        multiple
        chips
        :clearable="true"
        :deletable-chips="true"
    ></v-combobox>
  </div>
  </div>
  <div id="currency-container"
    class="px-2 py-3"
  >
    <currency-chart
        v-for="(currency, index) in selectedCurrencies"
        :key="`currency-chart-${currency}-${index}`"
        :currency="currency"
        :currencies="currencies[currency]"
        :baseCurrency="baseCurrency"
        :baseCurrencyValue="baseCurrencyValue"
    >
    </currency-chart>
  </div>
</template>

<script>
import CurrencyChart from "./CurrencyChart.vue";
import { io } from "socket.io-client";

const baseUrl = "http://localhost:3070";
const socket = io("ws://localhost:3070");

export default {
  name: "DisplayCurrencies",
  components: { CurrencyChart },
  data() {
    return {
      currencies: [],
      availableCurrencies: [],
      baseCurrency: "EUR",
      selectedCurrencies: ["RON", "USD", "BTC", "XAU", "CAD", "CUC", "DOP", "LTL", "LVL", "TND", "TOP"],
    };
  },
  computed: {
    baseCurrencyValue() {
      return 1;
    },
  },
  mounted() {
    console.log("Mounted")
    fetch(baseUrl + "/rates/availableCurrencies")
        .then((response) => response.json())
        .then(async (data) => {
          console.log(data);
          this.availableCurrencies = data;
          await fetch(baseUrl + "/rates/last/100")
              .then((response) => response.json())
              .then((data) => {
                this.currencies = {};
                this.availableCurrencies.forEach((currency) => {
                  this.currencies[currency] = [];
                });
                data.forEach((latestCurrency) => {
                  this.availableCurrencies.forEach((currency) => {
                    this.currencies[currency].push(latestCurrency[currency]);
                  });
                });
                socket.on("latest-currency", (latest) => {
                  this.availableCurrencies.forEach((currency) => {
                    this.currencies[currency].push(latest[currency]);
                  });
                });
              });
        });
    fetch(baseUrl + "/rates/baseCurrency")
        .then((response) => response.json())
        .then((data) => {
          this.baseCurrency = data;
        });
  },
};
</script>

<style scoped>
#currency-container {
  display: flex;
  overflow: scroll;
}
</style>
