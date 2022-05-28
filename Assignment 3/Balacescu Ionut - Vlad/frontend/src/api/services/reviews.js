import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  addReview(review) {
    return HTTP.post(BASE_URL + "/games/" + review.game.id, review, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getReviews(id) {
    return HTTP.get(BASE_URL + "/games/" + id + "/review", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
