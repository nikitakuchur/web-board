import React, {Component} from 'react';
import {Brush} from "../tools/Brush";
import Websocket from "react-websocket";

export default class Board extends Component {
    static defaultProps = {
        tool: new Brush(),
    }

    constructor(props) {
        super(props);
        this.canvasRef = React.createRef();
        this.contextRef = React.createRef();
        this.strokes = [];
        this.webSocketRef = React.createRef();
    }

    componentDidMount() {
        this.contextRef = this.canvasRef.current.getContext('2d');
        window.addEventListener("resize", () => {
            this.canvasRef.current.width = window.innerWidth;
            this.canvasRef.current.height = window.innerHeight;
            this.draw();
        });
    }

    addStroke(stroke) {
        this.strokes.push(stroke);
    }

    removeStrokeById(id) {
        let i = -1;
        do {
            i++;
            if (i >= this.strokes.length) {
                return;
            }
        } while (this.strokes[i].id !== id)
        this.removeStrokeByIndex(i);
    }

    removeStrokeByIndex(index) {
        this.strokes.splice(index, 1);
    }

    clear() {
        this.sendClearMessage();
        this.strokes = [];
        this.draw();
    }

    getStrokes() {
        return this.strokes.slice();
    }

    handleMouseDown = e => {
        this.props.tool.down(this, e.pageX, e.pageY);
    }

    handleTouchStart = e => {
        this.props.tool.down(this, e.touches[0].pageX, e.touches[0].pageY);
    }

    handleMouseUp = e => {
        this.props.tool.up(this, e.pageX, e.pageY);
    }

    handleTouchEnd = e => {
        this.props.tool.up(this, e.pageX, e.pageY);
    }

    handleMouseMove = e => {
        this.props.tool.move(this, e.pageX, e.pageY);
    }

    handleTouchMove = e => {
        this.props.tool.move(this, e.touches[0].pageX, e.touches[0].pageY);
    }

    handleMessage = (data) => {
        let result = JSON.parse(data);
        console.log(result);
        if (result.clear) {
            this.strokes = [];
        }
        if (result.strokes.length > 0) {
            // If id of the last stroke is null, then we got the stroke that we just send
            if (this.strokes.length > 0 && this.strokes[this.strokes.length - 1].id == null) {
                // And we need to assign the received id
                this.strokes[this.strokes.length - 1].id = result.strokes[0].id;
            } else {
                result.strokes.forEach(value => {
                    this.strokes.push(value);
                });
            }
        }
        if (result.deleted !== -1) {
            this.removeStrokeById(result.deleted);
        }
        this.draw();
    }

    sendStrokesMessage(strokes) {
        let message = JSON.stringify({
            strokes: strokes,
            deleted: -1,
            clear: false,
        });
        this.webSocketRef.current.sendMessage(message);
    }

    sendDeleteMessage(deleted) {
        let message = JSON.stringify({
            strokes: [],
            deleted: deleted,
            clear: false,
        });
        this.webSocketRef.current.sendMessage(message);
    }

    sendClearMessage() {
        let message = JSON.stringify({
            strokes: [],
            deleted: -1,
            clear: true,
        });
        this.webSocketRef.current.sendMessage(message);
    }

    draw() {
        this.contextRef.clearRect(0, 0, this.canvasRef.current.width, this.canvasRef.current.height);
        this.contextRef.lineCap = 'round';
        this.contextRef.lineJoin = 'round';

        for (let stroke of this.strokes) {
            this.contextRef.strokeStyle = stroke.color;
            this.contextRef.lineWidth = stroke.size;
            this.contextRef.beginPath();
            this.contextRef.moveTo(stroke.points[0].x, stroke.points[0].y);

            for (let point of stroke.points) {
                this.contextRef.lineTo(point.x, point.y)
            }
            this.contextRef.stroke();
        }
    }

    render() {
        let protocol = location.protocol === 'https:' ? "wss://" : "ws://";
        let url = protocol + location.host + "/api/board-endpoint";
        return (
            <React.Fragment>
                <canvas ref={this.canvasRef} id="canvas" width={window.innerWidth} height={window.innerHeight}
                        onMouseDown={this.handleMouseDown}
                        onTouchStart={this.handleTouchStart}
                        onMouseUp={this.handleMouseUp}
                        onTouchEnd={this.handleTouchEnd}
                        onMouseMove={this.handleMouseMove}
                        onTouchMove={this.handleTouchMove}/>
                <Websocket ref={this.webSocketRef} url={url} onMessage={this.handleMessage}/>
            </React.Fragment>
        );
    }
}
