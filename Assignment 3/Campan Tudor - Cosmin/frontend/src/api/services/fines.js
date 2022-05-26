import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/fines", { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  create(item) {
    return HTTP.post(BASE_URL + "/fines/create", item, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  edit(item) {
    return HTTP.put(BASE_URL + "/fines/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(item) {
    return HTTP.delete(BASE_URL + "/fines/" + item.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
