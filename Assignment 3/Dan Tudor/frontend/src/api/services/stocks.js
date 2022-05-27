import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    allStocks() {
        return HTTP.get(BASE_URL + "/stocks", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    create(stock) {
        return HTTP.post(BASE_URL + "/stocks", stock, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    edit(stock) {
        return HTTP.patch(BASE_URL + "/stocks", stock, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
    delete(stock) {
        return HTTP.delete(BASE_URL + "/stocks/" + stock.id, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
};
