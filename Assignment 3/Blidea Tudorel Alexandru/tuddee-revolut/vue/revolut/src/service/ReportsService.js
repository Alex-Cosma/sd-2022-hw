import Url from "@/service/Url";
import AuthService from "@/service/AuthService";
import axios from "axios";

const options = {
    "CSV": {
        type: "csv/text"
    },
    "PDF": {
        type: "application/octetstream"
    }
}

const getCardsReport = async (type) => {
    let report = (await axios.get(Url.getCardReportsUrl(type.toUpperCase()), {
        headers: AuthService.getAuthHeaders()
    })).data;

    const blob = new Blob([report], options[type.toUpperCase()]);

    const href = URL.createObjectURL(blob);

    const a = Object.assign(document.createElement("a"), {
        href,
        style: "display:none",
        download: `cards_report.${type.toLowerCase()}`,
    });

    document.body.appendChild(a);

    a.click();
    URL.revokeObjectURL(href);
    a.remove();
}

export default {
    getCardsReport
}