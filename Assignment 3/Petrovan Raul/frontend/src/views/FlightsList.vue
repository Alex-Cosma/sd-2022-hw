<template>
  <v-card>
    <v-card-title>
      Your Flights:
      <v-spacer></v-spacer>
      <!--      <v-btn @click="addStudent">Add Student</v-btn>-->
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="flights"
    >

      <template v-slot:no-data>
        You have no flights yet.
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import api from "../api";
import {auth as store} from "../store/auth.module";
// import * as SockJS from "sockjs-client";
// import {Stomp} from "@stomp/stompjs";

export default {
  name: "FlightsList",
  data() {
    return {
      flights: [],
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
    async refreshList() {
      this.flights = await api.flights.allFlightsForUser(store.state.user.id);
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
  created() {
    this.refreshList();
  },
  // mounted() {
  //   let connection = new SockJS("http://localhost:8088/stomp");
  //   let stompClient = Stomp.over(connection);
  //   stompClient.connect({}, function (frame) {
  //     // setConnected(true);
  //     console.log('Connected: ' + frame);
  //     stompClient.subscribe('/topic/messages', function (greeting) {
  //       console.log(greeting);
  //     });
  //   });
  // }
};
</script>

<style scoped>

</style>