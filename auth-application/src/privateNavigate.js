import React, { Component } from 'react';
import { BrowserRouter as Router, Redirect } from 'react-router-dom';

class PrivateNavigate extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: sessionStorage.getItem('username'),
        };
    }

    render() {
        var { username } = this.state;
        return (
            username
                ? <Redirect to='/chatApp' />
                : <Redirect to='/login' />
        )
    }
}

export default PrivateNavigate;