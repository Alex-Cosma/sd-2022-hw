import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allQuizzes() {
    return HTTP.get(BASE_URL + "/quizz", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  createQuizz(quizz) {
    return HTTP.post(BASE_URL + "/quizz", quizz, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  editQuizz(id, quizz) {
    return HTTP.put(BASE_URL + "/quizz/" + id, quizz, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteQuizz(id) {
    return HTTP.delete(BASE_URL + "/quizz/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  filterAll(filter) {
    return HTTP.get(BASE_URL + "/quizz/filter/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
