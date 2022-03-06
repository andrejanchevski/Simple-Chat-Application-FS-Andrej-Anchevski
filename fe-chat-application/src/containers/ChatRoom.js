import React, {useEffect, useState} from "react";
import SendChatMessageForm from "../components/SendChatMessageForm";
import ChatBox from "../components/ChatBox";
import {useUserContext, useUserMessageUpdateContext} from "../context/UserContext";
import {WebSocketService} from "../api/WebSocketService";
import {useNavigate} from "react-router-dom";
import {fetchChatRoomMessagesByPage} from "../api/ApiService";
import userJoinedChatMessage from "../components/UserJoinedChatMessage";

const ChatRoom = () => {

    const currentUser = useUserContext();
    const setUserMessage = useUserMessageUpdateContext();
    const [wsClient, setWsClient] = useState(null);
    const [publicChatMessages, setPublicChatMessages] = useState([]);
    const [nextPagingState, setPagingState] = useState(null);
    const navigate = useNavigate();


    useEffect(() => {
        currentUser.active ? initStompClient() : navigate('/login');
        fetchChatRoomMessagesByPage(1, 3).then((res) => {
            setPagingState(res.data.pagingState)
        });
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

    const fetchNewMessagesPage = () => {
        fetchChatRoomMessagesByPage(1, 3, nextPagingState).then((res) => {
            console.log('PLEASE WORK',res);
        });
    }

    return (
        <div className="container w-25 justify-content-center mt-5 border rounded">
            <div className="row" style={{minHeight: "75vh", maxHeight: "75vh"}}>
                <ChatBox chatMessages={publicChatMessages} onTopChatBoxScroll={fetchNewMessagesPage}/>
            </div>
            <div className="row">
                <SendChatMessageForm onMessageSent={sendChatMessage}/>
            </div>

        </div>
    )
}

export default ChatRoom;