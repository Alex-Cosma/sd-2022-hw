import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allInvestments(user) {
    return HTTP.get(BASE_URL + "/investments/" + user.id, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
};
