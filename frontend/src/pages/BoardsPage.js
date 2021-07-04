import React, {Component} from 'react';
import {Card, Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import NewBoardModal from "../modals/NewBoardModal";
import ReactLoading from 'react-loading';
import BoardPreview from "../components/BoardPreview";

class BoardsPage extends Component {

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
        fetch("/api/rest/boards", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(
            res => {
                if (res.status === 403) {
                    // Go to login page if something is wrong
                    document.location.href = "/login";
                    return;
                }
                res.json().then(res => this.setState({boards: res, loading: false}));
            }
        );
    }

    addBoard(board) {
        fetch("/api/rest/boards", {
                method: "POST",
                body: JSON.stringify(board),
                headers: {
                    "Content-Type": "application/json"
                }
            }
        ).then(() => this.getBoards());
    }

    removeBoard(board) {
        fetch("/api/rest/boards", {
                method: "DELETE",
                body: JSON.stringify(board),
                headers: {
                    "Content-Type": "application/json"
                }
            }
        ).then(() => this.getBoards());
    }

    card(board) {
        return (
            <Card key={board.id} style={{width: '350px', minHeight: '300px', margin: '10px'}}>
                <Card.Body style={{
                    padding: '14px',
                    display: 'flex',
                    justifyContent: 'space-between',
                    flexDirection: 'column'
                }}>
                    <Card.Title style={{display: 'flex', justifyContent: 'space-between'}}>
                        <h5 className="m-auto">{board.name}</h5>
                        <Button variant="light"
                                onClick={() => {
                                    this.removeBoard(board);
                                }}>×</Button>
                    </Card.Title>
                    <Card.Text>{board.description}</Card.Text>
                    <BoardPreview strokes={board.strokes}/>
                    <Link className={"mt-4"} to={{pathname: '/boards/' + board.id}}
                          style={{justifyContent: 'flex-end', display: 'flex'}}>
                        <Button variant="primary">Go to board</Button>
                    </Link>
                </Card.Body>
            </Card>
        )
    }

    handleLogOutButtonClick = () => {
        console.log("Log Out");
        fetch("/api/rest/auth/logout", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                }
            }
        ).then(() => document.location.href = "/login");
    }

    render() {
        let boards = [];
        for (let board of this.state.boards) {
            boards.push(this.card(board));
        }

        return (
            <React.Fragment>
                <div className={"m-2"} style={{display: 'flex'}}>
                    <h4>Boards</h4>
                    <Button style={{marginLeft: 'auto'}} onClick={this.handleLogOutButtonClick}>Log Out</Button>
                </div>
                <div style={{display: 'flex', flexWrap: 'wrap'}}>
                    {boards}
                    <Button variant="outline-primary" style={{width: '350px', minHeight: '308px', margin: '10px'}}
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

export default BoardsPage;
