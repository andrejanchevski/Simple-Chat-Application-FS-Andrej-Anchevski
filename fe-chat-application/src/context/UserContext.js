import React, {createContext, useState, useContext} from "react";

const UserContext = createContext({});
const UserUserNameUpdateContext = createContext({});
const UserActiveUpdateContext = createContext({});
const UserMessageUpdateContext = createContext({});

export const useUserContext = () => {
    const context = useContext(UserContext);
    if (context === undefined) {
        throw new Error("userUserContext was outside of its Provider");
    }

    return context;
}

export const useUserNameUpdateContext = () => {
    const context = useContext(UserUserNameUpdateContext);
    if (context === undefined) {
        throw new Error("useUserNameUpdateContext was outside of its Provider");
    }
    return context;
}

export const useActiveUpdateContext = () => {
    const context = useContext(UserActiveUpdateContext);
    if (context === undefined) {
        throw new Error("useActiveUpdateContext was outside of its Provider");
    }
    return context;
}

export const useUserMessageUpdateContext = () => {
    const context = useContext(UserMessageUpdateContext);
    if (context === undefined) {
        throw new Error("useUserMessageUpdateContext was outside of its Provider");
    }
    return context;
}



export const UserContextProvider = ({children}) => {
    const [currentUser, setUser] = useState({
        userName: "",
        userId: "",
        active: false,
        currentMessage: ""

    });

    const setUserName = (newUsername) => {
        setUser(current => ({...current, userName: newUsername}))
    };

    const makeUserActive = (userId) => {
        setUser(current => ({...current, active: true, userId: userId}))
    }

    const setUserMessage = (newMessage) => {
        setUser(current => ({...current, currentMessage: newMessage}))
    }

    return (
        <UserContext.Provider value={currentUser}>
            <UserUserNameUpdateContext.Provider value={setUserName}>
                <UserActiveUpdateContext.Provider value={makeUserActive}>
                    <UserMessageUpdateContext.Provider value={setUserMessage}>
                        {children}
                    </UserMessageUpdateContext.Provider>
                </UserActiveUpdateContext.Provider>
            </UserUserNameUpdateContext.Provider>
        </UserContext.Provider>)
}