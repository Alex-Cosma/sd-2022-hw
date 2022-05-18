import authHeader, {BASE_URL, HTTP} from "../http";

export default {

    allGroups() {
        return HTTP.get(BASE_URL + "/groups", {headers: authHeader()}).then(
            (response) => {
                console.log(response.data);
                return response.data;
            }
        );
    },
    addUser(group_id) {
        const user = JSON.parse(localStorage.getItem("user"));
        return HTTP.patch(BASE_URL + "/groups/" +group_id, user, {headers: authHeader()}).then(
            (response) => {
                console.log(response.data);
                return response.data;
            }
        );
    }
};