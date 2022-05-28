import authHeader, { BASE_URL, HTTP } from "../http";

export default {

  reportCSV() {
    return HTTP.get(BASE_URL + "/user/reportCSV", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  reportPDF() {
    return HTTP.get(BASE_URL + "/user/reportPDF", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  allUsers() {
    return HTTP.get(BASE_URL + "/user", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(user) {
    return HTTP.post(BASE_URL + "/user", user, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(user) {
    return HTTP.put(BASE_URL + "/user/" + user.id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(user) {
    return HTTP.delete(BASE_URL + "/user/" + user.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
