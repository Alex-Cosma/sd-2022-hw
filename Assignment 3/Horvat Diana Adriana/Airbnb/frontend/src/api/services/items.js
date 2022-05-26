import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/accommodations", { headers: authHeader() }).then(
      (response) => {
        console.log(response.data);
        return response.data;
      }
    );
  },
  create(item) {
    console.log(item);
    return HTTP.post(BASE_URL + "/accommodations", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  bookAccommodation(item) {
    console.log(item);
    return HTTP.post(BASE_URL + "/bookings", item,{
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(item) {
    return HTTP.delete(BASE_URL + "/accommodations/" + item.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(item) {
    console.log(item);
    return HTTP.put(BASE_URL + "/accommodations/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  postReview(item){
    return HTTP.post(BASE_URL + "/reviews", item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  allBookings(user){
    return HTTP.get(BASE_URL + "/users/" + user.id +"/bookings", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  cancelBooking(booking_id){
    return HTTP.delete(BASE_URL + "/bookings/" + booking_id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  }
};
