<template>
  <div class="mx-2 my-2">
    <v-card elevation="5" outlined class="px-12 py-2">
      <v-card-title>
        {{ `${currency} (reported to ${baseCurrency})` }}
        <v-spacer></v-spacer>
        <span :style="{ color: trendColor }">
          ({{ trendValue > 0 ? "+" : ""
          }}{{ ((trendValue * 10000) | 1) / 10000 }})
        </span>
      </v-card-title>
      <LineChartGenerator
          :chart-options="chartOptions"
          :chart-data="chartData"
          :chart-id="chartId"
          :dataset-id-key="datasetIdKey"
          :plugins="plugins"
          :css-classes="cssClasses"
          :styles="styles"
          :width="width"
          :height="height"
      />
      <v-switch
          v-model="view100"
          label="View only 100"
          color="primary"
      ></v-switch>
    </v-card>
  </div>
</template>

<script>
import { Line as LineChartGenerator } from "vue-chartjs";

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  PointElement,
} from "chart.js";

ChartJS.register(
    Title,
    Tooltip,
    Legend,
    LineElement,
    LinearScale,
    CategoryScale,
    PointElement
);

export default {
  name: "CurrencyChart",
  components: {
    LineChartGenerator,
  },
  props: {
    chartId: {
      type: String,
      default: "line-chart",
    },
    datasetIdKey: {
      type: String,
      default: "label",
    },
    width: {
      type: Number,
      default: 450,
    },
    height: {
      type: Number,
      default: 350,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Array,
      default: () => [],
    },
    currency: {
      type: String,
      default: "USD",
    },
    baseCurrency: {
      type: String,
      default: "EUR",
    },
    baseCurrencyValue: {
      type: Number,
      default: 1,
    },
    currencies: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      view100: true,
    };
  },
  computed: {
    last100() {
      if (this.view100 === true) {
        return this.currencies.slice(-100);
      } else {
        return this.currencies;
      }
    },
    trendValue() {
      const lastValues = this.currencies.slice(-2);
      return lastValues[1] - lastValues[0];
    },
    trendColor() {
      if (this.trendValue > 0) {
        return "#246D50";
      } else {
        return "#C32416";
      }
    },
    chartData() {
      return {
        labels: [...new Array(this.last100.length).keys()],
        datasets: [
          {
            label: this.currency,
            borderColor: this.trendColor,
            pointBackgroundColor: this.trendColor,
            data: this.last100,
          },
        ],
      };
    },
    chartOptions() {
      return {
        radius: 3,
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            ticks: {
              callback: (value) => {
                return `${((value * 100) | 1) / 100} ${this.baseCurrency}`;
              },
            },
          },
        },
        legend: {
          display: false,
        },
      };
    },
  },
};
</script>
