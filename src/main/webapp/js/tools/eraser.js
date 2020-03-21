export class Eraser {

    constructor() {
        this.size = 10;
        this.pressed = false;
    }

    mouseEvent(board, x, y) {
        for (let stroke of board.strokes.values()) {
            let point1 = stroke.points[0];
            for (let i = 0; i < stroke.points.length; i++) {
                let point2 = stroke.points[i];
                if (this.getMinDistance(point1, point2, x, y) < (this.size + stroke.size) / 2) {
                    sendDeleteMessage(stroke.id);
                    board.strokes.delete(stroke.id);
                }
                point1 = stroke.points[i];
            }
        }
        board.draw();
    }

    getMinDistance(point1, point2, x, y) {
        let t = (x - point1.x) * (point2.x - point1.x) + (y - point1.y) * (point2.y - point1.y);
        t /= Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2);
        t = t < 0 || isNaN(t) ? 0 : t;
        t = t > 1 ? 1 : t;
        let l = Math.pow(point1.x - x + t * (point2.x - point1.x), 2) + Math.pow(point1.y - y + t * (point2.y - point1.y), 2);
        return Math.sqrt(l);
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