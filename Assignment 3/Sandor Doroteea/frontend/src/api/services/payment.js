import authHeader, { BASE_URL, HTTP } from "../http";
// https://www.npmjs.com/package/js-file-download
export default {
  checkout(payment) {
    return HTTP.post(BASE_URL + "/payment", payment, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  findAll() {
    console.log("here");
    return HTTP.get(BASE_URL + "/payment", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  save(payment) {
    return HTTP.post(BASE_URL + "/payment", payment, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  generatePDF(payment) {
    return HTTP.get(BASE_URL + "/payment/PDF/" + payment.user_id, {
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      var fileDownload = require("js-file-download");
      fileDownload(response.data, "MyTracks.pdf");
      return response.data;
    });
  },
  generateCSV(payment) {
    return HTTP.get(BASE_URL + "/payment/CSV/" + payment.user_id, {
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      var fileDownload = require("js-file-download");
      fileDownload(response.data, "MyTracks.csv");
      return response.data;
    });
  },
};
