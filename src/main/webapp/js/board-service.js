let ws;

function initWebSocket() {
    let protocol = location.protocol === 'https:' ? "wss://" : "ws://";
    ws = new WebSocket(protocol + location.host  + location.pathname + "board-endpoint");
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
        strokes: strokes
    });
    ws.send(message);
}

function sendDeleteMessage(deleted) {
    let message = JSON.stringify({
        deleted: deleted
    });
    ws.send(message);
}

function sendClearMessage() {
    let message = JSON.stringify({
        clear: true
    });
    ws.send(message);
}
