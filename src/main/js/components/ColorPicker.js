import React from 'react';
import reactCSS from 'reactcss';
import {SketchPicker} from 'react-color';

class ColorPicker extends React.Component {
    static defaultProps = {
        color: "#000000",
        onChange: () => {
        },
    };

    constructor(props) {
        super(props);
        this.state = {
            displayColorPicker: false,
            color: this.props.color,
        };
    }

    handleClick = () => {
        this.setState({displayColorPicker: !this.state.displayColorPicker});
    };

    handleClose = () => {
        this.setState({displayColorPicker: false});
    };

    handleChange = (color) => {
        this.props.onChange(color.hex);
    };

    render() {
        const styles = reactCSS({
            'default': {
                color: {
                    width: '54px',
                    height: '28px',
                    borderRadius: '2px',
                    background: `${this.props.color}`,
                },
                swatch: {
                    padding: '5px',
                    background: '#fff',
                    borderRadius: '1px',
                    boxShadow: '0 0 0 1px rgba(0,0,0,.1)',
                    display: 'inline-block',
                    cursor: 'pointer',
                },
                popover: {
                    position: 'absolute',
                    zIndex: '2',
                },
                cover: {
                    position: 'fixed',
                    top: '0px',
                    right: '0px',
                    bottom: '0px',
                    left: '0px',
                },
            },
        });

        return (
            <React.Fragment>
                <div style={styles.swatch} onClick={this.handleClick}>
                    <div style={styles.color}/>
                </div>
                <div>
                    {this.state.displayColorPicker ? <div style={styles.popover}>
                        <div style={styles.cover} onClick={this.handleClose}/>
                        <SketchPicker color={this.props.color} onChange={this.handleChange}/>
                    </div> : null}
                </div>
            </React.Fragment>
        );
    }
}

export default ColorPicker;
