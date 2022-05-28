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
    return HTTP.post(BASE_URL + "/trainings", training, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(id, training) {
    return HTTP.patch(BASE_URL + "/trainings/"+id, training, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/trainings/"+id, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  filter(filter) {
    return HTTP.get(BASE_URL + "/trainings/filter/"+filter, { headers: authHeader()}).then(
      (response) => {
        return response.data;
      }
    );
  },
};
