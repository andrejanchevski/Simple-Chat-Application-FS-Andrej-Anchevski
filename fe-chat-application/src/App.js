import ChatRoom from "./containers/ChatRoom";
import {BrowserRouter as Router, Navigate, Route, Routes} from "react-router-dom";
import JoinChatGroupForm from "./components/JoinChatGroupForm";
import {UserContextProvider} from "./context/UserContext";


function App() {

    return (
        <div>
            <UserContextProvider>
                <Router>
                    <Routes>
                        <Route path="/chatRoom" element={<ChatRoom/>}/>
                        <Route path="/login" element={<JoinChatGroupForm/>}/>
                        <Route exact path="/" element={<Navigate replace to ="/login" />}>
                        </Route>
                    </Routes>
                </Router>
            </UserContextProvider>
        </div>
    );
}

export default App;

