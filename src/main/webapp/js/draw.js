let canvas, ctx,
    brush = {
        color: '#000000',
        size: 10,
        down: false,
    },
    strokes = [],
    currentStroke = null;

function init() {
    canvas = $('#draw');
    ctx = canvas[0].getContext('2d');

    function updateSize() {
        canvas.attr({
            width: window.innerWidth,
            height: window.innerHeight
        });
        draw();
    }
    $(window).resize(updateSize).resize();

    initWebSocket();
    onMessage(data => {
        if (data.clear) {
            strokes = [];
        }
        if (data.strokes != null) {
            strokes = strokes.concat(data.strokes);
        }
        draw();
    });

    function mouseEvent(e) {
        if (currentStroke != null) {
            if (e.pageX === undefined) {
                // It needs for mobile devices
                currentStroke.points.push({
                    x: e.originalEvent.touches[0].pageX,
                    y: e.originalEvent.touches[0].pageY,
                });
            } else {
                currentStroke.points.push({
                    x: e.pageX,
                    y: e.pageY,
                });
            }
            draw();
        }
    }

    canvas.on('mousedown touchstart', function (e) {
        brush.down = true;
        currentStroke = {
            color: brush.color,
            size: brush.size,
            points: [],
        };
        strokes.push(currentStroke);
        mouseEvent(e);
    });

    canvas.on('mouseup touchend', function () {
        sendStrokesMessage([currentStroke]);
        brush.down = false;
        currentStroke = null;
    });

    canvas.on('mousemove touchmove', function (e) {
        if (brush.down) {
            mouseEvent(e);
        }
    });

    $('#clear-button').click(function () {
        sendClearMessage();
        strokes = [];
        draw();
    });

    $('#slider').change(function () {
        brush.size = $(this).val();
    });

    $('#color').change(function () {
        brush.color = $(this).val();
    })
}

function draw() {
    ctx.clearRect(0, 0, canvas.width(), canvas.height());
    ctx.lineCap = 'round';
    ctx.lineJoin = 'round';
    for (let stroke of strokes) {
        ctx.strokeStyle = stroke.color;
        ctx.lineWidth = stroke.size;
        ctx.beginPath();
        ctx.moveTo(stroke.points[0].x, stroke.points[0].y);
        for (let point of stroke.points) {
            ctx.lineTo(point.x, point.y)
        }
        ctx.stroke();
    }
}

$(init);
