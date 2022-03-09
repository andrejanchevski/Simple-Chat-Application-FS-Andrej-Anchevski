import {axiosClient} from "./ApiClient";
import {format} from "date-fns";

export const saveUser = (userData) => {
    return axiosClient.post('/users', JSON.stringify(userData))
}

export const fetchChatRoomMessagesByPage = (chatRoomId, pageSize, page, date) => {
    return axiosClient
        .get(`/chat-messages/paged?chatRoomId=${chatRoomId}&pageSize=${pageSize}&page=${page}&boundedDate=${format(date, `yyyy-MM-dd'T'HH:mm:ss'Z'`)}`);
}