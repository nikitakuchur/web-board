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
            color: "#000000"
        }
        this.boardRef = React.createRef();
        this.toolBarRef = React.createRef();
    }

    handleClearButtonClick = () => {
        this.boardRef.current.clear();
    }

    handleToolChange = (tool) => {
        this.setState({tool: tool});
    }

    handleColorChange = (color) => {
        this.setState({color: color});
    }

    // TODO: Fix this!!!
    render() {
        return (
            <React.Fragment>
                <Board ref={this.boardRef} color={this.state.color}
                       setColor={(color) => this.toolBarRef.current.setColor(color)}
                       tool={this.state.tool}/>
                <ToolBar ref={this.toolBarRef} onClearButtonClick={this.handleClearButtonClick}
                         onToolUpdate={this.handleToolChange} onColorChange={this.handleColorChange}/>
            </React.Fragment>
        );
    }
}

export default BoardPage;
