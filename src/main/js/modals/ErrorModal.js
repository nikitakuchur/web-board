import React, {Component} from 'react';
import {Button, Modal} from "react-bootstrap";

class ErrorModal extends Component {
    static defaultProps = {
        description: "Error",
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
                aria-labelledby="contained-modal-title-vcenter"
                centered
                onHide={this.props.onCancelButtonClick}>
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Error
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.props.description}
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onCancelButtonClick}>Ok</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default ErrorModal;
