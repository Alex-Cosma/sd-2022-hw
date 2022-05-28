import authHeader, { API_PATH, REVIEWS, HTTP } from "../http";

export default {
  getReviews(id) {
    return HTTP.get(API_PATH + REVIEWS + "/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getAllRatings() {
    return HTTP.get(API_PATH + REVIEWS + "/ratings", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  addReview(book, user_id) {
    return HTTP.post(
      API_PATH + REVIEWS + "/addreviews/" + book.id + "/" + user_id,
      book,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      console.log(API_PATH + REVIEWS + "/addreviews/" + book.id);
      return response.data;
    });
  },
};
