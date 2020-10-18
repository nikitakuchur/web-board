import React, {Component} from 'react';
import '../../css/board.css';
import ToolBar from "../components/ToolBar";
import Board from "../components/Board";
import {Brush} from "../tools/Brush";
import {BoardContext} from "../contexts/BoardContext";

class BoardPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tool: new Brush(),
            color: "#000000",
            setColor: this.setColor,
        }
        this.boardRef = React.createRef();
        this.toolBarRef = React.createRef();
    }

    setColor = (color) => {
        this.setState({color: color});
    }

    handleClearButtonClick = () => {
        this.boardRef.current.clear();
    }

    handleToolChange = (tool) => {
        this.setState({tool: tool});
    }

    render() {
        return (
            <BoardContext.Provider value={this.state}>
                <Board ref={this.boardRef} tool={this.state.tool}/>
                <ToolBar ref={this.toolBarRef} onClearButtonClick={this.handleClearButtonClick}
                         onToolUpdate={this.handleToolChange}/>
            </BoardContext.Provider>
        );
    }
}

BoardPage.contextType = BoardContext;

export default BoardPage;
