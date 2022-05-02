import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/user", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(user) {
    return HTTP.patch(BASE_URL + "/user/" + user.id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteUser(user) {
    return HTTP.delete(BASE_URL + "/user/" + user.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

};
