import {Canvas} from './canvas.js'
import {Brush} from './tools/brush.js'

function init() {
    let canvas = new Canvas();
    let tool = new Brush();
    canvas.setTool(tool);

    $('#clear-button').click(function () {
        canvas.clear();
    });

    $('#slider').change(function () {
        tool.size = $(this).val();
    });

    $('#color').change(function () {
        tool.color = $(this).val();
    });
}

$(init);
