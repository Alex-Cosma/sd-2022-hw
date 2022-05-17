import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allQuestions() {
    return HTTP.get(BASE_URL + "/question", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  getQuestion(id) {
    return HTTP.get(BASE_URL + "/question/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  createQuestion(question) {
    return HTTP.post(BASE_URL + "/question", question, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  editQuestion(id, question) {
    return HTTP.put(BASE_URL + "/question/" + id, question, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteQuestion(id) {
    return HTTP.delete(BASE_URL + "/question/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  filterAll(filter) {
    return HTTP.get(BASE_URL + "/question/filter/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
