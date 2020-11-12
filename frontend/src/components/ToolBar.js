import React, {Component} from 'react';
import {Image, Button, Form, ToggleButtonGroup, ToggleButton} from "react-bootstrap";
import ColorPicker from "./ColorPicker";
import RangeSlider from 'react-bootstrap-range-slider';
import {BoardContext} from "../contexts/BoardContext";

import clearIcon from '../img/clear.png';

class ToolBar extends Component {
    static defaultProps = {
        onClearButtonClick: () => {
        },
        onToolUpdate: () => {
        },
        onColorUpdate: () => {
        },
    };


    constructor(props) {
        super(props);
        this.state = {
            tool: this.props.tools.brush,
        };
    }

    handleToolChange = (value) => {
        let tool = this.props.tools[value];
        this.setState({tool: tool});
        this.props.onToolUpdate(tool);
    }

    handleBrushSizeChange = (event) => {
        let tool = this.state.tool;
        if (tool.size !== undefined) {
            tool.size = Number(event.target.value);
            this.setState({tool: tool});
            this.props.onToolUpdate(tool);
        }
    };

    toolButton(name, tool) {
        return (
            <ToggleButton variant="light" key={name} value={name}>
                <Image width="24px" src={tool.icon}/>
            </ToggleButton>
        );
    }

    render() {
        let toolButtons = [];
        for (const [key, value] of Object.entries(this.props.tools)) {
            toolButtons.push(this.toolButton(key, value));
        }
        return (
            <Form inline id="tool-bar">
                <Form.Group className="ml-1 mr-1">
                    <Button variant="light" id="clear-button"
                            onClick={this.props.onClearButtonClick}>
                        <Image width="24px" src={clearIcon}/>
                    </Button>
                </Form.Group>
                <Form.Group className="mr-1">
                    <ToggleButtonGroup type="radio" name="options" defaultValue="brush"
                                       onChange={this.handleToolChange}>
                        {toolButtons}
                    </ToggleButtonGroup>
                </Form.Group>
                <Form.Group className="mr-1">
                    <ColorPicker color={this.props.color} onChange={this.props.onColorUpdate}/>
                </Form.Group>
                {this.state.tool.size !== undefined ?
                    (<RangeSlider min={1} max={50}
                                  value={this.state.tool.size}
                                  onChange={this.handleBrushSizeChange}/>) : null}
            </Form>
        );
    }
}

ToolBar.contextType = BoardContext;

export default ToolBar;
