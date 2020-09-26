export class Brush {

    constructor() {
        this.color = "black";
        this.size = 10;
        this.pressed = false;
        this.currentStroke = null;
    }

    down(board, x, y) {
        this.pressed = true;
        this.currentStroke = {
            id: null,
            color: this.color,
            size: this.size,
            points: [],
        };
        board.strokes.push(this.currentStroke);
        this.mouseEvent(board, x, y);
    }

    up(board, x, y) {
        sendStrokesMessage([this.currentStroke]);
        this.pressed = false;
        this.currentStroke = null;
    }

    move(board, x, y) {
        if (this.pressed) {
            this.mouseEvent(board, x, y);
        }
    }

    mouseEvent(board, x, y) {
        if (this.currentStroke != null) {
            this.currentStroke.points.push({
                x: x,
                y: y,
            });
        }
        board.draw();
    }
}
