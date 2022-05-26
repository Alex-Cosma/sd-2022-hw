import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  save(watchlist) {
    return HTTP.post(BASE_URL + "/watchlist/add", watchlist, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  isInWatchlist(userId, movieId) {
    return HTTP.get(BASE_URL + "/watchlist/" + userId + "/" + movieId, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  findByUserId(userId) {
    return HTTP.get(BASE_URL + "/watchlist/" + userId, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  remove(watchlist) {
    return HTTP.post(BASE_URL + "/watchlist/remove", watchlist, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
