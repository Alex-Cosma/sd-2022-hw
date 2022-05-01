import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/books", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(book) {
    return HTTP.post(BASE_URL + "/books", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(id, book) {
    return HTTP.patch(BASE_URL + "/books/"+id, book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/books/"+id, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  sell(id) {
    return HTTP.post(BASE_URL + "/books/"+id, { headers: authHeader() }).then(
      (response) => {
        return response.data;

      }
    );
  },
  filter(filter) {
    return HTTP.get(BASE_URL + "/books/filter/"+filter, { headers: authHeader()}).then(
      (response) => {
        return response.data;
      }
    );
  },
  report(type) {
    return HTTP.get(BASE_URL + "/books/export/"+type, { headers: authHeader()}).then(
      (response) => {
        return response.data;
      }
    );
  },
};
