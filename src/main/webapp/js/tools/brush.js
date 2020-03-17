export class Brush {

    constructor(color = 'black', size = 10) {
        this.color = color;
        this.size = size;
        this.pressed = false;
        this.currentStroke = null;
    }

    mouseEvent(canvas, x, y) {
        if (this.currentStroke != null) {
            this.currentStroke.points.push({
                x: x,
                y: y,
            });
        }
        canvas.draw();
    }

    down(canvas, x, y) {
        this.pressed = true;
        this.currentStroke = {
            color: this.color,
            size: this.size,
            points: [],
        };
        canvas.strokes.push(this.currentStroke);
        this.mouseEvent(canvas, x, y);
    }

    up(canvas, x, y) {
        sendStrokesMessage([this.currentStroke]);
        this.pressed = false;
        this.currentStroke = null;
    }

    move(canvas, x, y) {
        if (this.pressed) {
            this.mouseEvent(canvas, x, y);
        }
    }
}
