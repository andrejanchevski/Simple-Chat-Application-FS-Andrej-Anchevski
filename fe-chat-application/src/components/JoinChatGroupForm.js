import React from "react";
import {useActiveUpdateContext, useUserContext, useUserNameUpdateContext} from "../context/UserContext";
import {useNavigate} from "react-router-dom";


const JoinChatGroupForm = () => {

    const currentUser = useUserContext();
    const setUserName = useUserNameUpdateContext();
    const setUserActive = useActiveUpdateContext();
    const navigate = useNavigate();


    const handleUserSubmit = (event) => {
        event.preventDefault();
        setUserActive();
        navigate('/chatRoom');

    }

    const handleUserNameChange = (event) =>{
        const {value} = event.target;
        setUserName(value);
    }

    return (
        <div className="container d-flex align-items-center justify-content-center"
             style={{minHeight: "75vh", maxHeight: "75vh"}}>
            <form className="w-50" onSubmit={handleUserSubmit}>
                <div className="input-group">
                    <input className="form-control mr-sm-2"
                           type="text"
                           value={currentUser.userName}
                           onChange={handleUserNameChange}
                           placeholder="Enter Username"
                           aria-label="Enter Username"/>
                    <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Join Chat
                    </button>
                </div>
            </form>
        </div>
    )
}

export default JoinChatGroupForm;