import SockJS from "sockjs-client";
import {over} from "stompjs";


export const WebSocketService = {
    getWebSocketClient: (onConnected, onError) => {
        const Sock = new SockJS('http://localhost:8080/ws');
        const stompClient = over(Sock);
        return stompClient
    }
}