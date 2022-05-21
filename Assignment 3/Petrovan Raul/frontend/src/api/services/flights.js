import authHeader, {BASE_URL, HTTP} from "../http";

export default {
    allFlightsForUser(userId) {
        return HTTP.get(BASE_URL + "/flights/" + userId, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    flightsForInstructorAndStudent(instructorId, studentId) {
        return HTTP.get(BASE_URL + "/flights/" + instructorId + "/withStudent/" + studentId, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    allAirplanes() {
        return HTTP.get(BASE_URL + "/airplanes", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    allAirports() {
        return HTTP.get(BASE_URL + "/airports", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    create(flight) {
        return HTTP.post(BASE_URL + "/flights/add-flight", flight, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },

};
