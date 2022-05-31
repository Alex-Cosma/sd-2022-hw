import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  downloadReservations() {
    return HTTP.get(BASE_URL + "/reserve/download", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allCourtsForAdmin() {
    return HTTP.get(BASE_URL + "/users/courts-admin", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(court) {
    return HTTP.post(BASE_URL + "/courts", court, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(user) {
    return HTTP.delete(BASE_URL + "/users/" + user.id, {
      headers: authHeader(),
    }).then();
  },
  /*create(user) {
    return HTTP.post(BASE_URL + "/users", user, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(user) {
    return HTTP.patch(BASE_URL + "/users", user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },*/
};
