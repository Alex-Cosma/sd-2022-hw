import authHeader, {BASE_URL, HTTP} from "../http";

export default {
    allUsers() {
        return HTTP.get(BASE_URL + "/user", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    userDetails(id) {
        return HTTP.get(BASE_URL + "/user/" + id, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    allStudents() {
        return HTTP.get(BASE_URL + "/students", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    allInstructors() {
        return HTTP.get(BASE_URL + "/instructors", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    allStudentsForInstructor(instructorId) {
        return HTTP.get(BASE_URL + "/my-students/" + instructorId, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    unassignedStudents() {
        return HTTP.get(BASE_URL + "/unassigned-students", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    addStudentForInstructor(studentId, instructorId) {
        return HTTP.put(BASE_URL + "/add-student/" + studentId + "/instructor/" + instructorId, {},{headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    update(user) {
        return HTTP.put(BASE_URL + "/user/" + user.id, user, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },

};
