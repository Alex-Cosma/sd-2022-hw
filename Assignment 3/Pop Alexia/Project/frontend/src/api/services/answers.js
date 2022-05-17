import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  create(answer) {
    return HTTP.post(BASE_URL + "/answer", answer, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  editAnswer(id, answer) {
    return HTTP.put(BASE_URL + "/answer/" + id, answer, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
