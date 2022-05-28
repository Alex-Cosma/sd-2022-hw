import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  mail(user) {
    return HTTP.get(BASE_URL + "/users/mail/"+user.id, { headers: authHeader() }).then(
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
  edit(user) {
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
  buy(user,stock,quantity){
    return HTTP.put(BASE_URL + "/users/buy/" + stock.id + "/" + quantity, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sell(user,stock,quantity){
    return HTTP.put(BASE_URL + "/users/sell/" + stock.id + "/" + quantity, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
