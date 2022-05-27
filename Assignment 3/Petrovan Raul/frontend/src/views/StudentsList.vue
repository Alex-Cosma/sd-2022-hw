<template>
  <v-card>
    <v-card-title>
      Your Students:
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
          @change="searchStudents"
      ></v-text-field>
      <v-btn @click="addStudent">Add Student</v-btn>
    </v-card-title>
    <div class="details">Click on a student to view your flights with them.</div>
    <v-data-table
        :headers="headers"
        :items="students"
        :search="search"
        @click:row="editFlights"
    >

      <template v-slot:no-data>
        You have no students assigned. Click the button above to add students.
      </template>
    </v-data-table>
    <StudentDialog
        :opened="studentDialogVisible"
        :students="unassignedStudents"
        @refresh="refreshList"
        v-on:close="closeDialog"
    ></StudentDialog>
    <FlightDialog
        :opened="flightDialogVisible"
        :student="selectedStudent"
        @refresh="refreshList"
        v-on:close="closeDialog"
    ></FlightDialog>
  </v-card>
</template>

<script>
import api from "../api";
import {auth as store} from "../store/auth.module";
import StudentDialog from "@/components/StudentDialog";
import FlightDialog from "@/components/FlightDialog";

export default {
  name: "StudentsList",
  components: {
    StudentDialog,
    FlightDialog,
  },
  data() {
    return {
      students: [],
      unassignedStudents: [],
      selectedStudent: {},
      selectedStudentFlights: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        {text: "First Name", value: "firstName"},
        {text: "Last Name", value: "lastName"},
      ],
      studentDialogVisible: false,
      flightDialogVisible: false,
    };
  },
  methods: {
    async addStudent() {
      this.studentDialogVisible = true;
      this.unassignedStudents = await api.users.unassignedStudents();
    },
    closeDialog() {
      this.refreshList();
    },
    editFlights(student) {
      this.selectedStudent = student;
      this.flightDialogVisible = true;

    },
    async refreshList() {
      this.studentDialogVisible = false;
      this.flightDialogVisible = false;
      this.students = await api.users.allStudentsForInstructor(store.state.user.id);
    },
    searchStudents() {
      this.refreshList();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped>
.details {
 padding: 1rem;
}
</style>