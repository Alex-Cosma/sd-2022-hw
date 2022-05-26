import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allThreads() {
    return HTTP.get(BASE_URL + "/thread", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  threadById(id) {
    return HTTP.get(BASE_URL + "/thread/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allThreadsFromCategory(id) {
    return HTTP.get(BASE_URL + "/thread/of_category/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(thread) {
    return HTTP.post(BASE_URL + "/thread", thread, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(thread) {
    return HTTP.put(BASE_URL + "/thread/" + thread.id, thread, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(thread) {
    return HTTP.delete(BASE_URL + "/thread/" + thread.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
