export class Brush {

    constructor() {
        this.size = 10;
        this.color = "black";
        this.pressed = false;
        this.currentStroke = null;
    }

    down(board, x, y) {
        this.pressed = true;
        this.currentStroke = {
            id: null,
            size: this.size,
            color: this.color,
            points: [],
        };
        board.addStroke(this.currentStroke);
        this.mouseEvent(board, x, y);
    }

    up(board, x, y) {
        board.sendStrokesMessage([this.currentStroke]);
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