import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allGamesFromWishlist(user) {
    return HTTP.get(
      BASE_URL + "/wishlist/" + user.id, { headers: authHeader() }).then(
          (response) => {
      return response.data;
    });
  },
  deleteGame(user, game) {
    return HTTP.delete(BASE_URL + "/wishlist/" + user.id + "/" + game.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
/*  create(wishlist) {
    return HTTP.post(BASE_URL + "/wishlist/"+user.id, user,game, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },*/
  /*create(item) {
      return HTTP.post(BASE_URL + "/items", item, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
      );
    },
    edit(item) {
      return HTTP.patch(BASE_URL + "/items/" + item.id, item, {
        headers: authHeader(),
      }).then((response) => {
        return response.data;
      });
    },
    deleteItm(item) {
      return HTTP.delete(BASE_URL + "/items/" + item.id, {
        headers: authHeader(),
      }).then((response) => {
        return response.data;
      });
    },
    generatePDF() {
      return HTTP.get(BASE_URL + "/items/export/PDF", { headers: authHeader() });
    },*/
};
