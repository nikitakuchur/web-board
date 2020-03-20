import {Board} from './board.js'
import {Brush} from './tools/brush.js'

function init() {
    let board = new Board();
    let tool = new Brush();
    board.setTool(tool);

    $('#clear-button').click(function () {
        board.clear();
    });

    $('#slider').change(function () {
        tool.size = $(this).val();
    });

    $('#color').change(function () {
        tool.color = $(this).val();
    });
}

$(init);
