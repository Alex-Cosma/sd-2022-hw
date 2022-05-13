import React, { Component } from "react";
import TextField from "@material-ui/core/TextField";

class BooksFilters extends Component {
  state = {
    title: "",
    author: "",
    genre: "",
  };

  handleTitleChange = (value) => {
    this.setState({ title: value });
  };

  handleAuthorChange = (value) => {
    this.setState({ author: value });
  };

  handleGenreChange = (value) => {
    this.setState({ genre: value });
  };

  render() {
    return (
      <React.Fragment>
        <TextField
          type="text"
          placeholder="Title"
          value={this.state.title}
          onChange={(event) => {
            this.handleTitleChange(event.target.value);
          }}
        ></TextField>
        <br />
        <TextField
          type="text"
          placeholder="Author"
          value={this.state.author}
          onChange={(event) => {
            this.handleAuthorChange(event.target.value);
          }}
        ></TextField>
        <br />
        <TextField
          type="text"
          placeholder="Genre"
          value={this.state.genre}
          onChange={(event) => {
            this.handleGenreChange(event.target.value);
          }}
        ></TextField>

        <button
          className="btn btn-primary btn-sm m-2"
          onClick={() => {
            this.props.fetchByFilters(this.state);
          }}
        >
          Filter
        </button>
      </React.Fragment>
    );
  }
}

export default BooksFilters;
