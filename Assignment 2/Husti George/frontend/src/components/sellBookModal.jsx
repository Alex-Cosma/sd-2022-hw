import React, { Component } from "react";
import { Modal } from "react-bootstrap";
import TextField from "@material-ui/core/TextField";

class SellBookModal extends Component {
  state = {
    userId: "",
    quantity: "",
  };

  handleUserIdTextFieldChange = (value) => {
    this.setState({ userId: value });
  };
  handleQuantityTextFieldChange = (value) => {
    this.setState({ quantity: value });
  };

  render() {
    return (
      <Modal show={this.props.modalIsOpen} onHide={this.props.closeModal}>
        <Modal.Header>
          <Modal.Title>Sell Book</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <TextField
            type="text"
            placeholder="User ID"
            onChange={(event) => {
              this.handleUserIdTextFieldChange(event.target.value);
            }}
          ></TextField>
          <br />
          <br />
          <TextField
            type="text"
            placeholder="Quantity"
            onChange={(event) => {
              this.handleQuantityTextFieldChange(event.target.value);
            }}
          ></TextField>
        </Modal.Body>
        <Modal.Footer>
          <button
            className="btn btn-primary btn-sm m-2"
            onClick={() => {
              this.props.sellBook(this.state.userId, this.state.quantity);
              this.props.closeModal();
            }}
          >
            Sell Book
          </button>

          <button onClick={this.props.closeModal}>Close</button>
        </Modal.Footer>
      </Modal>
    );
  }
}

export default SellBookModal;
