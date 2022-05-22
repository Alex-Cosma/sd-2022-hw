import authHeader, {BASE_URL, HTTP} from "../http";

export default {
    allUsers() {
        return HTTP.get(BASE_URL + "/users", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    addFriend(friendId) {
        const user=JSON.parse(localStorage.getItem('user'));
        return HTTP.patch(BASE_URL + "/users/"+friendId,user, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
}
;
