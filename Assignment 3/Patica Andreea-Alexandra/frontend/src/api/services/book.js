import authHeader, { BASE_URL, HTTP } from "../http";
import * as emails from "./email.js";
import ByteToDocument from "@/api/services/pdfGenerator";

export default {
    // getAllItemz() {
    //     console.log(BASE_URL + "/property");
    //     return HTTP.get(BASE_URL + "/property", { headers: authHeader() }).then(
    //         (response) => {
    //             console.log(response.data);
    //             return response.data;
    //         }
    //     );
    // },
    getBookingsByOwner(owner) {
        console.log(BASE_URL + "/book/" + owner.username);
        return HTTP.get(BASE_URL + "/book/"+owner.username, { headers: authHeader() }).then(
            (response) => {
                console.log(response.data);
                return response.data;
            }
        );
    },
    create(book) {
        console.log(BASE_URL + "/book", book);
        return HTTP.post(BASE_URL + "/book", book, {
            headers: authHeader(),
        }).then((response) => {
            emails.sendEmail(emails.configureEmail(book));
            console.log(response.data);
            return response.data;
        });
    },
    // edit(property) {
    //     return HTTP.patch(BASE_URL + property.id, property, {
    //         headers: authHeader(),
    //     }).then((response) => {
    //         return response.data;
    //     });
    // },
    deleteBooking(booking) {
        console.log("delete");
        return HTTP.delete(BASE_URL + "/book/" + booking.id, {
            headers: authHeader(),
        }).then((response) => {
            console.log(response.data);
            return response.data;
        });
    },

    downloadBooking(booking) {
        console.log("PDF");
        return HTTP.post(BASE_URL + "/book/pdf", booking, {
            headers: authHeader(),
        }).then((response) => {
            console.log(response.data);
            const sampleArr = ByteToDocument.base64ToArrayBuffer(response.data);
            console.log(sampleArr)
            ByteToDocument.saveByteArray(
                'Booking_' +
                booking.property.name +
                '_on_date_' +
                booking.date +
                '.pdf',
                response.data
            )
        });
    },

};
