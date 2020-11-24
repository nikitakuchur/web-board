import React, {Component} from 'react';
import {Button, Modal, Form} from "react-bootstrap";

class NewBoardModal extends Component {
    static defaultProps = {
        onOkButtonClick: (board) => {
        },
        onCancelButtonClick: () => {
        },
    };

    constructor(props) {
        super(props);
        this.nameRef = React.createRef();
        this.descriptionRef = React.createRef();
    }

    handleOkButtonClick() {
        let board = {
            name: this.nameRef.current.value,
            description: this.descriptionRef.current.value,
        }
        this.props.onOkButtonClick(board);
    }

    render() {
        return (
            <Modal
                show={this.props.show}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                onHide={this.props.onCancelButtonClick}
                onKeyDown={e => {
                    if (e.key === 'Enter') {
                        this.handleOkButtonClick();
                    }
                }}>
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        New board
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form.Control ref={this.nameRef} className="mb-2" placeholder="Name"/>
                    <Form.Control ref={this.descriptionRef} as="textarea" rows={2} placeholder="Description"/>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.handleOkButtonClick}>Ok</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default NewBoardModal;
