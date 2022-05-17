import authHeader, { BASE_URL, HTTP } from "../http";

export default {

  allPosts(id) {
    return HTTP.get(BASE_URL + "/posts/"+id, { headers: authHeader() }).then(
      (response) => {
        console.log(response.data);
        return response.data;
      }
    );
  },
  create(post) {
    const user = JSON.parse(localStorage.getItem("user"));
    return HTTP.post(BASE_URL + "/posts/"+user.id, post, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(item) {
    return HTTP.put(BASE_URL + "/posts/"+ item.id,item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    console.log("id", id);
    const user = JSON.parse(localStorage.getItem("user"));
    return HTTP.delete(BASE_URL + "/posts/"+ user.id+"/"+id, {
      headers: authHeader(),
    }).then((response) => {
      console.log(response);
      return response.data;
    });
  },
  sell(item) {
    return HTTP.patch(BASE_URL + "/posts/"+ item.id,item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  report(type){
    return HTTP.get(BASE_URL + "/posts/report/"+ type, {

      responseType:type==="PDF"?'arraybuffer':"",
      headers: authHeader(),
    }).then((response) => {
      var fileDownload = require('js-file-download');
      fileDownload(response.data, "Report_"+type+"."+type.toLowerCase());
      return response.data;
    });
  },
};
