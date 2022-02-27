import {axiosClient} from "./ApiClient";

export const saveUser = (userData) => {
    return axiosClient.post('/users', JSON.stringify(userData))
}

export const fetchLatestMessagesAfterSomeDate = (chatRoomId, dateBefore) => {
    return axiosClient.get(`/chat-messages/archived?chatRoomId=${chatRoomId}&beforeDate=${dateBefore}`)
}

export const fetchAllChatRoomMessages = (chatRoomId) => {
    return axiosClient.get(`/chat-messages?chatRoomId=${chatRoomId}`)
}