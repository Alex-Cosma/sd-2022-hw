import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allCategories() {
    return HTTP.get(BASE_URL + "/category", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  categoryById(id) {
    return HTTP.get(BASE_URL + "/category/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(category) {
    return HTTP.post(BASE_URL + "/category", category, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(category) {
    return HTTP.put(BASE_URL + "/category/" + category.id, category, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(category) {
    return HTTP.delete(BASE_URL + "/category/" + category.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
