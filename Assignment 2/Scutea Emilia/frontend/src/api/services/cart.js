import authHeader, { API_PATH, CART, HTTP } from "../http";

export default {
  getCart(id) {
    console.log(API_PATH + CART + "/get/" + id);
    return HTTP.get(API_PATH + CART + "/get/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  createCart(book, user_id) {
    return HTTP.post(API_PATH + CART + "/create/" + user_id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteFromCart(user_id, book_id, book) {
    return HTTP.put(
      API_PATH + CART + "/delete/" + user_id + "/" + book_id,
      book,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
  deleteCart(user_id) {
    return HTTP.delete(API_PATH + CART + "/deletecart/" + user_id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  placeOrder(user_id) {
    return HTTP.delete(API_PATH + CART + "/order/" + user_id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
