import React, {Component} from 'react';
import {Brush} from "../tools/Brush";
import {Eraser} from "../tools/Eraser";

export default class BoardPage extends Component {
    static defaultProps = {
        onClearButtonClick: () => {
        },
        onToolUpdate: () => {
        },
    }

    tools = {
        brush: new Brush(),
        eraser: new Eraser(),
    }

    constructor(props) {
        super(props);
        this.state = {
            tool: new Brush(),
        };
    }

    handleToolChange = (event) => {
        let tool = this.tools[event.target.value];
        this.setState({tool: tool});
        this.props.onToolUpdate(tool);
    }

    handleBrushSizeChange = (event) => {
        let tool = this.state.tool;
        tool.size = Number(event.target.value);
        this.setState({tool: tool});
        this.props.onToolUpdate(tool);
    };

    handleBrushColorChange = (event) => {
        let tool = this.state.tool;
        if (tool.color !== undefined) {
            tool.color = event.target.value;
            this.setState({tool: tool});
            this.props.onToolUpdate(tool);
        }
    };

    render() {
        let colorPicker;
        if (this.state.tool.color !== undefined) {
            colorPicker = (
                <select id="color-picker" onChange={this.handleBrushColorChange}>
                    <option value="black">Black</option>
                    <option value="red">Red</option>
                    <option value="green">Green</option>
                    <option value="blue">Blue</option>
                </select>
            );
        }
        return (
            <div id="tool-bar">
                <button id="clear-button" onClick={this.props.onClearButtonClick}>Clear</button>
                <select id="tool-select" onChange={this.handleToolChange}>
                    <option value="brush">Brush</option>
                    <option value="eraser">Eraser</option>
                </select>
                <input id="size-slider" type="range" min="1" max="50"
                       value={this.state.tool.size !== undefined ? this.state.tool.size : 10}
                       onChange={this.handleBrushSizeChange}/>
                {colorPicker}
            </div>
        );
    }
}
