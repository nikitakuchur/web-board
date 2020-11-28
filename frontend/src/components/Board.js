import React, {Component} from 'react';
import Websocket from "react-websocket";
import {BoardContext} from "../contexts/BoardContext";
import ReactLoading from 'react-loading';
import tinycolor from "tinycolor2";

class Board extends Component {
    static defaultProps = {
        onError: () => {
        },
    }

    constructor(props) {
        super(props);
        this.state = {
            loading: true,
        }
        this.canvasRef = React.createRef();
        this.strokes = [];
        this.webSocketRef = React.createRef();
    }

    componentDidMount() {
        window.addEventListener("resize", () => {
            if (this.canvasRef.current != null) {
                this.canvasRef.current.width = window.innerWidth;
                this.canvasRef.current.height = window.innerHeight;
                this.draw();
            }
        });
    }

    componentDidUpdate() {
        this.draw();
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
        if (this.context.tool.pressed !== true) {
            this.context.tool.down(this, e.pageX, e.pageY);
        }
    }

    handleTouchStart = e => {
        if (this.context.tool.pressed === false) {
            this.context.tool.down(this, e.touches[0].pageX, e.touches[0].pageY);
        }
    }

    handleMouseUp = e => {
        this.context.tool.up(this, e.pageX, e.pageY);
    }

    handleTouchEnd = e => {
        this.context.tool.up(this, e.pageX, e.pageY);
    }

    handleMouseMove = e => {
        this.context.tool.move(this, e.pageX, e.pageY);
    }

    handleTouchMove = e => {
        this.context.tool.move(this, e.touches[0].pageX, e.touches[0].pageY);
    }

    handleMessage = (data) => {
        let result = JSON.parse(data);
        console.log(result);
        if (result.error) {
            this.handleError(result);
            return;
        }
        this.handleBoardMessage(result);
        this.setState({loading: false});
    }

    handleError = (error) => {
        console.log(error.description);
        this.props.onError(error);
    }

    handleBoardMessage = (message) => {
        if (message.clear) {
            this.strokes = [];
        }
        if (message.strokes.length > 0) {
            // If id of the last stroke is null, then we got the stroke that we just send
            if (this.strokes.length > 0 && this.strokes[this.strokes.length - 1].id == null) {
                // And we need to assign the received id
                this.strokes[this.strokes.length - 1].id = message.strokes[0].id;
            } else {
                message.strokes.forEach(value => {
                    if (value != null) {
                        this.strokes.push(value);
                    }
                });
            }
        }
        if (message.deleted !== undefined && message.deleted !== null) {
            this.removeStrokeById(message.deleted);
        }
        this.draw();
    }

    sendStrokesMessage(strokes) {
        let message = JSON.stringify({
            strokes: strokes,
            deleted: null,
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
            deleted: null,
            clear: true,
        });
        this.webSocketRef.current.sendMessage(message);
    }

    draw() {
        const context = this.canvasRef.current.getContext('2d');
        context.clearRect(0, 0, this.canvasRef.current.width, this.canvasRef.current.height);
        context.lineCap = 'round';
        context.lineJoin = 'round';

        for (let stroke of this.strokes) {
            context.strokeStyle = tinycolor(stroke.color).toString();
            context.lineWidth = stroke.size;
            context.beginPath();
            context.moveTo(stroke.points[0].x, stroke.points[0].y);

            for (let point of stroke.points) {
                context.lineTo(point.x, point.y)
            }
            context.stroke();
        }
    }

    render() {
        let protocol = location.protocol === 'https:' ? "wss://" : "ws://";
        let url = protocol + location.host + "/api/board-endpoint/" + this.props.id;
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
                {this.state.loading ? <ReactLoading className={"loading"}
                                                    type={"spin"}
                                                    color={"#bdbdbd"}
                                                    height={'8%'}
                                                    width={'8%'}/> : null}
            </React.Fragment>
        );
    }
}

Board.contextType = BoardContext;

export default Board;
