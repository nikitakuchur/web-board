export class Eraser {

    constructor(size = 10) {
        this.size = size;
        this.pressed = false;
    }

    mouseEvent(board, x, y) {
        for (let stroke of board.strokes.values()) {
            for (let point of stroke.points) {
                let a = point.x - x;
                let b = point.y - y;
                console.log(Math.sqrt(a * a + b * b));
                if (Math.sqrt(a * a + b * b) < this.size) {
                    sendDeleteMessage(stroke.id);
                    board.strokes.delete(stroke.id);
                }
            }
        }
        board.draw();
    }

    down(board, x, y) {
        this.pressed = true;
        this.mouseEvent(board, x, y);
    }

    up(board, x, y) {
        this.pressed = false;
    }

    move(board, x, y) {
        if (this.pressed) {
            this.mouseEvent(board, x, y);
        }
    }
}