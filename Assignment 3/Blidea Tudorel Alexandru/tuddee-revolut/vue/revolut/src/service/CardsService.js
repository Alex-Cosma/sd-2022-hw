import AuthService from "@/service/AuthService";
import Url from "@/service/Url";
import axios from "axios";

const getCards = async () => {
    return (await axios.get(Url.getMyCardsUrl(), {
        headers: AuthService.getAuthHeaders()
    })).data;
}

export default {
    getCards
}