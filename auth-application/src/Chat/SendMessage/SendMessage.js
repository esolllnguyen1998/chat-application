import React, { Component } from 'react';
import TextField from '@material-ui/core/TextField';
import RaisedButton from '@material-ui/core/Button';
import Singleton from '../../socket';
import MessageType from './MessageType';
import AttachFileIcon from '@material-ui/icons/AttachFile';
import ImageIcon from '@material-ui/icons/Image';
import SentimentVerySatisfiedIcon from '@material-ui/icons/SentimentVerySatisfied';
import { connect } from 'react-redux';
import { Row } from 'reactstrap';
import './SendMessage.scss';
import { toast } from 'react-toastify';
import { bindActionCreators } from 'redux';

class SendMessage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            inputValue: '',
            file: null,
            userName: sessionStorage.getItem("username"),
        }
        this.fileSelector = this.buildFileSelector();
    }

    render() {

        if (!this.props.thisUser) return '';

        const styles = {
            position: 'absolute',
            bottom: 0,
            width: '100%'
        };
        const fieldStyle = {
            width: '70%'
        };
        const btnStyles = {
            marginLeft: 25
        };

        return (
            <div style={styles}>
                <Row style={{ marginLeft: "5%" }}>
                    <AttachFileIcon onClick={this.handleFileSelect} className="image-hover" style={{ marginRight: "2%" }} />
                    <ImageIcon className="image-hover" style={{ marginRight: "2%" }} />
                    <SentimentVerySatisfiedIcon className="image-hover" style={{ marginRight: "2%" }} />
                </Row>
                <TextField
                    hintText="Write message here.."
                    fullWidth={true}
                    style={fieldStyle}
                    value={this.state.inputValue}
                    onChange={this.updateInputValue.bind(this)}
                    onKeyPress={this.handleKeyPress}
                    autoFocus
                />
                <RaisedButton style={btnStyles} onClick={this.sendMessage.bind(this)} > Send </RaisedButton>
            </div>
        );
    }

    handleFileSelect = (e) => {
        e.preventDefault();
        this.fileSelector.click();
    }

    handleKeyPress = (event) => {
        if (event.key === 'Enter' && this.state.inputValue != '') {
            this.sendMessage();
        }
    }

    buildFileSelector() {
        const fileSelector = document.createElement('input');
        fileSelector.setAttribute('type', 'file');
        fileSelector.setAttribute('multiple', 'multiple');
        fileSelector.addEventListener("input", (event) => {
            event.stopPropagation();
            event.preventDefault();
            var file = event.target.files[0];
            if (this.bytesToMegaBytes(file.size) > 5) {
                toast("Vui l�ng ch?n file nh? h?n 5mb.", { type: "warning" })
            }
            else {
                this.handleSaveFile(file)
            }
        })
        return fileSelector;
    }

    handleSaveFile(file) {
        let formData = new FormData();
        formData.append('file', file);
        formData.append('username', this.state.userName);
        fetch('http://localhost:8080/upload', {
            method: 'POST',
            body: formData,
        })
            .then(res => {
                if (res.status == 400) {
                    toast('Cannot upload.')
                }
                if (res.status == 200) {
                    res.json().then(data => {
                        this.sendFileMessage(data)
                    })
                }
            })
            .catch(console.log)
    }

    bytesToMegaBytes(bytes) {
        return bytes / (1024 * 1024);
    }

    sendFileMessage(fileModel) {
        const socket = Singleton.getInstance();
        let messageDto = JSON.stringify({ user: this.props.thisUser, data: "", type: MessageType.FILE_MESSAGE, filemodel: fileModel });
        socket.send(messageDto);
    }

    sendMessage() {
        const socket = Singleton.getInstance();
        let messageDto = JSON.stringify({ user: this.props.thisUser, data: this.state.inputValue, type: MessageType.TEXT_MESSAGE });
        socket.send(messageDto);
        this.setState({ inputValue: '' })
    }

    updateInputValue(evt) {
        this.setState({
            inputValue: evt.target.value
        })
    }
}

function mapStateToProps(state) {
    return {
        messages: state.messages,
        thisUser: state.thisUser
    }
}

export default connect(mapStateToProps)(SendMessage);



