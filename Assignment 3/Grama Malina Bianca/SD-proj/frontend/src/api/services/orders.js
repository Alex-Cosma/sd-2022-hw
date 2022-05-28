import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  create(order) {
    return HTTP.post(BASE_URL + "/orders", order, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  ordersForCustomer(customerId) {
    return HTTP.get(BASE_URL + "/orders/" + customerId, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(orderId) {
    return HTTP.delete(BASE_URL + "/orders/" + orderId, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  showReport(reportType, userId) {
    return HTTP.get(BASE_URL + "/orders/report/" + reportType + "/" + userId, {
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      var fileDownload = require("js-file-download");
      fileDownload(response.data, "report.pdf");
      return response.data;
    });
  },
};
