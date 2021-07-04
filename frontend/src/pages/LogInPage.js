import React, {Component} from 'react';
import {Button, Form} from "react-bootstrap";
import {Link} from "react-router-dom";

class LogInPage extends Component {

    constructor(props) {
        super(props);
        this.nameRef = React.createRef();
        this.passwordRef = React.createRef();
        this.state = {incorrect: false};
    }

    handleLogInButtonClick = () => {
        const logInInfo = {
            name: this.nameRef.current.value,
            password: this.passwordRef.current.value,
        };
        fetch("/api/rest/auth/login", {
                method: "POST",
                body: JSON.stringify(logInInfo),
                headers: {
                    "Content-Type": "application/json"
                }
            }
        ).then(res => res.json())
            .then(res => {
                if (res.result === true) {
                    document.location.href = "/boards";
                } else {
                    this.setState({incorrect: true})
                }
            });
    }

    handleKeyDown = e => {
        if (e.key === 'Enter') {
            this.handleLogInButtonClick();
        }
    }

    render() {
        let message = null;
        if (this.state.incorrect) {
            message = <p style={{color: 'red', textAlign: 'center', marginTop: '10px'}}>This combination of name and password is incorrect!</p>;
        }
        return (
            <React.Fragment>
                <h4 className={"ml-2 mt-2"}>Log In page</h4>
                <div className={"m-2"} style={{width: '300px'}}>
                    <Form.Control ref={this.nameRef} className="mb-2" placeholder="Name" onKeyDown={this.handleKeyDown}/>
                    <Form.Control ref={this.passwordRef} type="password" className="mb-2" placeholder="Password" onKeyDown={this.handleKeyDown}/>
                    <Button style={{width: '100%'}} onClick={this.handleLogInButtonClick}>Log In</Button>
                    <Link style={{display: 'block', textAlign: 'center', marginTop: '10px'}} to='/signup'>Sign Up</Link>
                    {message}
                </div>
            </React.Fragment>);
    }
}

export default LogInPage;
