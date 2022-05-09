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
  edit(book) {
    return HTTP.put(BASE_URL + "/books/" + book.id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(book) {
    return HTTP.delete(BASE_URL + "/books/" + book.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  filterBooks(filter) {
    return HTTP.get(BASE_URL + "/books/filtered/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sellBook(id, quantity) {
    return HTTP.put(BASE_URL + "/books/sell/" + id, quantity, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  exportReport(type) {
    return HTTP.get(BASE_URL + "/books/export/" + type, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
