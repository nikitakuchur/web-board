export class Canvas {

    constructor() {
        this.canvas = $('#canvas');
        this.ctx = this.canvas[0].getContext('2d');
        this.strokes = [];

        let updateSize = () => {
            this.canvas.attr({
                width: window.innerWidth,
                height: window.innerHeight
            });
            this.draw();
        };
        $(window).resize(updateSize).resize();

        onMessage(data => {
            if (data.clear) {
                this.strokes = [];
            }
            if (data.strokes != null) {
                this.strokes = this.strokes.concat(data.strokes);
            }
            this.draw();
        });
    }

    setTool(tool) {
        // Down
        this.canvas.on('mousedown', e => {
            tool.down(this, e.pageX, e.pageY);
        });

        this.canvas.on('touchstart', e => {
            tool.down(this, e.originalEvent.touches[0].pageX, e.originalEvent.touches[0].pageY);
        });

        // Up
        this.canvas.on('mouseup', e => {
            tool.up(this, e.pageX, e.pageY);
        });

        this.canvas.on('touchend', e => {
            tool.up(this, e.pageX, e.pageY);
        });

        // Move
        this.canvas.on('mousemove', e => {
            tool.move(this, e.pageX, e.pageY);
        });

        this.canvas.on('touchmove', e => {
            tool.move(this, e.originalEvent.touches[0].pageX, e.originalEvent.touches[0].pageY);
        });
    }

    draw() {
        this.ctx.clearRect(0, 0, this.canvas.width(), this.canvas.height());
        this.ctx.lineCap = 'round';
        this.ctx.lineJoin = 'round';

        for (let stroke of this.strokes) {
            this.ctx.strokeStyle = stroke.color;
            this.ctx.lineWidth = stroke.size;
            this.ctx.beginPath();
            this.ctx.moveTo(stroke.points[0].x, stroke.points[0].y);

            for (let point of stroke.points) {
                this.ctx.lineTo(point.x, point.y)
            }
            this.ctx.stroke();
        }
    }

    clear() {
        sendClearMessage();
        this.strokes = [];
        this.draw();
    }
}
