import React, {useState} from "react";
import {over} from 'stompjs'
import SockJS from "sockjs-client";

let stompClient = null;
const ChatRoom = () => {

    const [currentUser, setCurrentUser] = useState({
        userName: '',
        active: false,
        currentMessage: ''
    });

    const [publicChatMessages, setPublicChatMessages] = useState([]);

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
        stompClient.subscribe('/chatroom/public', onMessageReceived);
        sendUserJoinMessage()
    }

    const onError = (err) => {
    }

    const onMessageReceived = (payload) => {
        let receivedMessage = JSON.parse(payload.body);
        publicChatMessages.push(receivedMessage);
        setPublicChatMessages([...publicChatMessages])
    }

    const sendUserJoinMessage = () => {
        let chatJoinMessage = {
            senderName: currentUser.userName,
            messageType: "JOIN"
        };
        stompClient.send("/app/message", {}, JSON.stringify(chatJoinMessage));
    }

    const sendChatMessage = () => {
        if (stompClient) {
            let chatMessage = {
                senderName: currentUser.userName,
                messageType: "MESSAGE",
                messageBody: currentUser.currentMessage
            };
            stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
            setCurrentUser({...currentUser, "currentMessage": ""});
        }
    }

    const handleChatMessage = (event) => {
        const {value} = event.target;
        setCurrentUser({...currentUser, "currentMessage": value});
    }

    const handleUserName = (event) => {
        const {value} = event.target;
        setCurrentUser({...currentUser, 'userName': value});
    }
    return (
        <div className='container h-100'>
            <div className="row h-75">
                <div className="col" align="center">
                    {!currentUser.active ?
                        <form className="form-inline" onSubmit={registerUser}>
                            <div className="input-group">
                                <input className="form-control mr-sm-2"
                                       value={currentUser.userName}
                                       onChange={handleUserName}
                                       type="text"
                                       placeholder="Enter Username"
                                       aria-label="Enter Username"/>
                                <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Join Chat
                                </button>
                            </div>
                        </form> :
                        <div className="container h-100 mt-5">
                            <div className="row h-100">
                                <div className="col-3"></div>
                                <div className="col-6 border d-flex flex-column-reverse p-0">
                                    <div className="input-group w-100">
                                        <input type="text"
                                               className="form-control"
                                               value={currentUser.currentMessage}
                                               onChange={handleChatMessage}
                                               placeholder="Send Chat Message"
                                               aria-label="Send Chat Message"
                                               aria-describedby="basic-addon2"/>
                                        <div className="input-group-append">
                                            <button
                                                className="btn btn-secondary"
                                                onClick={sendChatMessage}
                                                type="button">Send
                                            </button>
                                        </div>
                                    </div>
                                    {publicChatMessages.map((chatMessage, index) => (
                                        <div
                                            className={`${chatMessage.senderName === currentUser.userName 
                                            && chatMessage.messageType !== "JOIN" ? "border" : ""} mb-2`}
                                            key={index}
                                        >
                                            {chatMessage.messageType === 'MESSAGE' ? chatMessage.messageBody :
                                            chatMessage.senderName + ' has joined the chat'}
                                        </div>
                                    ))}
                                </div>
                                <div className="col-3"></div>
                            </div>
                        </div>


                    }
                </div>
            </div>
        </div>
    )
}

export default ChatRoom