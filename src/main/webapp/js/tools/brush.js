export class Brush {

    constructor(color = 'black', size = 10) {
        this.color = color;
        this.size = size;
        this.pressed = false;
        this.currentStroke = null;
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

    down(board, x, y) {
        this.pressed = true;
        board.lastId++;
        this.currentStroke = {
            id: board.lastId,
            color: this.color,
            size: this.size,
            points: [],
        };
        board.strokes.set(board.lastId, this.currentStroke);
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
}
