import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allMovies() {
    return HTTP.get(BASE_URL + "/movies", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  findById(id) {
    return HTTP.get(BASE_URL + "/movies/" + id, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(movie) {
    return HTTP.post(BASE_URL + "/movies/create", movie, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(movie) {
    return HTTP.put(BASE_URL + "/movies/edit", movie, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/movies/delete/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
