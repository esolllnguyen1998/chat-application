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
            password: "",
            passwordRetype: "",
            nickname: "",
            email: "",
            fullname: ""
        }
    }

    onRegister() {

        if (this.state.fullname == "") {
            toast('Please type fullname.', { type: "error" })
        }
        else if (this.state.username == "") {
            toast('Please type username.', { type: "error" })
        }
        else if (this.state.username.length < 4) {
            toast('Username have to 4 characters.', { type: "error" })
        }
        else if (this.state.password == "") {
            toast('Please type password.', { type: "error" })
        }
        else if (this.state.password.length < 6) {
            toast('Passowrd have to 6 characters.', { type: "error" })
        }
        else if (this.state.passwordRetype == "") {
            toast('Please type RetypePassword.', { type: "error" })
        }
        else if (this.state.password != this.state.passwordRetype) {
            toast('RetypePassword is wrong.', { type: "error" })
        }
        else if (this.state.email == "") {
            toast('Please type email.', { type: "error" })
        }
        else if (this.state.email.indexOf("@") == 0) {
            toast('Email format is wrong.', { type: "error" })
        }
        else if (this.state.nickname == "") {
            toast('Please type nickname.', { type: "error" })
        }
        else {
            var data = {
                username: this.state.username,
                password: this.state.password,
                nickname: this.state.nickname,
                email: this.state.email,
                fullname: this.state.fullname,
            };

            fetch('http://localhost:8080/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            })
                .then(res => {
                    if (res.status == 200) {
                        toast('Register success', {
                            type: "success"
                        })
                    }
                    if (res.status == 400) {
                        toast('User is existing. If you have an account, you can login.', {
                            type: "error"
                        })
                    }
                })
                .catch(console.log)
        }
    }

    onChangeUsername(e) {
        this.setState({ username: e.target.value })
    }

    onChangePassword(e) {
        this.setState({ password: e.target.value })
    }

    onChangeRetypePassword(e) {
        this.setState({ passwordRetype: e.target.value })
    }

    onChangeNickname(e) {
        this.setState({ nickname: e.target.value })
    }

    onChangeEmail(e) {
        this.setState({ email: e.target.value })
    }
    onChangeFullname(e) {
        this.setState({ fullname: e.target.value })
    }

    render() {
        return (
            <Form className="login-form">
                <h2>Register</h2>
                <FormGroup style={{ width: "70%", marginLeft: "15%" }} >
                    <Input required onChange={(e) => this.onChangeFullname(e)} type="text" name="fullname" id="fullname" placeholder="Enter your Fullname" />
                </FormGroup>
                <FormGroup style={{ width: "70%", marginLeft: "15%" }} >
                    <Input required onChange={(e) => this.onChangeUsername(e)} type="text" name="username" id="username" placeholder="Enter your Username" />
                </FormGroup>
                <FormGroup style={{ width: "70%", marginLeft: "15%" }} >
                    <Input required onChange={(e) => this.onChangePassword(e)} type="password" name="password" id="examplePassword" placeholder="Enter your Password" />
                </FormGroup>
                <FormGroup style={{ width: "70%", marginLeft: "15%" }} >
                    <Input required onChange={(e) => this.onChangeRetypePassword(e)} type="password" name="password" id="passwordRetype" placeholder="Retype your Password" />
                </FormGroup>
                <FormGroup style={{ width: "70%", marginLeft: "15%" }} >
                    <Input required onChange={(e) => this.onChangeEmail(e)} type="email" name="Email" id="Email" placeholder="Enter your Email" />
                </FormGroup>
                <FormGroup style={{ width: "70%", marginLeft: "15%" }} >
                    <Input required onChange={(e) => this.onChangeNickname(e)} type="text" name="Nickname" id="Nickname" placeholder="Enter your Nickname" />
                </FormGroup>
                <Button onClick={() => this.onRegister()} style={{ width: "45%" }} type="button">Register</Button>
                <br></br>
                <a href="/login" style={{ fontSize: "20px" }}>Login</a>
            </Form>
        );
    }
};
