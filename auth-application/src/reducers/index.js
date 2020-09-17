import { combineReducers } from "redux";
import UsersReducer from './UsersReducer';
import MessagesReducer from './MessagesReducer';
import ThisUserReducer from './ThisUserReducer';
import FileMessagesReducer from './FileMessagesReducer'

const rootReducer = combineReducers({
    users: UsersReducer,
    messages: MessagesReducer,
    thisUser: ThisUserReducer,
    fileMessages: FileMessagesReducer
});

export default rootReducer;
