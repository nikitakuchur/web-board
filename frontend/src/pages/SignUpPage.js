import React, {Component} from 'react';
import {Button, Form} from "react-bootstrap";
import {Link} from "react-router-dom";

class SignUpPage extends Component {

    constructor(props) {
        super(props);
        this.nameRef = React.createRef();
        this.passwordRef = React.createRef();
        this.state = {exists: false};
    }

    handleSignUpButtonClick = () => {
        const signUpInfo = {
            name: this.nameRef.current.value,
            password: this.passwordRef.current.value,
        };
        fetch("/api/rest/auth/signup", {
                method: "POST",
                body: JSON.stringify(signUpInfo),
                headers: {
                    "Content-Type": "application/json"
                }
            }
        ).then(res => res.json())
            .then(res => {
                if (res.result === true) {
                    document.location.href = "/login";
                } else {
                    this.setState({exists: true})
                }
            });
    }

    handleKeyDown = e => {
        if (e.key === 'Enter') {
            this.handleSignUpButtonClick();
        }
    }

    render() {
        let message = null;
        if (this.state.exists) {
            message = <p style={{color: 'red', textAlign: 'center', marginTop: '10px'}}>User with this name already
                exists!</p>;
        }
        return (
            <React.Fragment>
                <h4 className={"ml-2 mt-2"}>Sign Up page</h4>
                <div className={"m-2"} style={{width: '300px'}}>
                    <Form.Control ref={this.nameRef} className="mb-2" placeholder="Name"
                                  onKeyDown={this.handleKeyDown}/>
                    <Form.Control ref={this.passwordRef} type="password" className="mb-2" placeholder="Password"
                                  onKeyDown={this.handleKeyDown}/>
                    <Button style={{width: '100%'}} onClick={this.handleSignUpButtonClick}>Sign Up</Button>
                    <Link style={{display: 'block', textAlign: 'center', marginTop: '10px'}} to='/login'>Log In</Link>
                    {message}
                </div>
            </React.Fragment>);
    }
}

export default SignUpPage;
