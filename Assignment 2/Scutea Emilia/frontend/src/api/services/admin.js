import authHeader, { API_PATH, BOOKS, HTTP } from "../http";

export default {
  generateReport(type) {
    return HTTP.get(API_PATH + BOOKS + "/export/" + type, {
      responseType: type === "PDF" ? "arraybuffer" : "",
      headers: authHeader(),
    }).then((response) => {
      var fileDownload = require("js-file-download");
      fileDownload(response.data, "Report_" + type + "." + type.toLowerCase());
      return response.data;
    });
  },
};
