import axios from "axios";

import Url from "@/service/Url";

const auth = axios.create({
    baseURL: Url.getAuthUrl(),
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Origin': '*'
    }
});

let authHeaders = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer ',
    'Access-Control-Allow-Origin': '*'
}

const getAuthHeaders = () => {
    return { ...authHeaders }
}

const signUp = async signUpForm => {
    return (await auth.post('/signup', signUpForm)).data;
}

const signIn = async signInForm => {
    const jwtResponse = (await auth.post('/signin', signInForm)).data;

    authHeaders = {
        ...authHeaders,
        'Authorization': 'Bearer ' + jwtResponse.token
    }

    return jwtResponse;
}

const getDetails = async () => {
    const headerz = getAuthHeaders();
    return await fetch("http://localhost:8080/api/users/my/details", {
        method: "GET",
        mode: "cors",
        headers: headerz
    }).then(response => response.json());
}

export default { getAuthHeaders, signUp, signIn, getDetails }