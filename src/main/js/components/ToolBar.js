import React, {Component} from 'react';
import {Brush} from "../tools/Brush";
import {Eraser} from "../tools/Eraser";
import {Image, Button, Form, ToggleButtonGroup, ToggleButton} from "react-bootstrap";
import ColorPicker from "./ColorPicker";
import RangeSlider from 'react-bootstrap-range-slider';
import {Pipette} from "../tools/Pipette";
import {BoardContext} from "../contexts/BoardContext";

import clearIcon from '../../img/clear.png';
import brushIcon from '../../img/brush.png';
import pipetteIcon from '../../img/pipette.png';
import eraserIcon from '../../img/eraser.png';

class ToolBar extends Component {
    static defaultProps = {
        onClearButtonClick: () => {
        },
        onToolUpdate: () => {
        },
    };

    tools = {
        brush: new Brush(),
        pipette: new Pipette(),
        eraser: new Eraser(),
    }

    constructor(props) {
        super(props);
        this.state = {
            tool: new Brush(),
            color: "#000000",
        };
    }

    handleToolChange = (value) => {
        let tool = this.tools[value];
        if (tool.color !== undefined) {
            tool.color = this.state.color;
        }
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

    render() {
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
                        <ToggleButton variant="light" value="brush">
                            <Image width="24px" src={brushIcon}/>
                        </ToggleButton>
                        <ToggleButton variant="light" value="pipette">
                            <Image width="24px" src={pipetteIcon}/>
                        </ToggleButton>
                        <ToggleButton variant="light" value="eraser">
                            <Image width="24px" src={eraserIcon}/>
                        </ToggleButton>
                    </ToggleButtonGroup>
                </Form.Group>
                <Form.Group className="mr-1">
                    <ColorPicker color={this.context.color} onChange={this.context.setColor}/>
                </Form.Group>
                {this.state.tool.size !== undefined ?
                    (<RangeSlider min="1" max="50"
                                  value={this.state.tool.size}
                                  onChange={this.handleBrushSizeChange}/>) : null}
            </Form>
        );
    }
}

ToolBar.contextType = BoardContext;

export default ToolBar;
