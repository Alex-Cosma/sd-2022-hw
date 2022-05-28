import authHeader, { BASE_URL, HTTP } from "../http";
// import fileDownload from "js-file-download";

export default {
    allUsersAccommodations(user) {
    return HTTP.get(BASE_URL + "/users/" + user.id + "/accommodations", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(user) {
    return HTTP.post(BASE_URL + "/users", user, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  delete(user) {
    return HTTP.delete(BASE_URL + "/users/" + user.id, {
        headers: authHeader(),
    }).then((response) => {
        return response.data;
    });
  },
    edit(user) {
        return HTTP.put(BASE_URL + "/users/" + user.id, user, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },

    generateReport(report) {

        return HTTP.get(BASE_URL + "/users/export/" + report.type, { responseType: 'arraybuffer', headers: authHeader() }).then(
            (response) => {
                var fileDownload = require('js-file-download');
                fileDownload(response.data, "Report"+"."+report.type);
                return response.data;
            }
        );
    },

    becomeHost(user){
        return HTTP.patch(BASE_URL + "/users/" + user.id, user,{
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },

    downloadPdf(item) {
        return HTTP.get(BASE_URL + "/accommodations/" + item.id + "/pdf",{
            responseType: 'arraybuffer',
            headers: authHeader(),
        }).then((response) => {
            var fileDownload = require('js-file-download');
            fileDownload(response.data, "Report.pdf");
            return response.data;
        });
    },

    sendEmail(item){
        return HTTP.get(BASE_URL + "/accommodations/" + item.id + "/sendEmail",{
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },

};
