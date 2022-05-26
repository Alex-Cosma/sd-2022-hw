<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
      persistent
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          Add a new flight with {{ student.firstName }} {{ student.lastName }}
        </v-toolbar>
        <v-form>
          <v-select
              :items="planes"
              :item-text="'name'"
              label="Airplane Flown"
              solo
              v-model="flight.airplane"
              return-object
          ></v-select>
          <v-select
              :items="airports"
              :item-text="'name'"
              label="Departure Airport"
              solo
              v-model="flight.departureAirport"
              return-object
          ></v-select>
          <v-select
              :items="airports"
              :item-text="'name'"
              label="Arrival Airport"
              solo
              v-model="flight.arrivalAirport"
              return-object
          ></v-select>
          <div class="horizontal">
          <v-date-picker
              v-model="flight.departureDate"
              label="Date of departure"
              solo>
          </v-date-picker>
          <v-date-picker
              v-model="flight.arrivalDate"
              label="Date of arrival"
              solo>
          </v-date-picker>
          </div>
          <v-time-picker
              format="ampm"
              landscape
              v-model="flight.departureTime"
              label="Time of departure"
          ></v-time-picker>
          <v-time-picker
              format="ampm"
              landscape
              v-model="flight.arrivalTime"
              label="Time of arrival"
          ></v-time-picker>

        </v-form>
        <v-alert
            :value="hasErrors"
            type="error"
            dismissible
            transition="scale-transition"
        >
          Please fill in all fields
        </v-alert>
        <v-alert
            :value="hasDateErrors"
            type="error"
            dismissible
            transition="scale-transition"
        >
          The arrival date must be after the departure date
        </v-alert>
        <v-alert
            :value="hasTimeErrors"
            type="error"
            dismissible
            transition="scale-transition"
        >
          The arrival time must be after the departure time
        </v-alert>
        <v-card-actions>
          <v-btn @click="persist">
            Save Flight
          </v-btn>
          <v-btn @click="close">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";
import {required} from "vuelidate/lib/validators";
import {auth as store} from "@/store/auth.module";

export default {
  name: "AddFlightDialog",
  props: {
    opened: Boolean,
    student: Object,
    flight: Object,
  },
  validations: {
  flight: {
    airplane: { required },
    departureAirport: { required },
    arrivalAirport: { required },
    departureDate: { required },
    arrivalDate: { required,
      minValue(value) {
        return new Date(value) >= new Date(this.flight.departureDate);
      }
    },
    departureTime: { required },
    arrivalTime: { required,
      minValue(value) {
        return new Date(this.flight.departureDate + " " + value) >= new Date(this.flight.arrivalDate + ' ' + this.flight.departureTime);
      }
    }
  },
},
  data() {
    return {
      airports: [],
      planes: [],
      hasErrors: false,
      hasDateErrors: false,
      hasTimeErrors: false,
    };
  },
  methods: {
    close() {
      this.$emit('close')
    },
    persist() {
      this.$v.flight.$touch();
      this.hasErrors = false;
      this.hasDateErrors = false;
      this.hasTimeErrors = false;
      if (this.$v.flight.$error) {
        this.hasErrors = true;
      }
      if(!this.$v.flight.arrivalDate.minValue) {
        console.log(this.$v.flight.arrivalDate.minValue);
        this.hasDateErrors = true;
      }
      if(!this.$v.flight.arrivalTime.minValue) {
        console.log(this.$v.flight.arrivalTime.minValue);
        this.hasTimeErrors = true;
      }
      if(this.hasErrors || this.hasDateErrors || this.hasTimeErrors) {
        return;
      }
      console.log(this.flight);
      const flightDTO = {
        student: {id: this.student.id},
        instructor: {id: store.state.user.id},
        airplane: this.flight.airplane,
        departureAirport: this.flight.departureAirport,
        arrivalAirport: this.flight.arrivalAirport,
        departureTime: Date.parse(this.flight.departureDate + " " + this.flight.departureTime),
        arrivalTime: Date.parse(this.flight.arrivalDate + " " + this.flight.arrivalTime),
      };
      console.log(flightDTO);
      api.flights
          .create(flightDTO)
          .then(() => this.$emit("close"));

    },
    async refreshDropdownLists() {
      this.airports = await api.flights.allAirports();
      this.planes = await api.flights.allAirplanes();
    },
  },
  watch: {
    opened(opened) {
      if (opened) {
        this.refreshDropdownLists();
      }
    },
  },
}
</script>

<style scoped>
.v-dialog__content {
  align-items: center;
  justify-content: flex-end;
}
.horizontal {
  display: flex;
  flex-direction: row;
}
</style>