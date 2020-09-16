import React, { Component } from 'react';
import { connect } from 'react-redux';
import ListItem from '@material-ui/core/ListItem';
import './UserList.scss'
import {
    Card, CardBody,
    CardTitle, Button, Row
} from 'reactstrap';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';

class UserList extends Component {
    constructor() {
        super()
    }

    render() {
        if (!this.props.users || this.props.users.length < 1) {
            return '';
        }

        return (
            <div className="left-menu">
                <div style={{ textAlign: "center", fontSize: "30px", fontWeight: "bold" }}>Online List</div>
                <ListItem textAlign="center">
                    <div style={{ display: "block" }}>
                        {this.renderUserList()}
                    </div>
                </ListItem>
            </div>
        );
    }

    renderUserList() {
        var userList = [];
        for (var item of this.props.users) {
            userList.push(
                <Row>
                    <Card style={{ width: "100%" }}>
                        <CardBody >
                            <CardTitle style={{ display: "inline-flex" }}>
                                <h4 style={{ marginRight: "80%" }}>{item.nickname}</h4>
                                <CheckCircleIcon color="inherit" style={{ marginTop: "6%", color: "#7FFF00" }} />
                            </CardTitle>
                            {item.username == this.props.thisUser.username ? <Button onClick={this.props.onLogout()} style={{ width: "100%" }}>Logout</Button> : <></>}
                        </CardBody>
                    </Card>
                </Row>
            )
        }
        return userList;
    }
}

function mapStateToProps(state) {
    return {
        users: state.users,
        thisUser: state.thisUser
    }
}

export default connect(mapStateToProps)(UserList);
