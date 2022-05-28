import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  sendMail() {
    return HTTP.get(BASE_URL + "/email", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
};
