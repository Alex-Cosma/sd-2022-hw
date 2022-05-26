import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allPosts() {
    return HTTP.get(BASE_URL + "/post", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  allPostsFromThread(id) {
    return HTTP.get(BASE_URL + "/post/of_thread/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(post) {
    return HTTP.post(BASE_URL + "/post", post, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(post) {
    return HTTP.put(BASE_URL + "/post/" + post.id, post, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(post) {
    return HTTP.delete(BASE_URL + "/thread/" + post.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
