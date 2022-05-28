import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allPlaylistTracks() {
    return HTTP.get(BASE_URL + "/playlists/tracks/238", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(playlist) {
    return HTTP.post(BASE_URL + "/playlists/{id}/tracks", playlist, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(playlist) {
    return HTTP.put(
      BASE_URL + "/playlists/{id}/tracks/" + playlist.id,
      playlist,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
  delete() {
    return HTTP.delete(BASE_URL + "/playlists/{id}/tracks1", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
