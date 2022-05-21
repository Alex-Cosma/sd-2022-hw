import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allTracks() {
    return HTTP.get(BASE_URL + "/tracks", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(track) {
    return HTTP.post(BASE_URL + "/tracks", track, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  update(track) {
    return HTTP.put(BASE_URL + "/tracks/" + track.id, track, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(track) {
    return HTTP.delete(BASE_URL + "/tracks/" + track.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
