import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allComments() {
    return HTTP.get(BASE_URL + "/comments", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(comment) {
    return HTTP.post(BASE_URL + "/comments", comment, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(comment) {
    return HTTP.put(BASE_URL + "/comments/" + comment.id, comment, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(comment) {
    return HTTP.delete(BASE_URL + "/comments/" + comment.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
