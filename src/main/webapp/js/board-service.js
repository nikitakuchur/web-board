let ws;

function initWebSocket() {
    ws = new WebSocket("ws://" + location.host  + location.pathname + "board-endpoint");
}

function closeWebSocket() {
    ws.close();
}

function onMessage(f) {
    ws.onmessage = function(event) {
        let message = JSON.parse(event.data);
        f(message);
    }
}

function sendStrokesMessage(strokes) {
    let message = JSON.stringify({
        strokes: strokes,
        deleted: -1,
        clear: false,
    });
    ws.send(message);
}

function sendDeleteMessage(deleted) {
    let message = JSON.stringify({
        strokes: null,
        deleted: deleted,
        clear: false,
    });
    ws.send(message);
}

function sendClearMessage() {
    let message = JSON.stringify({
        strokes: null,
        deleted: -1,
        clear: true,
    });
    ws.send(message);
}
