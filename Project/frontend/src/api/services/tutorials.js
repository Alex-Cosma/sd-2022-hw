import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  create(formdata) {
    return HTTP.post(BASE_URL + "/tutorials/addTutorial", formdata, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data",
    }).then((response) => {
      return response.data;
    });
  },
  getAll() {
    return HTTP.get(BASE_URL + "/tutorials/getAllTutorials", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  downloadTutorial(title) {
    return HTTP.get(BASE_URL + "/tutorials/getTutorialForDownload/" + title, {
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      var fileDownload = require("js-file-download");
      fileDownload(response.data, title.toString());
      return response.data;
    });
  },
};
