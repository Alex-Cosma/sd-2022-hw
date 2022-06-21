import AuthService from "@/service/AuthService";
import Url from "@/service/Url";
import axios from "axios";

const getMyDeposits = async () => {
    return (await axios.get(Url.getMyDepositsUrl(), {
        headers: AuthService.getAuthHeaders()
    })).data;
}

export default {
    getMyDeposits
}