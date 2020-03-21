export class Board {

    constructor() {
        this.canvas = $('#canvas');
        this.ctx = this.canvas[0].getContext('2d');
        this.strokes = new Map();
        this.lastId = -1;

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
                this.strokes = new Map();
                this.lastId = -1;
            }
            if (data.strokes != null) {
                data.strokes.forEach(value => {
                    this.strokes.set(value.id, value);
                    if (value.id > this.lastId) {
                        this.lastId = value.id;
                    }
                });
            }
            if (data.deleted !== -1) {
                this.strokes.delete(data.deleted);
            }
            this.draw();
        });
    }

    setTool(tool) {
        this.canvas.off();

        // Down
        this.canvas.on('mousedown', e => {
            tool.down(this, e.pageX, e.pageY);
        });

        this.canvas.on('touchstart', e => {
            tool.down(this, e.originalEvent.touches[0].pageX, e.originalEvent.touches[0].pageY);
        });

        // Up
        this.canvas.on('mouseup touchend', e => {
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

        for (let stroke of this.strokes.values()) {
            this.ctx.strokeStyle = stroke.color;
            this.ctx.lineWidth = stroke.size;
            this.ctx.beginPath();
            this.ctx.moveTo(stroke.points[0].x, stroke.points[0].y);

            for (let point of stroke.points) {
                this.ctx.lineTo(point.x, point.y)
            }
            this.ctx.stroke();
        }
        console.log(this.lastId);
    }

    clear() {
        sendClearMessage();
        this.strokes = new Map();
        this.lastId = -1;
        this.draw();
    }
}
