import React, {Component} from 'react';
import {Card, Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import NewBoardModal from "../modals/NewBoardModal";

class MainPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            boards: [],
            showNewBoardModal: false,
        }
    }

    card(board) {
        return (
            <React.Fragment key={board.id}>
                <Card style={{width: '18rem'}}>
                    <Card.Body style={{padding: '14px'}}>
                        <Card.Title>{board.name}</Card.Title>
                        <Card.Text>{board.description}</Card.Text>
                        <Link to={{pathname: '/board'}}>
                            <Button variant="secondary">Go to board</Button>
                        </Link>
                    </Card.Body>
                </Card>
            </React.Fragment>
        )
    }

    render() {
        let boards = [];
        for (let board of this.state.boards) {
            boards.push(this.card(board))
        }

        return (
            <React.Fragment>
                <h4>Main page</h4>
                <Button variant="primary" onClick={() => {
                    this.setState({showNewBoardModal: true})
                }}>New</Button>
                <div style={{display: 'flex', flexWrap: 'wrap', width: '100vh'}}>
                    <Card style={{width: '18rem'}}>
                        <Card.Body style={{padding: '14px'}}>
                            <Card.Title>Board</Card.Title>
                            <Card.Text>The board description</Card.Text>
                            <Link to={{pathname: '/board'}}>
                                <Button variant="secondary">Go to board</Button>
                            </Link>
                        </Card.Body>
                    </Card>
                    {boards}
                </div>
                <NewBoardModal show={this.state.showNewBoardModal}
                               onOkButtonClick={(board) => {
                                   board.id = this.state.boards.length;
                                   this.state.boards.push(board);
                                   this.setState({showNewBoardModal: false});
                               }}
                               onCancelButtonClick={() => {
                                   this.setState({showNewBoardModal: false});
                               }}
                />
            </React.Fragment>);
    }
}

export default MainPage;
