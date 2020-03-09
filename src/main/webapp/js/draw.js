let canvas, ctx,
    brush = {
        color: '#000000',
        size: 10,
        down: false,
    },
    strokes = [],
    currentStroke = null;

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

function clear() {
    clearBoard().done(function() {
        strokes = [];
        draw();
    });
}

function init() {
    canvas = $('#draw');
    ctx = canvas[0].getContext('2d');

    function updateSize() {
        canvas.attr({
            width: window.innerWidth,
            height: window.innerHeight
        });
    }
    $(window).resize(updateSize).resize();

    (function refresher() {
        getStrokes().done(function (data) {
            if (!brush.down) {
                strokes = JSON.parse(data);
                draw();
            }}).always(function () {
            setTimeout(refresher, 400);
        });
    })();

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

    canvas.on('mousedown touchstart', function(e) {
        brush.down = true;
        currentStroke = {
            color: brush.color,
            size: brush.size,
            points: [],
        };
        strokes.push(currentStroke);
        mouseEvent(e);
    });

    canvas.on('mouseup touchend', function() {
        addStroke(currentStroke).always(function () {
            brush.down = false;
        });
        currentStroke = null;
    });

    canvas.on('mousemove touchmove', function(e) {
        if (brush.down) {
            mouseEvent(e);
        }
    });

    $('#clear-button').click(function () {
        clear();
    });

    $('#slider').change(function() {
        brush.size = $(this).val()
    });
}

$(init);
