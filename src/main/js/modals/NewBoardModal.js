import React, {Component} from 'react';
import {Button, Modal, Form} from "react-bootstrap";

class NewBoardModal extends Component {
    static defaultProps = {
        onOkButtonClick: (name) => {
        },
        onCancelButtonClick: () => {
        },
    };

    constructor(props) {
        super(props);
        this.nameRef = React.createRef();
        this.descriptionRef = React.createRef();
    }

    render() {
        return (
            <Modal
                show={this.props.show}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered>
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        New board
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form.Control ref={this.nameRef} className="mb-1" placeholder="Name"/>
                    <Form.Control ref={this.descriptionRef} as="textarea" rows={2} placeholder="Description"/>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => {
                        let board = {
                            name: this.nameRef.current.value,
                            description: this.descriptionRef.current.value,
                        }
                        this.props.onOkButtonClick(board)
                    }}>Ok</Button>
                    <Button onClick={this.props.onCancelButtonClick}>Close</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default NewBoardModal;
