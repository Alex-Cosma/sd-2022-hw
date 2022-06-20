import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allTrainings() {
    return HTTP.get(BASE_URL + "/trainings", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(training) {
    return HTTP.post(BASE_URL + "/trainings", training, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(id, training) {
    return HTTP.patch(BASE_URL + "/trainings/" + id, training, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/trainings/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  filter(filter) {
    return HTTP.get(BASE_URL + "/trainings/filter/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  report(type, username) {
    return HTTP.get(BASE_URL + "/trainings/export/" + type + "/" + username, {
      responseType: type ? "arraybuffer" : "",
      headers: authHeader(),
    }).then((response) => {
      var fileDownload = require("js-file-download");
      fileDownload(response.data, "report." + type.toLowerCase());
      return response.data;
    });
  },
};
