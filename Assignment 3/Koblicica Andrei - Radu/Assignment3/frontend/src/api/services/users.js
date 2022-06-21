import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(user) {
    return HTTP.post(BASE_URL + "/users", user, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  update(user) {
    return HTTP.put(BASE_URL + "/users/" + user.id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(user) {
    return HTTP.delete(BASE_URL + "/users/" + user.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  generateReport(){
    return HTTP.get(BASE_URL + "/users/report",{
      headers: authHeader(), responseType: 'blob'
    }).then((response) => {
      return response;
    });
  },
  getWatchlist(userId){
    return HTTP.get(BASE_URL + "/watchlist/"+userId, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  addToWatchlist(userId, movieId){
    return HTTP.post(BASE_URL + "/watchlist/add/"+userId+"/"+movieId,{
      headers: authHeader(),
    }).then((response) => {
      alert(response.data);
    });
  },
  removeFromWatchlist(userId, movieId){
    return HTTP.post(BASE_URL + "/watchlist/remove/"+userId+"/"+movieId,{
      headers: authHeader(),
    }).then((response) => {
      alert(response.data);
    });
  }

};
