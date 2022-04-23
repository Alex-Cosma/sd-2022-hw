import tr, { Component } from "react";
import TextField from "@material-ui/core/TextField";

class Patient extends Component {
  state = {};

  handleChange = (e) => {
    this.props.name = "aaa";
  };

  render() {
    return (
      <tr>
        <td>
          <button onClick={() => {}} className="btn btn-primary btn-sm">
            Consultations
          </button>
        </td>
        <td>
          <button
            onClick={() => {
              this.props.onDelete(this.props.patient.id);
            }}
            className="btn btn-danger btn-sm"
          >
            Delete
          </button>
        </td>
        <td>{this.props.id}</td>
        <td>
          <TextField
            type="text"
            value={this.props.name}
            onChange={(e) => {
              this.props.onNameChange(this.props.patient, e.target.value);
            }}
          ></TextField>
        </td>
        <td>
          <TextField
            type="text"
            value={this.props.personalNumericalCode}
            onChange={(e) => {
              this.props.onPersonalNumericCodeChange(
                this.props.patient,
                e.target.value
              );
            }}
          ></TextField>
        </td>
        <td>
          <TextField
            type="text"
            value={this.props.birthDate}
            onChange={(e) => {
              this.props.onBirthDateChange(this.props.patient, e.target.value);
            }}
          ></TextField>
        </td>
        <td>
          <TextField
            type="text"
            value={this.props.address}
            onChange={(e) => {
              this.props.onAddressChange(this.props.patient, e.target.value);
            }}
          ></TextField>
        </td>
      </tr>
    );
  }
}

export default Patient;
