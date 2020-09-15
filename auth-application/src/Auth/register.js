import React, { Component } from 'react';
import { Button, Form, FormGroup, Input } from 'reactstrap';
import { toast } from 'react-toastify';
import '../assets/login.scss';
import '../App.css';

export default class Register extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: ""
        }
    }

    login() {
        var data = {
            username: this.state.username,
            password: this.state.password,
        };
        fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(res => {
                if (res.status == 400) {
                    toast('Username or Password is wrong')
                }
                if (res.status == 200) {
                    res.json().then(data => {
                        sessionStorage.setItem("nickname", data.nickname);
                        sessionStorage.setItem("username", data.username);
                    })
                    window.location.reload(false);
                }
            })
            .catch(console.log)
    }

    onChangeName(e) {
        this.setState({ username: e.target.value })
    }

    onChangePassword(e) {
        this.setState({ password: e.target.value })
    }

    render() {
        return (
            <div className="App">Đây là register</div>
        );
    }
};
