import React, {Component} from 'react';
import '../css/board.css';
import {Button, Form, Image, OverlayTrigger, Tooltip} from "react-bootstrap";
import ToolBar from "../components/ToolBar";
import Board from "../components/Board";
import {BoardContext} from "../contexts/BoardContext";
import ErrorModal from "../modals/ErrorModal";
import {Brush} from "../tools/Brush";
import {Pipette} from "../tools/Pipette";
import {Eraser} from "../tools/Eraser";

import backIcon from '../img/back.png';
import clearIcon from '../img/clear.png';

class BoardPage extends Component {
    constructor(props) {
        super(props);
        this.boardRef = React.createRef();
        this.toolBarRef = React.createRef();
        this.tools = {
            brush: new Brush(),
            pipette: new Pipette(),
            eraser: new Eraser(),
        };
        this.state = {
            error: {
                error: false,
                description: "",
            },
            tool: this.tools.brush,
            color: {
                r: 0,
                g: 0,
                b: 0,
                a: 255,
            },
            setColor: this.setColor,
        }
    }

    handleError = (error) => {
        this.setState({error: error});
    }

    setColor = (color) => {
        this.setState({color: color});
    }

    handleClearButtonClick = () => {
        this.boardRef.current.clear();
    }

    render() {
        return (
            <div style={{touchAction: 'none', overscrollBehavior: 'none'}}>
                <BoardContext.Provider value={this.state}>
                    <Board ref={this.boardRef} id={this.props.match.params.id} onError={this.handleError}/>
                    <div className={"d-flex flex-row toolbar"}>
                        <Form.Group className={"ml-1 mr-1 mb-0"}>
                            <OverlayTrigger
                                placement="bottom"
                                overlay={<Tooltip id="button-tooltip">Back</Tooltip>}>
                                <Button variant="light"
                                        onClick={() => document.location.href = "/"}>
                                    <Image width="24px" src={backIcon}/>
                                </Button>
                            </OverlayTrigger>
                        </Form.Group>
                        <ToolBar ref={this.toolBarRef}
                                 tools={this.tools}
                                 onClearButtonClick={this.handleClearButtonClick}
                                 color={this.state.color}
                                 onToolUpdate={(tool) => this.setState({tool: tool})}
                                 onColorUpdate={this.state.setColor}
                        />
                    </div>
                    <OverlayTrigger
                        placement="top"
                        overlay={<Tooltip id="button-tooltip">Clear</Tooltip>}>
                        <Button className={"mr-1"} style={{position: 'absolute', right: 0, bottom: 0}}
                                variant="light"
                                onClick={this.handleClearButtonClick}>
                            <Image width="24px" src={clearIcon}/>
                        </Button>
                    </OverlayTrigger>
                    <ErrorModal show={this.state.error.error} description={this.state.error.description}
                                onCancelButtonClick={() => document.location.href = "/"}/>
                </BoardContext.Provider>
            </div>
        );
    }
}

BoardPage.contextType = BoardContext;

export default BoardPage;
