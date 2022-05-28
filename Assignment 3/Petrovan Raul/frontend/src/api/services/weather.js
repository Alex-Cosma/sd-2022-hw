import authHeader, {BASE_URL, HTTP} from "../http";

export default {
    getMetarFor(icao) {
        return HTTP.get(BASE_URL + "/weather/metar/" + icao, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    getMetarDecodedFor(icao) {
        return HTTP.get(BASE_URL + "/weather/metar/" + icao + "/decoded", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    getTafDecodedFor(icao) {
        return HTTP.get(BASE_URL + "/weather/taf/" + icao + "/decoded", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
};
