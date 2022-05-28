import authHeader, { API_PATH, REVIEWS, HTTP } from "../http";

export default {
  getReviewsForUser(id) {
    console.log(API_PATH + REVIEWS + "/user" + "/review/" + id);
    return HTTP.get(API_PATH + REVIEWS + "/user" + "/review/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
