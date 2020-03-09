function clearBoard() {
    return $.ajax({
        type: 'POST',
        url: 'board-servlet',
        data: 'clear',
    });
}

function addStroke(stroke) {
    return $.ajax({
        type: 'POST',
        url: 'board-servlet',
        data: {stroke: JSON.stringify(stroke)},
        dataType: 'json',
    });
}

function getStrokes() {
    return $.ajax('board-servlet');
}