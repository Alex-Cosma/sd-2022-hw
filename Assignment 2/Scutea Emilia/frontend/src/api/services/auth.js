import { API_PATH, HTTP } from "../http";

export default {
  login(data) {
    return HTTP.post(API_PATH + "/auth/sign-in", data).then((response) => {
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
  },

  logout() {
    localStorage.removeItem("user");
  },

  register(data) {
    return HTTP.post(API_PATH + "/auth/sign-up", data);
  },
};
