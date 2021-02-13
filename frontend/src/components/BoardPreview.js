import React, {Component} from 'react';
import tinycolor from "tinycolor2";

class BoardPreview extends Component {

    constructor(props) {
        super(props);
        this.canvasRef = React.createRef();
    }

    componentDidMount() {
        this.draw();
    }

    // TODO: Remove duplicated code
    draw() {
        const context = this.canvasRef.current.getContext('2d');
        let width = this.canvasRef.current.width;
        let height = this.canvasRef.current.height;
        const scale = width / 1920;

        context.clearRect(0, 0, width, height);
        context.lineCap = 'round';
        context.lineJoin = 'round';

        for (let stroke of this.props.strokes) {
            context.strokeStyle = tinycolor(stroke.color).toString();
            context.lineWidth = stroke.size * scale;
            context.beginPath();
            if (stroke.points.length > 0) {
                context.moveTo(stroke.points[0].x * scale, stroke.points[0].y * scale);
            }

            for (let point of stroke.points) {
                context.lineTo(point.x * scale, point.y * scale)
            }
            context.stroke();
        }
    }

    render() {
        return (
            <canvas ref={this.canvasRef}/>
        );
    }
}

export default BoardPreview;
