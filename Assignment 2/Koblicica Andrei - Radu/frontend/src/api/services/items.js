import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/books", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  filteredItems(filter) {
    return HTTP.get(BASE_URL + "/books/filtered/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(item) {
    return HTTP.post(BASE_URL + "/books", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  update(item) {
    return HTTP.put(BASE_URL + "/books/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(item) {
    return HTTP.delete(BASE_URL + "/books/" + item.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sell(item) {
    return HTTP.patch(BASE_URL + "/books/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
