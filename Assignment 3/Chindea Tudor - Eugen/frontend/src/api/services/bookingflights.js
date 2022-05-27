import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    allFlights() {
    return HTTP.get(BASE_URL + "/bookingflights", { headers: authHeader() }).then(
        (response) => {
        return response.data;
        }
    );
    },
  bookFlight(bookingHotel) {
    return HTTP.post(BASE_URL + "/bookingflights", bookingHotel, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  
 

};