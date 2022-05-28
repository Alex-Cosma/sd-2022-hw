<template>
  <v-card>
    <v-alert v-if="showAlert" type="error" value="errors">{{ errors }}</v-alert>
    <v-card-title>
      Comments
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <!--      <v-btn @click="addComment">Add Comment</v-btn>-->
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="comments"
      :search="search"
      @click:row="editComment"
    ></v-data-table>
    <CommentDialog
      :opened="dialogVisible"
      :comment="selectedComment"
      @refresh="refreshList"
    ></CommentDialog>
  </v-card>
</template>

<script>
import api from "../api";
import CommentDialog from "../components/CommentDialog";

export default {
  name: "Comments",
  props: {
    comment: Object,
  },
  components: { CommentDialog },
  data() {
    return {
      showAlert: false,
      errors: [],
      comments: [],
      search: "",
      headers: [
        {
          text: "Content",
          align: "start",
          sortable: false,
          value: "content",
        },
        { text: "user_id", value: "userId" },
        { text: "track_id", value: "trackId" },
      ],
      dialogVisible: false,
      selectedComment: {},
    };
  },
  methods: {
    // addComment() {
    //   this.dialogVisible = true;
    // },
    editComment(comment) {
      this.selectedComment = comment;
      if (
        this.selectedComment.userId !== this.$store.getters["auth/getUserID"]
      ) {
        this.showAlert = true;
        this.errors =
          "You can't edit or delete the comment because you did not add it!☺";
      } else {
        this.showAlert = false;
        this.dialogVisible = true;
      }
    },
    async refreshList() {
      this.showAlert = false;
      this.comments = await api.comments.allComments();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>
<style scoped></style>
