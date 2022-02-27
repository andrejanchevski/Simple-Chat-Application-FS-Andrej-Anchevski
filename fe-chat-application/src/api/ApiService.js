import {axiosClient} from "./ApiClient";

export const saveUser = (userData) => {
    return axiosClient.post('/users', JSON.stringify(userData))
}