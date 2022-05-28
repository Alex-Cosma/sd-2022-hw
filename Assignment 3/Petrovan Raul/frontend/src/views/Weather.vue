<template>
  <v-card>
    <v-card-title>
      Check Aviation Weather
      <v-spacer></v-spacer>

      <!--      <v-btn @click="addStudent">Add Student</v-btn>-->
    </v-card-title>

    <v-tabs grow>
      <v-tab>
        METAR
      </v-tab>
      <v-tab-item>
        <v-card flat>
          <v-card-text> Here you can view current weather reports</v-card-text>
          <v-select
              :items="airports"
              :item-text="'name'"
              label="Choose Airport"
              solo
              v-model="airportMetar"
              return-object
              @change="getMetar"
              class="select"
          >
          </v-select>
          <div class="results" v-if="metar">
            Raw METAR: {{metar.raw_text}}
          </div>
        </v-card>
      </v-tab-item>

      <v-tab>
        TAF
      </v-tab>
      <v-tab-item>
        <v-card flat>
          <v-card-text> Here you can view weather forecasts</v-card-text>
          <v-select
              :items="airports"
              :item-text="'name'"
              label="Choose Airport"
              solo
              v-model="airportTaf"
              return-object
              @change="getTaf"
              class="select"
          >
          </v-select>
          <div class="results" v-if="taf">
            Raw TAF: {{ taf.raw_text }}
          </div>
        </v-card>
      </v-tab-item>
    </v-tabs>


  </v-card>
</template>

<script>
import api from "@/api";
// import {auth as store} from "@/store/auth.module";

export default {
  name: "Weather",
  data() {
    return {
      airportMetar: {},
      airportTaf: {},
      airports: [],
      metar: undefined,
      taf: undefined,
    }
  },
  methods: {
    async getMetar() {
      this.metar = await api.weather.getMetarDecodedFor(this.airportMetar.icao);
      this.metar = this.metar.data[0];
    },
    async getTaf() {
      this.taf = await api.weather.getTafDecodedFor(this.airportTaf.icao);
      this.taf = this.taf.data[0];
    },
  },
  async created() {
    this.airports = await api.flights.allAirports();
  },
}
</script>

<style scoped>
.table-weather {
  width: 800px;
  align-self: center;
}
.select {
  width: 300px;
  margin: 1rem;
}
.results {
  width: 800px;
  align-self: center;
  padding: 1rem;
}
</style>