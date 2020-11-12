import React, {Component} from 'react';
import '../css/board.css';
import ToolBar from "../components/ToolBar";
import Board from "../components/Board";
import {BoardContext} from "../contexts/BoardContext";
import ErrorModal from "../modals/ErrorModal";
import {Brush} from "../tools/Brush";
import {Pipette} from "../tools/Pipette";
import {Eraser} from "../tools/Eraser";

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
            color: "#000000",
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
                    <ToolBar ref={this.toolBarRef}
                             tools={this.tools}
                             onClearButtonClick={this.handleClearButtonClick}
                             color={this.state.color}
                             onToolUpdate={(tool) => this.setState({tool: tool})}
                             onColorUpdate={this.state.setColor}
                    />
                    <ErrorModal show={this.state.error.error} description={this.state.error.description}
                                onCancelButtonClick={() => document.location.href = "/"}/>
                </BoardContext.Provider>
            </div>
        );
    }
}

BoardPage.contextType = BoardContext;

export default BoardPage;
