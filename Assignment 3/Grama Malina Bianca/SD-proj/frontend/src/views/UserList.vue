<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser" :disabled="isUnauthorized()">Add User</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser"
    >
      <template v-slot:item="row">
        <tr>
          <td>{{ row.item.name }}</td>
          <td>{{ row.item.email }}</td>
          <td>{{ row.item.roles[0] }}</td>
          <td>
            <v-btn
              color="green"
              :disabled="isUnauthorized()"
              @click="editUser(row.item)"
            >
              Edit
            </v-btn>
          </td>
          <td>
            <v-btn
              color="red"
              :disabled="isUnauthorized()"
              @click="deleteUser(row.item)"
            >
              Delete
            </v-btn>
          </td>
        </tr>
      </template>
    </v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedUser"
      :edit="edit"
      @refresh="refreshList"
    ></UserDialog>
    <UserDialogEdit
      :opened="editDialogVisible"
      :user="selectedUser"
      :edit="edit"
      @refresh="refreshList"
    ></UserDialogEdit>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import UserDialogEdit from "../components/UserDialogEdit";

const auth_user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "UserList",
  components: { UserDialog, UserDialogEdit },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", align: "start", sortable: false, value: "email" },
        { text: "Roles", align: "start", sortable: false, value: "roles" },
        { text: "", align: "start", sortable: false, value: "" },
      ],
      edit: false,
      dialogVisible: false,
      editDialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    editUser(user) {
      this.selectedUser = user;
      this.dialogVisible = false;
      this.editDialogVisible = true;
      this.edit = true;
    },
    addUser() {
      this.dialogVisible = true;
      this.editDialogVisible = false;
      this.edit = false;
    },
    deleteUser(user) {
      api.users
        .delete({
          id: user.id,
        })
        .then(() => this.refreshList());
    },
    isUnauthorized() {
      return auth_user.roles[0] !== "ADMIN";
    },
    async refreshList() {
      this.edit = false;
      this.dialogVisible = false;
      this.editDialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
