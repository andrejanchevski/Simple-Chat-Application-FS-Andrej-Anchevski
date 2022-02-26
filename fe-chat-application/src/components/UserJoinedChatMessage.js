const UserJoinedChatMessage = (props) => {
  return (
      <div className= "w-100 mt-auto mb-2 p-1 text-center">
          <div className="text-dark">{props.senderName} has joined the conversation</div>
      </div>
  )
}

export default UserJoinedChatMessage;