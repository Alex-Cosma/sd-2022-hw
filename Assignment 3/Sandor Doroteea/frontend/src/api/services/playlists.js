import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allPlaylists(playlist) {
    return HTTP.get(BASE_URL + "/playlists/" + playlist.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(playlist) {
    return HTTP.put(BASE_URL + "/playlists/create/" + playlist.id, playlist, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(playlist) {
    return HTTP.put(BASE_URL + "/playlists/" + playlist.id, playlist, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(playlist) {
    return HTTP.delete(BASE_URL + "/playlists/" + playlist.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  createTrack(playlist, track) {
    return HTTP.post(
      BASE_URL + "/playlists/" + playlist.id + "/tracks",
      track,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
  editTrack(playlist, track) {
    return HTTP.put(
      BASE_URL + "/playlists/" + playlist.id + "/tracks/" + track.id,
      track,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
  deleteTrack(playlist, track) {
    return HTTP.delete(
      BASE_URL + "/playlists/" + playlist.id + "/tracks1",
      track,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
  sendMail() {
    return HTTP.post(BASE_URL + "/playlists/sendemail", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
