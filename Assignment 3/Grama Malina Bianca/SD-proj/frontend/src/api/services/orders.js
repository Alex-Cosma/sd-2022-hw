import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  create(order) {
    return HTTP.post(BASE_URL + "/orders", order, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
