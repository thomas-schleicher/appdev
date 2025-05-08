package aau.ss25.apde.assignment5_2.drawing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput

/**
 * A composable that displays the interactive drawing canvas.
 *
 * Combines gesture handling and drawing logic in a full-screen box.
 * Delegates gesture detection to [Modifier.drawGestures] and rendering to [DrawingContent].
 *
 * @param modifier Modifier for layout and styling.
 * @param state The current drawing state, including the path and brush settings.
 */
@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    state: DrawingState
) {
    Box(
        modifier = modifier
            .background(Color.White)
            .drawGestures(state)
    ) {
        DrawingContent(state)
    }
}

/**
 * Renders all drawn paths and the currently active stroke onto a Canvas.
 *
 * This is used internally by [DrawingCanvas] to display the user's drawing.
 *
 * @param state The current drawing state with path history and brush settings.
 */
@Composable
fun DrawingContent(state: DrawingState) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // draw all drawn lines/paths
        state.paths.forEach { drawn ->
            drawPath(
                path = drawn.path,
                color = Color.Black,
                style = Stroke(width = drawn.strokeWidth)
            )
        }
        // draw current line/path
        drawPath(
            path = state.currentPath,
            color = Color.Black,
            style = Stroke(width = state.brushSize)
        )
    }
}

/**
 * A [Modifier] extension that adds gesture handling for drawing and resetting the canvas.
 *
 * - Drag gestures draw on the canvas using [Path].
 * - Long press clears all drawn paths.
 *
 * @param state The drawing state to update during gestures.
 * @return A modified [Modifier] with drag and long-press gesture detection applied.
 */
fun Modifier.drawGestures(state: DrawingState): Modifier = this
    .pointerInput(Unit) {
        detectTapGestures(
            // reset canvas, when long pressing on screen
            onLongPress = {
                state.paths.clear()
                state.currentPath.reset()
            }
        )
    }
    .pointerInput(Unit) {
        detectDragGestures(
            onDragStart = { offset -> // starting path, when and where finger touches canvas
                state.currentPath.moveTo(offset.x, offset.y) // moveTo position of finger, otherwise (0, 0)
            },
            onDrag = { change, _ -> // is called multiple times
                // adds a line to currentPath from last pos. to current
                // ensures that, on dragging a smooth line is drawn
                state.currentPath.lineTo(change.position.x, change.position.y)
            },
            onDragEnd = { // is called, when finger leaves canvas
                val finishedPath = Path().apply { addPath(state.currentPath) } // copy currentPath into new Path-Obj.
                // save it permanently in path list, incl. brushSize, so that it won't change on changing with Slider
                state.paths.add(DrawnPath(finishedPath, state.brushSize))
                state.currentPath.reset() // reset currentPath, to prepare it for the next stroke
            }
        )
    }