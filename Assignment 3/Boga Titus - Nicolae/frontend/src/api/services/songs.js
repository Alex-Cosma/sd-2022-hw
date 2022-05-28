import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  getSongs() {
    return HTTP.get(BASE_URL + "/songs", { headers: authHeader() }).then(
      (response) => {
        for (var i = 0; i < response.data.length; ++i) {
          var aux = response.data[i];
          aux = {
            id: aux.id,
            title: aux.title + " by " + aux.artist,
            items: [
              { title: "Play" },
              { title: "Pause" },
              { title: "Go to player (Beta Version)" },
              { title: "Add to playlist" },
            ],
          };
          response.data[i] = aux;
        }

        return response.data;
      }
    );
  },
  getSongsOnline() {
    return HTTP.get(BASE_URL + "/songs/online", { headers: authHeader() }).then(
      (response) => {
        for (var i = 0; i < response.data.length; ++i) {
          var aux = response.data[i];
          aux = {
            title: aux.title + " by " + aux.artist,
            items: [
              { title: "Add to playlist" },
              { title: "Go to player" },
              { title: "Go to artist" },
            ],
          };
          response.data[i] = aux;
        }

        return response.data;
      }
    );
  },
  getMp3(item) {
    return HTTP.get(BASE_URL + "/songs/" + item.id, {
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
