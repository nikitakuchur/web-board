import {Board} from './board.js'
import {Brush} from './tools/brush.js'
import {Eraser} from './tools/eraser.js'

function init() {
    const clearButton = $('#clear-button');
    const toolSelect = $('#tool-select');
    const sizeSlider = $('#size-slider');
    const colorPicker = $('#color-picker');

    const board = new Board();
    const tools = {Brush, Eraser};
    let tool = new tools[toolSelect.val()]();

    tool.size = parseFloat(sizeSlider.val());
    tool.color = colorPicker.val();
    board.setTool(tool);

    clearButton.click(function () {
        board.clear();
    });

    toolSelect.change(function () {
        tool = new tools[toolSelect.val()]();
        tool.size = parseFloat(sizeSlider.val());
        tool.color = colorPicker.val();
        board.setTool(tool);
    });

    sizeSlider.change(function () {
        tool.size = parseFloat($(this).val());
    });

    colorPicker.change(function () {
        tool.color = $(this).val();
    });
}

$(init);
