import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allGames() {
    return HTTP.get(BASE_URL + "/games", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(wishlist) {
    return HTTP.post(BASE_URL + "/games", wishlist, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  gameByID(id) {
    return HTTP.get(BASE_URL + "/games/" + id, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  addReview(review) {
    return HTTP.post(BASE_URL + "/games/" + review.game.id, review, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getReviews(review) {
    return HTTP.get(BASE_URL + "/games/" + review.game.id + "/review", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
};
