import React, {Component} from 'react';
import {Card, Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import NewBoardModal from "../modals/NewBoardModal";
import ReactLoading from 'react-loading';

class MainPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            boards: [],
            showNewBoardModal: false,
            loading: true,
        }
    }

    componentDidMount() {
        this.getBoards();
    }

    getBoards() {
        this.setState({loading: true});
        fetch("/api/boards", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => res.json())
            .then(
                (result) => {
                    this.setState({boards: result, loading: false});
                },
                (error) => {
                    console.error("Error! " + error)
                }
            )
    }

    addBoard(board) {
        fetch("/api/boards", {
                method: "POST",
                body: JSON.stringify(board),
                headers: {
                    "Content-Type": "application/json"
                }
            }
        ).then(
            () => {
                this.getBoards();
            },
            (error) => {
                console.error("Error! " + error)
            }
        )
    }

    removeBoard(board) {
        fetch("/api/boards", {
                method: "DELETE",
                body: JSON.stringify(board),
                headers: {
                    "Content-Type": "application/json"
                }
            }
        ).then(
            () => {
                this.getBoards();
            },
            (error) => {
                console.error("Error! " + error)
            }
        )
    }

    card(board) {
        return (
            <Card key={board.id} style={{width: '18rem', minHeight: '10rem', margin: '10px'}}>
                <Card.Body style={{
                    padding: '14px',
                    display: 'flex',
                    justifyContent: 'space-between',
                    flexDirection: 'column'
                }}>
                    <div>
                        <Card.Title style={{display: 'flex', justifyContent: 'space-between'}}>
                            <h5 className="m-auto">{board.name}</h5>
                            <Button variant="light"
                                    onClick={() => {
                                        this.removeBoard(board);
                                    }}>×</Button>
                        </Card.Title>
                        <Card.Text>{board.description}</Card.Text>
                    </div>
                    <Link to={{pathname: '/' + board.id}} style={{justifyContent: 'flex-end', display: 'flex'}}>
                        <Button variant="primary">Go to board</Button>
                    </Link>
                </Card.Body>
            </Card>
        )
    }

    render() {
        let boards = [];
        for (let board of this.state.boards) {
            boards.push(this.card(board));
        }

        return (
            <React.Fragment>
                <h4>Main page</h4>
                <div style={{display: 'flex', flexWrap: 'wrap'}}>
                    {boards}
                    <Button variant="outline-primary" style={{width: '18rem', minHeight: '10rem', margin: '10px'}}
                            onClick={() => {
                                this.setState({showNewBoardModal: true})
                            }}>
                        Add
                    </Button>
                </div>
                <NewBoardModal show={this.state.showNewBoardModal}
                               onOkButtonClick={(board) => {
                                   this.addBoard(board);
                                   this.setState({showNewBoardModal: false});
                               }}
                               onCancelButtonClick={() => {
                                   this.setState({showNewBoardModal: false});
                               }}
                />
                {this.state.loading ? <ReactLoading className={"loading"}
                                                    type={"spin"}
                                                    color={"#bdbdbd"}
                                                    height={'8%'}
                                                    width={'8%'}/> : null}
            </React.Fragment>);
    }
}

export default MainPage;
