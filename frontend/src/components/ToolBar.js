import React, {Component} from 'react';
import {Image, Form, ButtonGroup, ToggleButton, OverlayTrigger, Tooltip} from "react-bootstrap";
import ColorPicker from "./ColorPicker";
import RangeSlider from 'react-bootstrap-range-slider';

class ToolBar extends Component {
    static defaultProps = {
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

    handleToolChange = (event) => {
        let value = event.target.value;
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
            <OverlayTrigger
                key={name}
                placement="bottom"
                overlay={<Tooltip id="button-tooltip">{this.capitalizeFirstLetter(name)}</Tooltip>}>
                <ToggleButton variant="light"
                              type="checkbox"
                              value={name}
                              checked={this.state.tool === this.props.tools[name]}
                              onChange={this.handleToolChange}>
                    <Image width="24px" src={tool.icon}/>
                </ToggleButton>
            </OverlayTrigger>
        );
    }

    capitalizeFirstLetter(str) {
        return str.charAt(0).toUpperCase() + str.slice(1)
    }

    render() {
        let toolButtons = [];
        for (const [key, value] of Object.entries(this.props.tools)) {
            toolButtons.push(this.toolButton(key, value));
        }
        return (
            <Form inline>
                <Form.Group className={"mr-1"}>
                    <ButtonGroup toggle value="brush">
                        {toolButtons}
                    </ButtonGroup>
                </Form.Group>
                <Form.Group className={"mr-1"}>
                    <ColorPicker color={this.props.color} onChange={this.props.onColorUpdate}/>
                </Form.Group>
                {this.state.tool.size !== undefined ?
                    (<RangeSlider min={1} max={50}
                                  tooltipLabel={(value) => "Size: " + value}
                                  value={this.state.tool.size}
                                  onChange={this.handleBrushSizeChange}/>) : null}
            </Form>
        );
    }
}

export default ToolBar;
