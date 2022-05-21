<template>
  <div>
    <v-dialog
        transition="dialog-bottom-transition"
        max-width="1000"
        :value="opened"
        persistent
        content-class="custom-content"
    >
      <template>
        <v-card>
          <v-toolbar color="primary" dark>
            My flights with {{ student.firstName }} {{ student.lastName }}
          </v-toolbar>
          <v-data-table
              :headers="headers"
              :items="flights">
          </v-data-table>
          <v-card-actions>
            <v-btn @click="close">Cancel</v-btn>
            <v-btn @click="addFlight">Add a flight</v-btn>
          </v-card-actions>
        </v-card>
      </template>
    </v-dialog>
    <AddFlightDialog
        :opened="addFlightDialogOpened"
        :student="student"
        :flight="flight"
        @close="refreshFlightList"

    ></AddFlightDialog>
  </div>
</template>

<script>
import {auth as store} from "@/store/auth.module";
import api from "@/api";
import AddFlightDialog from "@/components/AddFlightDialog";

export default {
  name: "FlightDialog",
  components: {
    AddFlightDialog,
  },
  props: {
    opened: Boolean,
    student: Object,

  },
  data() {
    return {
      flights: [],
      addFlightDialogOpened: false,
      flight: {},
      headers: [
        {text: 'Departure', value: 'departure', align: "start"},
        {text: 'Arrival', value: 'arrival'},
        {text: 'Departure Time', value: 'departureTime'},
        {text: 'Arrival Time', value: 'arrivalTime'},
        {text: 'Aircraft', value: 'airplane.name'},
        {text: 'Student Name', value: 'student'},
        {text: 'Instructor Name', value: 'instructor'},
      ],
    }
  },
  methods: {
    close() {
      this.$emit('close')
    },
    addFlight() {
      console.log("add flight");
      this.addFlightDialogOpened = true;
    },
    async refreshFlightList() {
      this.addFlightDialogOpened = false;
      this.flights = await api.flights.flightsForInstructorAndStudent(store.state.user.id, this.student.id);
      this.flights.forEach(flight => {
        flight.departure = flight.departureAirport.name + " (" + flight.departureAirport.icao + ")";
        flight.arrival = flight.arrivalAirport.name + " (" + flight.arrivalAirport.icao + ")";
        flight.departureTime = new Date(flight.departureTime).toLocaleString() ;
        flight.arrivalTime = new Date(flight.arrivalTime).toLocaleString() ;
        flight.student = (flight.student.firstName? flight.student.firstName : "") + " " + (flight.student.lastName? flight.student.lastName : "");
        flight.instructor = (flight.instructor.firstName? flight.instructor.firstName : "") + " " + (flight.instructor.lastName? flight.instructor.lastName : "");
      });
    },
  },
  watch: {
    opened(opened) {
      if (opened) {
        this.refreshFlightList();
      }
    },
  },
}
</script>

<style scoped>
.v-dialog__content {
  align-items: center;
  justify-content: flex-start;
}
</style>