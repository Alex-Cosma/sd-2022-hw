<!--<template>-->
<!--  <v-card>-->
<!--    <v-card-title>-->
<!--      Properties-->
<!--      <v-spacer></v-spacer>-->
<!--      <v-text-field-->
<!--          v-model="search"-->
<!--          append-icon="mdi-magnify"-->
<!--          label="Search"-->
<!--          single-line-->
<!--          hide-details-->
<!--      ></v-text-field>-->
<!--    </v-card-title>-->
<!--    <v-data-table-->
<!--        :headers="headers"-->
<!--        :properties="properties"-->
<!--        :search="search"-->
<!--        @click:row="openProperty"-->
<!--    ></v-data-table>-->
<!--    <BookDialog-->
<!--        :opened="dialogVisible"-->
<!--        :property="selectedProperty"-->
<!--        :user="authenticatedUser"-->
<!--        @refresh="refreshList"-->
<!--        @close="dialogVisible = !dialogVisible"-->
<!--    ></BookDialog>-->
<!--  </v-card>-->
<!--</template>-->

<!--<script>-->
<!--import api from "../api";-->
<!--import BookDialog from "../components/BookDialog";-->
<!--//import router from "../router";-->
<!--import { auth as store } from "../store/auth.module";-->

<!--export default {-->
<!--  name: "SearchPropertyList",-->
<!--  components: { BookDialog },-->
<!--  data() {-->
<!--    return {-->
<!--      properties: [],-->
<!--      search: "",-->
<!--      headers: [-->
<!--        {-->
<!--          text: "Name",-->
<!--          align: "start",-->
<!--          sortable: false,-->
<!--          value: "name",-->
<!--        },-->
<!--        { text: "Owner", value: "owner" },-->
<!--        { text: "Address", value: "address" },-->
<!--        { text: "Price", value: "price" },-->
<!--        { text: "Description", value: "description" },-->
<!--        { text: "NumberOfRooms", value: "numberOfRooms" },-->
<!--        { text: "NumberOfBeds", value: "numberOfBeds" },-->
<!--        { text: "NumberOfBathrooms", value: "numberOfBathrooms" },-->
<!--        { text: "Kitchen", value: "kitchen" },-->
<!--      ],-->
<!--      dialogVisible: false,-->
<!--      selectedProperty: {},-->
<!--      authenticatedUser:store.getters.getUser(),                        //TODO-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    openProperty(property) {-->
<!--      //router.push("/property");-->
<!--      this.selectedProperty = property;-->
<!--      this.dialogVisible = true;-->
<!--    },-->
<!--    async refreshList() {-->
<!--      console.log("aaaaaaaaaaaaa");-->
<!--      this.dialogVisible = false;-->
<!--      this.selectedProperty = {};-->
<!--      this.properties = await api.properties.getAllItemz();-->
<!--      console.log("bbbbbbbbbbbbbbb");-->
<!--      console.log(this.properties);-->
<!--    },-->
<!--  },-->
<!--  created() {-->
<!--    this.refreshList();-->
<!--  },-->
<!--};-->
<!--</script>-->

<!--<style scoped></style>-->



<!--<template>-->
<!--  <v-card>-->
<!--    <v-card-title>-->
<!--      Properties-->
<!--      <v-spacer></v-spacer>-->
<!--      <v-text-field-->
<!--          v-model="search"-->
<!--          append-icon="mdi-magnify"-->
<!--          label="Search"-->
<!--          single-line-->
<!--          hide-details-->
<!--      ></v-text-field>-->
<!--    </v-card-title>-->
<!--    <v-data-table-->
<!--        :headers="headers"-->
<!--        :properties="properties"-->
<!--        :search="search"-->
<!--        @click:row="editProperty"-->
<!--    ></v-data-table>-->
<!--    <BookDialog-->
<!--        :opened="dialogVisible"-->
<!--        :property="selectedProperty"-->
<!--        @refresh="refreshList"-->
<!--        @close="dialogVisible = !dialogVisible"-->
<!--    ></BookDialog>-->
<!--  </v-card>-->
<!--</template>-->

