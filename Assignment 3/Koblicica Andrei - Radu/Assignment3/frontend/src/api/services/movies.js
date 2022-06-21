import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allMovies() {
    return HTTP.get(BASE_URL + "/movies", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  movieById(id){
    return HTTP.get(BASE_URL + "/movies/id="+id, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  reviewsByMovieId(id){
    return HTTP.get(BASE_URL + "/movies/id="+id+"/reviews", { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  addReview(review){
    return HTTP.post(BASE_URL + "/movies/id="+review.movieId+"/reviews",review, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  filteredMovies(filter) {
    return HTTP.get(BASE_URL + "/movies/filtered/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(movie) {
    return HTTP.post(BASE_URL + "/movies", movie, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  update(movie) {
    return HTTP.put(BASE_URL + "/movies/" + movie.id, movie, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(movie) {
    return HTTP.delete(BASE_URL + "/movies/" + movie.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

};
