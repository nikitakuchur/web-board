import React, {Component} from 'react';
import '../../css/board.css';
import ToolBar from "../components/ToolBar";
import Board from "../components/Board";
import {Brush} from "../tools/Brush";

class BoardPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tool: new Brush(),
        }
        this.boardRef = React.createRef();
    }

    handleClearButtonClick = () => {
        this.boardRef.current.clear();
    }

    handleToolChange = (tool) => {
        this.setState({tool: tool});
    }

    render() {
        return (
            <React.Fragment>
                <Board ref={this.boardRef} tool={this.state.tool}/>
                <ToolBar onClearButtonClick={this.handleClearButtonClick} onToolUpdate={this.handleToolChange}/>
            </React.Fragment>
        );
    }
}

export default BoardPage;
