import React, { Component } from 'react';
import { Card, CardTitle, CardSubtitle } from 'reactstrap';
import DescriptionIcon from '@material-ui/icons/Description';
import { connect } from 'react-redux';
import './ChatHistory.scss';

class ChatHistory extends Component {

    render() {
        var messages = [];
        const style = {
            backgroundColor: '#eaeaea',
            padding: 15,
            height: '420px',
            overflowY: 'scroll',
            display: 'flex',
            flexDirection: 'column'
        };

        messages.push(...this.props.messages);
        messages.push(...this.props.fileMessages);

        const msgs = messages.map((message, i) =>
            this.renderMessages(message, i)
        );

        return (
            <div style={style}>
                {msgs}
            </div>
        )
    }

    renderMessages(message, i) {
        const style = {
            display: 'block',
            margin: '5px 0'
        };

        var fileSize = 0;

        const isMe = this.props.thisUser.username === message.user.username;
        const floatDirection = isMe ? 'right' : 'left'
        const nameColor = isMe ? 'green' : 'red';
        const margin = isMe ? ' 0 0 0 40px' : '0 40px 0 0 ';

        const textStyle = {
            float: floatDirection,
            backgroundColor: '#fff',
            padding: '6px 10px',
            borderRadius: '15px',
            margin: margin,
            textAlign: 'left'
        }

        const nameStyle = {
            color: nameColor,
            float: floatDirection
        }

        if (message.filemodel) {
            var fileSize = this.bytesToMegaBytes(message.filemodel.size).toFixed(2)
        }

        return (
            <div key={i} style={style}>
                <span style={textStyle}>
                    <span style={nameStyle}>{message.user.nickname}</span>
                    <br />
                    {message.filemodel ?
                        <Card className="image-hover" onClick={() => this.downloadFile(message.filemodel.url)}>
                            <DescriptionIcon fontSize="large" />
                            <CardTitle style={{ color: "#66B2FF" }}  >{message.filemodel.fileName}</CardTitle>
                            <CardSubtitle style={{ color: "#66B2FF" }} >{fileSize}MB</CardSubtitle>
                        </Card>
                        : message.data
                    }
                </span>
            </div>
        );
    }

    downloadFile(url) {
        window.open(url);
    }

    bytesToMegaBytes(bytes) {
        return bytes / (1024 * 1024);
    }

}


function mapStateToProps(state) {
    return {
        messages: state.messages,
        thisUser: state.thisUser,
        fileMessages: state.fileMessages
    }
}

export default connect(mapStateToProps)(ChatHistory);



