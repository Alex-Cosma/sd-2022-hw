import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    getReports() {
        return HTTP.get(BASE_URL + "/demo.pdf", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
}