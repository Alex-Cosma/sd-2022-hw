import authHeader, { BASE_URL, HTTP } from "../http";
//import { jsPDF } from "jspdf";
import ByteToDocument from "./reportGen";
//import FileDownload from "js-file-download";

export default {
  getAllItemz() {
    console.log(BASE_URL + "/property");
    return HTTP.get(BASE_URL + "/property", { headers: authHeader() }).then(
      (response) => {
        console.log(response.data);
        return response.data;
      }
    );
  },
  getPropertiesByOwner(owner) {
    console.log(BASE_URL + "/property" + owner.getUsername());
    return HTTP.get(BASE_URL + "/property", { headers: authHeader() }).then(
        (response) => {
          console.log(response.data);
          return response.data;
        }
    );
  },
  create(property) {
    console.log(BASE_URL + "/property", property);
    return HTTP.post(BASE_URL + "/property", property, {
      headers: authHeader(),
    }).then((response) => {
      console.log(response.data);
      return response.data;
    });
  },
  edit(property) {
    return HTTP.patch(BASE_URL + property.id, property, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteProperty(property) {
    console.log("delete");
    return HTTP.delete(BASE_URL + "/property/" + property.id, {
      headers: authHeader(),
    }).then((response) => {
      console.log(response.data);
      return response.data;
    });
  },
  dataURItoBlob(dataURI) {
    const byteString = Buffer.from(dataURI).toString("base64");
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const int8Array = new Uint8Array(arrayBuffer);
    for (let i = 0; i < byteString.length; i++) {
      int8Array[i] = byteString.charCodeAt(i);
    }
    const blob = new Blob([int8Array], { type: "application/pdf" });
    return blob;
  },
  generateReport(type) {
    console.log(type);
    return HTTP.get(BASE_URL + "/items/export/" + type, {
      headers: authHeader(),
    }).then((response) => {
      console.log(response.data);
      if (type.localeCompare("PDF") === 0) {
        //TODO
        var sampleArr = ByteToDocument.base64ToArrayBuffer(response.data);
        ByteToDocument.saveByteArray(
          "Report_on_books_out_of_stock" + ".pdf",
          sampleArr
        );
      } else {
        console.log("CSV");
        let FileDownloadCSV = require("js-file-download");
        FileDownloadCSV(response.data, "report." + type);
        console.log("success!", response.data);
      }

      return response.data;
    });
  },
};
