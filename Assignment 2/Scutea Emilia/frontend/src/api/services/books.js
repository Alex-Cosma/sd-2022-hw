import authHeader, { API_PATH, BOOKS, BOOKSTORE, HTTP } from "../http";

export default {
  getAllBooks() {
    return HTTP.get(API_PATH + BOOKS, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(book) {
    return HTTP.post(API_PATH + BOOKS + "/create", book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(book) {
    return HTTP.put(API_PATH + BOOKS + "/edit/" + book.id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(book) {
    return HTTP.delete(API_PATH + BOOKS + "/delete/" + book.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getAllGenreTypes() {
    return HTTP.get(API_PATH + BOOKSTORE + "/genretypes", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getBooksByGenre(genre) {
    return HTTP.get(API_PATH + BOOKSTORE + "/getByGenre/" + genre, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allBooks() {
    return HTTP.get(API_PATH + BOOKSTORE, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  searchBooks() {
    return HTTP.get(API_PATH + BOOKSTORE + "/search/{str", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sellBook(book) {
    return HTTP.put(API_PATH + BOOKSTORE + "/sell/" + book.id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
