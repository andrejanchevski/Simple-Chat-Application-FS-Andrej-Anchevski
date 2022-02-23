import React, {useState} from "react";
import {over} from 'stompjs'
import SockJS from "sockjs-client";

let stompClient = null;
const ChatRoom = () => {

    const [currentUser, setCurrentUser] = useState([{
        userName: '',
        active: false,
        currentMessage: ''
    }])
    const [publicChatMessages, setPublicChatMessages] = useState([])

    const connect = () => {
        let Sock = new SockJS('http://localhost:8080/ws');
        stompClient = over(Sock);
        stompClient.connect({}, onConnected, onError);
    }

    const registerUser = (event) => {
        event.preventDefault();
        connect();
    }

    const onConnected = () => {
        setCurrentUser({...currentUser, "active": true});
        stompClient.subscribe('chatroom/public', onMessageReceived);
        sendUserJoinMessage()
    }

    const onError = () => {

    }

    const onMessageReceived = (payload) => {
        let receivedMessage = JSON.parse(payload.body);
        switch (receivedMessage.messageType) {
            case "JOIN":
                publicChatMessages.push(receivedMessage);
                setPublicChatMessages([...publicChatMessages])
                break
            case "MESSAGE":
                break;
        }
    }

    const sendUserJoinMessage = () => {
        let chatJoinMessage = {
            senderName: currentUser.userName,
            messageType: "JOIN"
        }
        stompClient.send("/app/message", {}, JSON.stringify(chatJoinMessage))
    }

    const handleUserName = (event) => {
        const {value} = event.target
        setCurrentUser({...currentUser, 'userName' : value})
    }

    return (
        <div className='container'>
            <div className="row">
                <div className="col" align="center">
                    <form className="form-inline" onSubmit={registerUser}>
                        <div className="input-group">
                            <input className="form-control mr-sm-2"
                                   value={currentUser.userName || ''}
                                   onChange={handleUserName}
                                   type="text"
                                   placeholder="Enter Username"
                                   aria-label="Enter Username"/>
                            <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Join Chat</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default ChatRoom