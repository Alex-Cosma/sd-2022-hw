import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  create(answer) {
    return HTTP.post(BASE_URL + "/answer", answer, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(id, answer) {
    return HTTP.put(BASE_URL + "/answer/" + id, answer, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  delete(id) {
    return HTTP.delete(BASE_URL + "/answer/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
