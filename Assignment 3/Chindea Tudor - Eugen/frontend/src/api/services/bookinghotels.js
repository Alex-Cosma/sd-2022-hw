import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    allHotels() {
    return HTTP.get(BASE_URL + "/bookinghotels", { headers: authHeader() }).then(
        (response) => {
        return response.data;
        }
    );
    },
  bookRoom(bookingHotel) {
    return HTTP.post(BASE_URL + "/bookinghotels", bookingHotel, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  
 

};