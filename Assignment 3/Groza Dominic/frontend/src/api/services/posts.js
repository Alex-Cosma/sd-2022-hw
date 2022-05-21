import authHeader, { BASE_URL, HTTP } from "../http";

export default {

  allPosts() {
    return HTTP.get(BASE_URL + "/posts/", { headers: authHeader() }).then(
      (response) => {
        console.log(response.data);
        return response.data;
      }
    );
  },
  create(post) {
    return HTTP.post(BASE_URL + "/posts", post, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(item) {
    const user = JSON.parse(localStorage.getItem("user"));
    console.log("item", item,item.id);
    return HTTP.put(BASE_URL + "/posts/"+user.id+"/"+ item.id,item, {
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
