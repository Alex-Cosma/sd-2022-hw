import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  findAll() {
    return HTTP.get(BASE_URL + "/review", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  filterAll(filter) {
    return HTTP.get(BASE_URL + "/review/filter/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  createReview(review) {
    return HTTP.post(BASE_URL + "/review", review, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  editReview(id, review) {
    return HTTP.put(BASE_URL + "/review/" + id, review, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteReview(id) {
    return HTTP.delete(BASE_URL + "/review/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
