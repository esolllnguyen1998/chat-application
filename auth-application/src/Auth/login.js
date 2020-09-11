import React, { Component } from 'react';
import { Button, Form, FormGroup, Label, Input, FormText } from 'reactstrap';
import { toast } from 'react-toastify';
import '../assets/login.scss';
import { Redirect } from "react-router-dom";

export default class Login extends Component {
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
                console.log(res.status)
                if (res.status == 400) {
                    toast('Username or Password is wrong')
                }
                if (res.status == 200) {
                    return <Redirect to="http://localhost:8080" />
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
            <Form className="login-form">
                <h2>Login</h2>
                <FormGroup style={{ width: "70%", marginLeft: "15%" }} >
                    <Input onChange={e => this.onChangeName(e)} type="text" name="username" id="username" placeholder="Enter your username" />
                </FormGroup>
                <FormGroup style={{ width: "70%", marginLeft: "15%" }} >
                    <Input onChange={e => this.onChangePassword(e)} type="password" name="password" id="examplePassword" placeholder="Enter your password" />
                </FormGroup>
                <Button onClick={() => this.login()} style={{ width: "45%" }} type="button">Login</Button>
                <br></br>
                <a href="#" style={{ fontSize: "20px" }}>Register</a>
            </Form>
        );
    }
};
