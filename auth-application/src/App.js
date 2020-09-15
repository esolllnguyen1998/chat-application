import React, { Component } from 'react';
import './App.css';

import { MuiThemeProvider } from '@material-ui/core/styles';
import UserList from './UserList/UserList';
import Chat from './Chat/Chat';
import Singleton from './socket';
import MessageType from './Chat/SendMessage/MessageType';
import Login from './Auth/login';
import { connect } from 'react-redux';
import { userJoined, userJoinedAck, userLeft, messageReceived } from './actions/index';
import { bindActionCreators } from 'redux';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Row, Col } from 'reactstrap';

class App extends Component {
  constructor() {
    super();
    this.state = {
      modalOpen: sessionStorage.getItem("username") == null || sessionStorage.getItem("username") == "" ? true : false,
      nickname: sessionStorage.getItem("nickname"),
      usernameInput: sessionStorage.getItem("username")
    }
  }

  componentDidMount() {
    if (!this.state.modalOpen) {
      this.registerSocket();
    }
  }

  render() {
    return (
      <>
        <MuiThemeProvider>
          <div className="App">
            {this.state.modalOpen ? <Login /> :
              <Row>
                <Col xs="3">
                  <UserList users={this.state.users} />
                </Col>
                <Col xs="6">
                  <Chat />
                </Col>
              </Row>
            }
          </div >
        </MuiThemeProvider >
      </>
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
    messageReceived: messageReceived
  }, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(App);