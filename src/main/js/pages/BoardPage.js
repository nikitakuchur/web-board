import React, {Component} from 'react';
import '../../css/board.css';
import ToolBar from "../components/ToolBar";
import Board from "../components/Board";
import {Brush} from "../tools/Brush";
import {BoardContext} from "../contexts/BoardContext";
import ErrorModal from "../modals/ErrorModal";

class BoardPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: {
                error: false,
                description: "",
            },
            tool: new Brush(),
            color: "#000000",
            setColor: this.setColor,
        }
        this.boardRef = React.createRef();
        this.toolBarRef = React.createRef();
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

    handleToolChange = (tool) => {
        this.setState({tool: tool});
    }

    render() {
        return (
            <div style={{touchAction: 'none', overscrollBehavior: 'none'}}>
                <BoardContext.Provider value={this.state}>
                    <Board ref={this.boardRef} id={this.props.match.params.id} tool={this.state.tool}
                           onError={this.handleError}/>
                    <ToolBar ref={this.toolBarRef} onClearButtonClick={this.handleClearButtonClick}
                             onToolUpdate={this.handleToolChange}/>
                    <ErrorModal show={this.state.error.error} description={this.state.error.description}/>
                </BoardContext.Provider>
            </div>
        );
    }
}

BoardPage.contextType = BoardContext;

export default BoardPage;
