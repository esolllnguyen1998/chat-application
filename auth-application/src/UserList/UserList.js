import React, { Component } from 'react';
import { connect } from 'react-redux';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import './UserList.scss'

class UserList extends Component {

    render() {
        if (!this.props.users || this.props.users.length < 1) {
            return '';
        }

        return (
            <div className="left-menu">
                <div style={{ textAlign: "center", fontSize: "30px" }}>Online List</div>
                <ListItem textAlign="center">
                    {this.renderUserList()}
                </ListItem>
            </div>
        );
    }

    renderUserList() {
        var userList = [];
        for (var item of this.props.users) {
            userList.push(<ListItemText primary={item.nickname} />)
        }
        return userList;
    }
}

function mapStateToProps(state) {
    return {
        users: state.users
    }
}

export default connect(mapStateToProps)(UserList);