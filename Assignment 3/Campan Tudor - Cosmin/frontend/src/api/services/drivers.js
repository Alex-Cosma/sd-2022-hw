import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    allDrivers() {
        return HTTP.get(BASE_URL + "/drivers", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    create(driver) {
        return HTTP.post(BASE_URL + "/drivers/create", driver, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    edit(driver) {
        return HTTP.put(BASE_URL + "/drivers/" + driver.id, driver, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
    delete(driver) {
        return HTTP.delete(BASE_URL + "/drivers/" + driver.id, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
    giveFine(driver,fine) {
        console.log(fine);
        return HTTP.put(BASE_URL + "/drivers/giveFine/" + driver.id, fine, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
    payFine(driver,fine) {
        console.log(fine);
        return HTTP.put(BASE_URL + "/drivers/payFine/" + driver.id, fine, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
};
