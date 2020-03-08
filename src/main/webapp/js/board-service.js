function clearBoard() {
    return $.ajax({
        type: 'POST',
        url: 'board',
        data: {clear: true},
    });
}

function addStroke(stroke) {
    return $.ajax({
        type: 'POST',
        url: 'board',
        data: {stroke: JSON.stringify(stroke)},
        dataType: 'json',
    });
}

function getStrokes() {
    return $.ajax('board');
}