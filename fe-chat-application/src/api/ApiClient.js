import axios from "axios";

export const axiosClient = axios.create({
    baseURL: `https://localhost:8080/app/chat-room`,
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        "Access-Control-Allow-Origin": "*"
    }
});

axiosClient.interceptors.response.use(
    function (response) {
        return response;
    },
    function (error) {
        return Promise.reject(error);
    }
);