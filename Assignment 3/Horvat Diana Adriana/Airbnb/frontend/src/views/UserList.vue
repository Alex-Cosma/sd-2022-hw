<template>
  <v-card>
    <v-card-title>
      Accommodations
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser">Add Accommodation</v-btn>
      <v-btn @click="changeToGuest">Change to guest</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search">
        <template v-slot:item="row">
          <tr>
            <td @click="editItem(row.item)">
              <img :src=row.item.imageURL.picture_url height="100px"
                   width="150px">
            </td>
            <td>{{row.item.name}}</td>
            <td>{{row.item.bedrooms}} {{row.item.bedrooms == 1 ? "bedroom" : "bedrooms"}}, {{row.item.beds}} {{row.item.beds == 1 ? "bed" : "beds"}}, {{Math.ceil(row.item.bathrooms)}}
              {{Math.ceil(row.item.bathrooms) == 1 ? "bathroom" : "bathrooms"}}</td>
            <td>
              {{row.item.address.street}}, {{row.item.address.city}}
            </td>
            <td>{{row.item.price}}$/night</td>
            <td>
              <v-btn color="red" @click="deleteItem(row.item)">
                Delete
              </v-btn>
            </td>
            <td>
              <v-btn @click="downloadPdf(row.item)">Download Details</v-btn>
            </td>
            <td>
              <v-btn @click="sendEmail(row.item)">Send details on mail</v-btn>
            </td>
          </tr>
        </template>
    </v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user ="selectedUser"
      @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import router from "@/router";
const auth_user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "UserList",
  components: { UserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "", align: "start", sortable: false, value: "email" },
        { text: "", align: "start", sortable: false, value: "roles" },
        { text: "", align: "start", sortable: false, value: "" },
        { text: "", align: "start", sortable: false, value: "" },
        { text: "", align: "start", sortable: false, value: "" },
        { text: "", align: "start", sortable: false, value: "" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    sendEmail(item){
      api.users.sendEmail(item);
    },
    downloadPdf(item){
      api.users.downloadPdf(item);
    },
    changeToGuest(){
      router.push('/accommodations');
    },
    editItem(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    addUser() {
      this.dialogVisible = true;
    },
    deleteItem(user) {
      api.items
        .delete({
          id: user.id,
        })
        .then(() => this.refreshList());
    },
    isUnauthorized(){
      if(auth_user.roles[0] === "ADMIN"){
        return false;
      }else{
        return true;
      }
    },
    generatePDF(){
      api.users
        .generateReport({
          type: "pdf",
        })
        .then(() => this.refreshList());
    },
    generateCSV(){
      api.users
          .generateReport({
            type: "csv",
          })
          .then(() => this.refreshList());
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsersAccommodations(auth_user);
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
