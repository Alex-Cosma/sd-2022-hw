import React, { Component } from "react";
import BookRow from "./bookRow";

class BooksTable extends Component {
  state = {
    // booksHaveFetchedFromBackend: false,
  };

  render() {
    return (
      <React.Fragment>
        <table class="table">
          <thead>
            <tr>
              <th scope="col"></th>
              <th scope="col"></th>
              <th scope="col">ID</th>
              <th scope="col">Author</th>
              <th scope="col">Title</th>
              <th scope="col">Genre</th>
              <th scope="col">Price</th>
              <th scope="col">Stock</th>
              <th scope="col">Published Date</th>
            </tr>
          </thead>
          <tbody>
            {this.props.books.map((book) => (
              <BookRow
                book={book}
                onAuthorChange={this.props.onAuthorChange}
                onTitleChange={this.props.onTitleChange}
                onGenreChange={this.props.onGenreChange}
                onPriceChange={this.props.onPriceChange}
                onStockChange={this.props.onStockChange}
                onPublishedDateChange={this.props.onPublishedDateChange}
                onDelete={this.props.onDelete}
                sellBook={this.props.sellBook}
              ></BookRow>
            ))}
          </tbody>
        </table>

        <button
          className="btn btn-primary btn-sm m-2"
          onClick={() => {
            this.props.saveUpdatedBooksList();
          }}
        >
          Save
        </button>

        <button
          className="btn btn-primary btn-sm m-2"
          onClick={() => {
            this.props.createBook();
          }}
        >
          Create Book
        </button>

      </React.Fragment>
    );
  }
}

export default BooksTable;
