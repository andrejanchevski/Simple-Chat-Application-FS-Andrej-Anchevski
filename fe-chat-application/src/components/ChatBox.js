import ChatMessageDetail from "./ChatMessageDetail";
import {useUserContext} from "../context/UserContext";
import UserJoinedChatMessage from "./UserJoinedChatMessage";
import MessageType from "../domain/MessageType";
import {useEffect, useRef} from "react";

const ChatBox = (props) => {
    const currentUser = useUserContext();
    const topChatBoxDivRef = useRef();
    const chatBoxDivRef = useRef();

    useEffect(() => {})

    const scrollChatBox = () => {
       if (topChatBoxDivRef.current.getBoundingClientRect().top === chatBoxDivRef.current.getBoundingClientRect().top){
           props.onTopChatBoxScroll();
       }
    }

    return (
        <div className="col overflow-auto d-flex flex-column" style={{height: "75vh"}}
             ref={chatBoxDivRef}
             onScroll={scrollChatBox}>
            <div ref={topChatBoxDivRef}/>
            {props.chatMessages.map((chatMessage, index) => {
                return chatMessage.messageType === MessageType.MESSAGE ?
                    <ChatMessageDetail
                        isUserMessage={currentUser.userName === chatMessage.senderName}
                        senderName={chatMessage.senderName}
                        messageBody={chatMessage.messageBody}
                        dateCreated={chatMessage.dateCreated}
                        key={index}/> :
                    <UserJoinedChatMessage senderName={chatMessage.senderName} key={index}/>
            })}
        </div>
    )
}

export default ChatBox;