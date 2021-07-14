import React, {Component} from 'react';
import Websocket from "react-websocket";
import ReactLoading from 'react-loading';
import {Button, Form} from "react-bootstrap";
import Draggable from "react-draggable";

class Chat extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            messages: [],
        }
        this.messageRef = React.createRef();
        this.webSocketRef = React.createRef();
    }

    handleSendButtonClick = () => {
        let message = JSON.stringify({
            user: null,
            text: this.messageRef.current.value,
        });
        this.webSocketRef.current.sendMessage(message);
        this.messageRef.current.value = '';
    }

    handleKeyUp = e => {
        if (e.key === 'Enter') {
            this.handleSendButtonClick();
        }
    }

    handleMessage = (message) => {
        console.log(message);
        const msg = JSON.parse(message);
        if (msg.user !== "info") {
            this.state.messages.push(msg);
        }
        this.setState({loading: false, messages: this.state.messages});
    }

    scrollToBottom = () => {
        this.messagesEnd.scrollIntoView({ behavior: "smooth" });
    }

    componentDidMount() {
        this.scrollToBottom();
    }

    componentDidUpdate() {
        this.scrollToBottom();
    }

    render() {
        let protocol = location.protocol === 'https:' ? "wss://" : "ws://";
        let url = protocol + location.host + "/api/chat-endpoint/" + this.props.id;

        let messages = [];
        for (const [i, message] of this.state.messages.entries()) {
            messages.push(<p style={{margin: 0}} key={i}>{message.user}: {message.text}</p>);
        }

        return (
            <Draggable>
                <div style={{
                    position: 'absolute', right: '4px', bottom: '44px', background: '#f4f5f7',
                    padding: '10px', border: '1px solid rgba(0,0,0,.125)', borderRadius: '.25rem'
                }}>
                    <h6 style={{textAlign: 'center'}}>Chat</h6>
                    <div style={{
                        background: 'white', border: '1px solid rgba(0,0,0,.125)', borderRadius: '.25rem',
                        padding: '10px', height: '300px', overflow: 'auto'
                    }}>
                        {messages}
                        <div style={{ float:"left", clear: "both" }}
                             ref={(el) => { this.messagesEnd = el; }}>
                        </div>
                    </div>
                    <div style={{display: 'flex', marginTop: '10px'}}>
                        <Form.Control ref={this.messageRef} as="textarea" rows={1} placeholder="Message" onKeyUp={e => this.handleKeyUp(e)}/>
                        <Button style={{marginLeft: '10px'}} onClick={this.handleSendButtonClick}>Send</Button>
                    </div>
                    <Websocket ref={this.webSocketRef} url={url} onMessage={this.handleMessage}/>
                    {this.state.loading ? <ReactLoading className={"loading"}
                                                        type={"spin"}
                                                        color={"#bdbdbd"}
                                                        height={'10%'}
                                                        width={'10%'}/> : null}
                </div>
            </Draggable>
        );
    }
}

export default Chat;
