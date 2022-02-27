import React, {useEffect, useState} from "react";
import SendChatMessageForm from "../components/SendChatMessageForm";
import ChatBox from "../components/ChatBox";
import {useUserContext, useUserMessageUpdateContext} from "../context/UserContext";
import {WebSocketService} from "../api/WebSocketService";

const ChatRoom = () => {

    const currentUser = useUserContext();
    const setUserMessage = useUserMessageUpdateContext();
    const [wsClient, setWsClient] = useState(null);
    const [publicChatMessages, setPublicChatMessages] = useState([]);

    useEffect(() => {
        initStompClient();
        return () => {
            if (wsClient) {
                wsClient.unsubscribe().then(() => {
                    wsClient.disconnect();
                });
            }
        }
    },[]);

    useEffect(() => {
        if(wsClient != null) {
            wsClient.connect({}, onConnected, onError);
        }
    }, [wsClient])

    const initStompClient = () => {
        const stompClient = WebSocketService.getWebSocketClient();
        setWsClient(() => stompClient);
    }

    const onConnected = () => {
        wsClient.subscribe('/chatroom/public', onMessageReceived);
        sendUserJoinMessage()
    }

    const onError = (err) => {

    }

    const onMessageReceived = (payload) => {
        let receivedMessage = JSON.parse(payload.body);
        setPublicChatMessages((currentMessages) => ([...currentMessages, receivedMessage]))
    }

    const sendUserJoinMessage = () => {
        const chatJoinMessage = {
            senderName: currentUser.userName,
            messageType: "JOIN",
            userId: currentUser.userId,
        };
        wsClient.send("/app/message", {}, JSON.stringify(chatJoinMessage));
    }

    const sendChatMessage = (event) => {
        event.preventDefault();
        if (wsClient) {
            let chatMessage = {
                senderName: currentUser.userName,
                messageType: "MESSAGE",
                messageBody: currentUser.currentMessage,
                userId: currentUser.userId
            };
            wsClient.send("/app/message", {}, JSON.stringify(chatMessage));
            setUserMessage("");
        }
    }
    return (
        <div className="container w-25 justify-content-center mt-5 border rounded">
            <div className="row" style={{minHeight: "75vh", maxHeight: "75vh"}}>
                <ChatBox chatMessages={publicChatMessages}/>
            </div>
            <div className="row">
                <SendChatMessageForm onMessageSent={sendChatMessage}/>
            </div>

        </div>
    )
}

export default ChatRoom;