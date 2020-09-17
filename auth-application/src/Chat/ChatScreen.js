import React, { Component } from 'react';
import UserList from '../UserList/UserList';
import Chat from './Chat';
import Singleton from '../socket';
import MessageType from './SendMessage/MessageType';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Row, Col } from 'reactstrap';
import { connect } from 'react-redux';
import { userJoined, userJoinedAck, userLeft, messageReceived } from '../actions/index';
import { bindActionCreators } from 'redux';
import { Redirect } from "react-router-dom";

class ChatScreen extends Component {
    constructor() {
        super();
        this.state = {
            modalOpen: sessionStorage.getItem("username") == null || sessionStorage.getItem("username") == "" ? true : false,
            usernameInput: sessionStorage.getItem("username"),
        }
    }

    componentDidMount() {
        if (!this.state.modalOpen) {
            this.registerSocket();
        }
    }

    onLogout() {
        sessionStorage.removeItem('username');
        window.location.reload(false);
    }

    render() {
        if (this.state.modalOpen) {
            return (<Redirect to='/login' />)
        }

        return (
            <Row style={{ width: "200vh" }}>
                <Col xs="3">
                    <UserList users={this.state.users} onLogout={() => this.onLogout} />
                </Col>
                <Col xs="6">
                    <Chat />
                </Col>
            </Row>
        );
    }

    registerSocket() {
        let self = this;
        this.socket = Singleton.getInstance();

        this.socket.onopen = (response) => {
            this.sendJoinedMessage();
        }

        this.socket.onmessage = (response) => {
            let message = JSON.parse(response.data);
            let users;
            switch (message.type) {
                case MessageType.TEXT_MESSAGE:
                    self.props.messageReceived(message);
                    break;
                case MessageType.USER_JOINED:
                    users = JSON.parse(message.data);
                    self.props.userJoined(users);
                    break;
                case MessageType.USER_LEFT:
                    users = JSON.parse(message.data);
                    self.props.userLeft(users);
                    break;
                case MessageType.USER_JOINED_ACK:
                    let thisUser = message.user;
                    self.props.userJoinedAck(thisUser);
                    break;
                default:
            }
        }
        window.onbeforeunload = () => {
            let messageDto = JSON.stringify({ user: this.props.thisUser, type: MessageType.USER_LEFT });
            this.socket.send(messageDto);
        }
    }

    sendJoinedMessage() {
        let messageDto = JSON.stringify({ user: { username: this.state.usernameInput }, type: MessageType.USER_JOINED });
        this.socket.send(messageDto);
    }

    updateInputValue(value) {
        this.setState({ usernameInput: value });
    }
}


function mapStateToProps(state) {
    return {
        messages: state.message,
        users: state.users,
        thisUser: state.thisUser
    }
}

function mapDispatchToProps(dispatch, props) {
    return bindActionCreators({
        userJoined: userJoined,
        userJoinedAck: userJoinedAck,
        userLeft: userLeft,
        messageReceived: messageReceived,
    }, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(ChatScreen);
