import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  save(review) {
    return HTTP.post(BASE_URL + "/reviews/add", review, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  findForId(id) {
    return HTTP.get(BASE_URL + "/reviews/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
