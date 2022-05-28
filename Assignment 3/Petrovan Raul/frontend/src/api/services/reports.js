import authHeader, {BASE_URL, HTTP} from "../http";

export default {
    getCSV(userId) {
        return HTTP.get(BASE_URL + "/flights/reports/csv/user/" + userId, {
            headers: authHeader(),
            responseType: "blob",
        }).then((response) => {
                const blob = new Blob([response.data], {type: "text/csv"});
                const link = document.createElement("a");
                link.href = window.URL.createObjectURL(blob);
                link.download = "report.csv";
                link.click();
                URL.revokeObjectURL(link.href);
            }
        ).catch(console.error);
    }
};
