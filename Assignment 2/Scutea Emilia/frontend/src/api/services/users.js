import authHeader, { API_PATH, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(API_PATH + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(user) {
    return HTTP.post(API_PATH + "/users", user, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(user) {
    return HTTP.put(API_PATH + "/users/edit/" + user.id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(user) {
    return HTTP.delete(API_PATH + "/users/delete/" + user.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