<!--<script>-->
<!--import api from "../api";-->
<!--//import PropertyDialog from "../components/PropertyDialog";-->
<!--import BookDialog from "../components/BookDialog";-->

<!--export default {-->
<!--  name: "PropertyList",-->
<!--  components: { BookDialog },-->
<!--  data() {-->
<!--    return {-->
<!--      properties: [],-->
<!--      search: "",-->
<!--      headers: [-->
<!--        {-->
<!--          text: "Name",-->
<!--          align: "start",-->
<!--          sortable: false,-->
<!--          value: "name",-->
<!--        },-->
<!--        { text: "Owner", value: "owner" },-->
<!--        { text: "Address", value: "address" },-->
<!--        { text: "Price", value: "price" },-->
<!--        { text: "Description", value: "description" },-->
<!--        { text: "NumberOfRooms", value: "numberOfRooms" },-->
<!--        { text: "NumberOfBeds", value: "numberOfBeds" },-->
<!--        { text: "NumberOfBathrooms", value: "numberOfBathrooms" },-->
<!--        { text: "Kitchen", value: "kitchen" },-->
<!--      ],-->
<!--      dialogVisible: false,-->
<!--      selectedProperty: {},-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    editProperty(property) {-->
<!--      this.selectedProperty = property;-->
<!--      this.dialogVisible = true;-->
<!--    },-->
<!--    async refreshList() {-->
<!--      console.log("aaaaaaaaaaaaa");-->
<!--      this.dialogVisible = false;-->
<!--      this.selectedProperty = {};-->
<!--      this.properties = await api.properties.getAllItemz();-->
<!--      console.log("bbbbbbbbbbbbbbb");-->
<!--      console.log(this.properties);-->
<!--    },-->
<!--  },-->
<!--  created() {-->
<!--    this.refreshList();-->
<!--  },-->
<!--};-->
<!--</script>-->

<!--<style scoped></style>-->



<template>
  <v-card>
    <v-card-title>
      Choose a property
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
    </v-card-title>
    <v-card-title>
      Arrival date
      <date-pick
          v-model="arrivalDate"
          :format="'YYYY.MM.DD'"
      ></date-pick>
    </v-card-title>
    <v-card-title>
      Leaving date
      <date-pick
          v-model="leavingDate"
          :format="'YYYY.MM.DD'"
      ></date-pick>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="properties"
        :search="search"
        @click:row="bookProperty"
    ></v-data-table>



    <BookDialog
        :opened="dialogVisible"
        :property="selectedProperty"
        :arrivalDate="arrivalDate"
        :leavingDate="leavingDate"
        @refresh="refreshList"
        @close="dialogVisible = !dialogVisible"
    ></BookDialog>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";
import DatePick from 'vue-date-pick';

export default {
  name: "PropertyList",
  components: { BookDialog,DatePick },
  data() {
    return {
      properties: [],
      search: "",
      arrivalDate: "",
      leavingDate: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Owner", value: "owner" },
        { text: "Address", value: "address" },
        { text: "Price", value: "price" },
        { text: "Description", value: "description" },
        { text: "NumberOfRooms", value: "numberOfRooms" },
        { text: "NumberOfBeds", value: "numberOfBeds" },
        { text: "NumberOfBathrooms", value: "numberOfBathrooms" },
        { text: "Kitchen", value: "kitchen" },
        //TODO add roles
      ],
      dialogVisible: false,
      selectedProperty: {},
    };
  },
  methods: {
    bookProperty(property) {
      this.selectedProperty = property;
      this.dialogVisible = true;
    },
    async refreshList() {
      console.log("aaaaaaaaaaaaa");
      this.dialogVisible = false;
      this.selectedProperty = {};
      this.properties = await api.properties.getAllItemz();
      console.log("bbbbbbbbbbbbbbb");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>

