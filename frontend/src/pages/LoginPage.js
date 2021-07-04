import React, {Component} from 'react';
import {Button, Form} from "react-bootstrap";

class LoginPage extends Component {

    constructor(props) {
        super(props);
        this.nameRef = React.createRef();
        this.passwordRef = React.createRef();
    }

    handleLogInButtonClick = () => {
        const loginInfo = {name: this.nameRef.current.value, password: this.passwordRef.current.value};
        fetch("/api/rest/auth/login", {
                method: "POST",
                body: JSON.stringify(loginInfo),
                headers: {
                    "Content-Type": "application/json"
                }
            }
        ).then(() => document.location.href = "/boards");
    }

    handleKeyDown = e => {
        if (e.key === 'Enter') {
            this.handleLogInButtonClick();
        }
    }

    render() {
        return (
            <React.Fragment>
                <h4 className={"ml-2 mt-2"}>Login page</h4>
                <div className={"m-2"} style={{width: '300px'}}>
                    <Form.Control ref={this.nameRef} className="mb-2" placeholder="Name" onKeyDown={this.handleKeyDown}/>
                    <Form.Control ref={this.passwordRef} type="password" className="mb-2" placeholder="Password" onKeyDown={this.handleKeyDown}/>
                    <Button style={{float: 'right'}} onClick={this.handleLogInButtonClick}>Log In</Button>
                </div>
            </React.Fragment>);
    }
}

export default LoginPage;
