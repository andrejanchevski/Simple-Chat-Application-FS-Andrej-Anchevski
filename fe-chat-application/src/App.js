import ChatRoom from "./containers/ChatRoom";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
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
                    </Routes>
                </Router>
            </UserContextProvider>
        </div>
    );
}

export default App;

