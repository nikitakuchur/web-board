import React, {Component} from 'react';
import {Brush} from "../tools/Brush";
import {Eraser} from "../tools/Eraser";
import {Button, Form} from "react-bootstrap";
import ColorPicker from "./ColorPicker";

class ToolBar extends Component {
    static defaultProps = {
        onClearButtonClick: () => {
        },
        onToolUpdate: () => {
        },
    };

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

    handleBrushColorChange = (color) => {
        let tool = this.state.tool;
        if (tool.color !== undefined) {
            tool.color = color;
            this.setState({tool: tool});
            this.props.onToolUpdate(tool);
        }
    };

    render() {
        return (
            <Form inline id="tool-bar">
                <Form.Group>
                    <Button variant="primary" id="clear-button"
                            onClick={this.props.onClearButtonClick}>Clear</Button>
                </Form.Group>
                <Form.Group>
                    <Form.Control as="select" id="tool-select" onChange={this.handleToolChange}>
                        <option value="brush">Brush</option>
                        <option value="eraser">Eraser</option>
                    </Form.Control>
                </Form.Group>
                <Form.Group>
                    <Form.Control type="range" id="size-slider" min="1" max="50"
                                  value={this.state.tool.size !== undefined ? this.state.tool.size : 10}
                                  onChange={this.handleBrushSizeChange}/>
                </Form.Group>
                {this.state.tool.color !== undefined ? <ColorPicker onChange={this.handleBrushColorChange}/> : null}
            </Form>
        );
    }
}

export default ToolBar;
