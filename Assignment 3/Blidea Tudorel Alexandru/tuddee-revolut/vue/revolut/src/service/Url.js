
const baseUrl = "http://localhost:8080/api";

const getAuthUrl = () => {
    return baseUrl + "/auth";
}

const getDetailsUrl = () => {
    return baseUrl + "/users/my/details";
}

const getMyCardsUrl = () => {
    return baseUrl + "/cards/my_cards";
}

const getCardReportsUrl = (type) => {
    return baseUrl + "/reports/my_cards/" + type;
}

const getMyContactsUrl = () => {
    return baseUrl + "/contacts/my";
}

const getMyPendingContactsUrl = () => {
    return baseUrl + "/contacts/my/pending";
}

const getTransferEndpoint = () => {
    return baseUrl + "/pay/transfer"
}

const getMyDepositsUrl = () => {
    return baseUrl + '/deposits/my';
}

export default {
    getAuthUrl,
    getDetailsUrl,
    getMyCardsUrl,
    getMyContactsUrl,
    getMyDepositsUrl,
    getCardReportsUrl,
    getTransferEndpoint,
    getMyPendingContactsUrl,
}