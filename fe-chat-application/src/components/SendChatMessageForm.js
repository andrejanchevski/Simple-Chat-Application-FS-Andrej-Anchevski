import {useUserContext, useUserMessageUpdateContext} from "../context/UserContext";

const SendChatMessageForm = (props) => {
    const currentUser = useUserContext();
    const setUserMessage = useUserMessageUpdateContext();

    const handleUserMessageChange = (event) => {
        const {value} = event.target;
        setUserMessage(value);
    }

    return (
        <div className="col p-0">
            <form onSubmit={props.onMessageSent}>
                <div className="input-group">
                    <input type="text"
                           className="form-control"
                           placeholder="Send Message"
                           aria-label="Send Message"
                           aria-describedby="basic-addon2"
                           value={currentUser.currentMessage}
                           onChange={handleUserMessageChange}
                    />
                    <div className="input-group-append">
                        <button className="btn btn-secondary"
                                type="submit"
                        >Send</button>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default SendChatMessageForm;