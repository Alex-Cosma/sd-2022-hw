import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allCourts() {
    return HTTP.get(BASE_URL + "/courts", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(court) {
    return HTTP.post(BASE_URL + "/courts", court, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(court) {
    return HTTP.patch(BASE_URL + "/courts", court, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(court) {
    return HTTP.delete(BASE_URL + "/courts/" + court.id, {
      headers: authHeader(),
    }).then();
  },
  reserve(court, name) {
    return HTTP.post(BASE_URL + "/reserve?courtId=" + court.id + "&username=" + name, {}, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
