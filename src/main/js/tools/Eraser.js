export class Eraser {

    constructor() {
        this.size = 10;
        this.pressed = false;
    }

    mouseEvent(board, x, y) {
        for (let [index, stroke] of board.getStrokes().entries()) {
            let point1 = stroke.points[0];
            for (let i = 0; i < stroke.points.length; i++) {
                let point2 = stroke.points[i];
                if (this.calcMinDistance(point1, point2, x, y) < (this.size + stroke.size) / 2) {
                    board.sendDeleteMessage(stroke.id);
                    board.removeStrokeByIndex(index);
                    board.draw();
                    return;
                }
                point1 = stroke.points[i];
            }
        }
        board.draw();
    }

    calcMinDistance(point1, point2, x, y) {
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
