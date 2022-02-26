const ChatMessageDetail = (props) => {

    return (
        <div className={`${props.isUserMessage ? 'border': ""} w-75 mt-auto mb-2 p-1`}>
            <span className="text-secondary" style={{fontSize: "0.9rem"}}>{props.senderName}</span>
            <div className="text-dark">{props.messageBody}</div>
            <span className="text-secondary" style={{fontSize: "0.9rem"}}>{props.dateCreated}</span>
        </div>
    )
}


export default ChatMessageDetail;