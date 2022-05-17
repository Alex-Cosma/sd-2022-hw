import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allQuizzSessions() {
    return HTTP.get(BASE_URL + "/quizzSession", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  createQuizzSession(quizzSession) {
    return HTTP.post(BASE_URL + "/quizzSession", quizzSession, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  exportReport(type, id) {
    return HTTP.get(BASE_URL + "/quizzSession/export/" + type + "/" + id, {
      responseType: type ? "arraybuffer" : "",
      headers: authHeader(),
    }).then((response) => {
      var fileDownload = require("js-file-download");
      fileDownload(response.data, "report." + type.toLowerCase());
      return response.data;
    });
  },
};
