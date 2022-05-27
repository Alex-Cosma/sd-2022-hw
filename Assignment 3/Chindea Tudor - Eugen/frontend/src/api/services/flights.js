import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allFlights() {
    return HTTP.get(BASE_URL + "/flights", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(flight) {
    

    return HTTP.post(BASE_URL + "/flights", flight, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(flight) {
    return HTTP.patch(BASE_URL + "/flights/" + flight.id, flight, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  delete(flight){
    return HTTP.delete(BASE_URL + "/flights/" + flight.id, { headers:authHeader() }).then(
      (response) => {
      return response.data;
    });
  },
 

};
