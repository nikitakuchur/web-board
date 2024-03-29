import tinycolor from 'tinycolor2'
import pipetteIcon from '../img/pipette.png';

export class Pipette {

    constructor() {
        this.icon = pipetteIcon;
    }

    down(board, x, y) {
        let strokes = board.getStrokes();
        for (let index = strokes.length - 1; index >= 0; index--) {
            let stroke = strokes[index];
            let point1 = stroke.points[0];
            for (let i = 0; i < stroke.points.length; i++) {
                let point2 = stroke.points[i];
                if (this.calcMinDistance(point1, point2, x, y) < stroke.size / 2) {
                    board.context.setColor(tinycolor(stroke.color).toRgb());
                    return;
                }
                point1 = stroke.points[i];
            }
        }
        board.context.setColor({
            r: 255,
            g: 255,
            b: 255,
            a: 255,
        });
    }

    calcMinDistance(point1, point2, x, y) {
        let t = (x - point1.x) * (point2.x - point1.x) + (y - point1.y) * (point2.y - point1.y);
        t /= Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2);
        t = t < 0 || isNaN(t) ? 0 : t;
        t = t > 1 ? 1 : t;
        let l = Math.pow(point1.x - x + t * (point2.x - point1.x), 2) + Math.pow(point1.y - y + t * (point2.y - point1.y), 2);
        return Math.sqrt(l);
    }

    up(board, x, y) {
    }

    move(board, x, y) {
    }

    mouseEvent(board, x, y) {
    }
}
