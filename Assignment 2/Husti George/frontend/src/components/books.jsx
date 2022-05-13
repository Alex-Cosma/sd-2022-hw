import React, { Component } from "react";
import BooksFilters from "./booksFilters";
import BooksTable from "./booksTable";

class Books extends Component {
  state = {
    booksFetchedFromBackend: false,
    books: [],
  };

  fetchAll() {
    if (this.state.booksFetchedFromBackend === false) {
      fetch("http://localhost:8080/bookStore/regularUser/getAllBooks").then(
        (response) => {
          response.json().then((value) => {
            this.setState({ books: value });
          });
        }
      );

      this.setState({ booksFetchedFromBackend: true });
    }
  }

  fetchByFilters = () => {};

  handleAuthorChange = (book, newTextField) => {
    this.handleTextFieldChange(book, newTextField, this.updateAuthor);
  };

  handleTitleChange = (book, newTextField) => {
    this.handleTextFieldChange(book, newTextField, this.updateTitle);
  };

  handleGenreChange = (book, newTextField) => {
    this.handleTextFieldChange(book, newTextField, this.updateGenre);
  };

  handlePriceChange = (book, newTextField) => {
    this.handleTextFieldChange(book, newTextField, this.updatePrice);
  };

  handleStockChange = (book, newTextField) => {
    this.handleTextFieldChange(book, newTextField, this.updateStock);
  };

  handlePublishedDateChange = (book, newTextField) => {
    this.handleTextFieldChange(book, newTextField, this.updatePublishedDate);
  };

  updateAuthor = (books, index, newTextField) => {
    books[index].author = newTextField;
    return books;
  };

  updateTitle = (books, index, newTextField) => {
    books[index].title = newTextField;
    return books;
  };

  updateGenre = (books, index, newTextField) => {
    books[index].genre = newTextField;
    return books;
  };

  updatePrice = (books, index, newTextField) => {
    books[index].price = newTextField;
    return books;
  };

  updateStock = (books, index, newTextField) => {
    books[index].stock = newTextField;
    return books;
  };

  updatePublishedDate = (books, index, newTextField) => {
    books[index].publishedDate = newTextField;
    return books;
  };

  handleTextFieldChange = (book, newTextField, booksAfterFieldChange) => {
    const books = [...this.state.books];
    const index = books.indexOf(book);
    books[index] = { ...book };
    const updatedBooksList = booksAfterFieldChange(books, index, newTextField);
    this.setState({ books: updatedBooksList });
  };

  handleDeleteBook = (bookToDelete) => {
    const updatedBooksList = this.state.books.filter(
      (book) => book !== bookToDelete
    );
    this.setState({ books: updatedBooksList });

    const requestOptions = {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    };
    fetch(
      "http://localhost:8080/bookStore/administrator/deleteBook?" +
        "bookId=" +
        bookToDelete.id,
      requestOptions
    );
  };

  saveUpdatedBooksList = () => {
    this.removeEmptyBooks();

    this.state.books.forEach((book) => {
      let requestOptions = {
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          author: book.author,
          title: book.title,
          genre: book.genre,
          price: book.price,
          stock: book.stock,
          publishedDate: book.publishedDate,
        }),
      };

      let url = "http://localhost:8080/bookStore/administrator/";

      if (book.id === undefined) {
        url += "createBook";
        requestOptions.method = "POST";
      } else {
        url += "updateBook?" + "bookId=" + book.id;
        requestOptions.method = "PUT";
      }

      fetch(url, requestOptions)
        .then((response) => response.json())
        .then((value) => {
          const books = this.state.books;
          books[books.length - 1].id = value.id;
          this.setState({ books });
        });
    });
  };

  removeEmptyBooks() {
    const books = this.state.books.filter(
      (books) => Object.keys(books).length !== 0
    );
    this.setState({ books });
  }

  createBook = () => {
    const books = [...this.state.books];
    books.push({});
    this.setState({ books });
  };

  sellBook = (userId, quantity) => {
    const requestOptions = {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
    };
    fetch(
      "http://localhost:8080/bookStore/regularUser/sellBook?" +
        "bookId=" +
        userId +
        "&quantity=" +
        quantity,
      requestOptions
    );
  };

  render() {
    this.fetchAll();
    return (
      <React.Fragment>
        <BooksFilters fetchByFilters={this.fetchByFilters} />
        <BooksTable
          books={this.state.books}
          onAuthorChange={this.handleAuthorChange}
          onTitleChange={this.handleTitleChange}
          onGenreChange={this.handleGenreChange}
          onPriceChange={this.handlePriceChange}
          onStockChange={this.handleStockChange}
          onPublishedDateChange={this.handlePublishedDateChange}
          onDelete={this.handleDeleteBook}
          saveUpdatedBooksList={this.saveUpdatedBooksList}
          createBook={this.createBook}
          sellBook={this.sellBook}
        />
      </React.Fragment>
    );
  }
}

export default Books;
