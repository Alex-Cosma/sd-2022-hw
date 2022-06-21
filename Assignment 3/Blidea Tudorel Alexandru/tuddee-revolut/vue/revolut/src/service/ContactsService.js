import AuthService from "@/service/AuthService";
import Url from "@/service/Url";
import axios from "axios";

const getMyContacts = async () => {
    return (await axios.get(Url.getMyContactsUrl(), {
        headers: AuthService.getAuthHeaders()
    })).data;
}

const getMyPendingContacts = async () => {
    console.log(Url.getMyContactsUrl())
    return (await axios.get(Url.getMyPendingContactsUrl(), {
        headers: AuthService.getAuthHeaders()
    })).data;
}

const sendMoney = async (form) => {
    console.log(Url.getMyContactsUrl())
    return (await axios.post(Url.getTransferEndpoint(), form, {
        headers: AuthService.getAuthHeaders()
    })).data;
}

export default {
    sendMoney,
    getMyContacts,
    getMyPendingContacts
}