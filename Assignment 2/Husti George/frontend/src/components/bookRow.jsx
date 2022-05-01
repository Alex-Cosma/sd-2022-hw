import React, { Component } from "react";
import TextField from "@material-ui/core/TextField";
import SellBookModal from "./sellBookModal";

class BookRow extends Component {
  state = {
    modalIsOpen: false,
  };

  handleOpenModal = () => {
    this.setState({ modalIsOpen: true });
  };

  handleCloseModal = () => {
    this.setState({ modalIsOpen: false });
  };

  render() {
    return (
      <React.Fragment>
        <tr>
          <td>
            <button
              className="btn btn-primary btn-sm m-2"
              onClick={() => {
                this.props.onDelete(this.props.book);
              }}
            >
              Delete Book
            </button>
          </td>

          <td>
            <button
              className="btn btn-primary btn-sm m-2"
              onClick={() => {
                this.handleOpenModal();
              }}
            >
              Sell Book
            </button>

            <SellBookModal
              bookId={this.props.book.id}
              modalIsOpen={this.state.modalIsOpen}
              closeModal={this.handleCloseModal}
              sellBook={this.props.sellBook}
            />
          </td>
          <td>{this.props.book.id}</td>

          <td>
            <TextField
              type="text"
              value={this.props.book.author}
              onChange={(e) => {
                this.props.onAuthorChange(this.props.book, e.target.value);
              }}
            ></TextField>
          </td>

          <td>
            <TextField
              type="text"
              value={this.props.book.title}
              onChange={(e) => {
                this.props.onTitleChange(this.props.book, e.target.value);
              }}
            ></TextField>
          </td>

          <td>
            <TextField
              type="text"
              value={this.props.book.genre}
              onChange={(e) => {
                this.props.onGenreChange(this.props.book, e.target.value);
              }}
            ></TextField>
          </td>

          <td>
            <TextField
              type="text"
              value={this.props.book.price}
              onChange={(e) => {
                this.props.onPriceChange(this.props.book, e.target.value);
              }}
            ></TextField>
          </td>

          <td>
            <TextField
              type="text"
              value={this.props.book.stock}
              onChange={(e) => {
                this.props.onStockChange(this.props.book, e.target.value);
              }}
            ></TextField>
          </td>

          <td>
            <TextField
              type="text"
              value={this.props.book.publishedDate}
              onChange={(e) => {
                this.props.onPublishedDateChange(
                  this.props.book,
                  e.target.value
                );
              }}
            ></TextField>
          </td>
        </tr>
      </React.Fragment>
    );
  }
}

export default BookRow;
