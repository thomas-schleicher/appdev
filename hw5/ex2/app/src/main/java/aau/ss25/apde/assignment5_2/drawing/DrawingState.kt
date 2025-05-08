package aau.ss25.apde.assignment5_2.drawing

import aau.ss25.apde.assignment5_2.drawing.DrawnPath
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Path

/**
 * Holds the drawing state for the gesture-based drawing canvas.
 *
 * This class manages the brush size, the list of completed paths,
 * and the current in-progress path during a drawing gesture.
 */
class DrawingState {

    /**
     * The current brush size used for new strokes.
     *
     * Changing this value only affects future paths.
     * Each completed path stores the brush size it was drawn with.
     */
    var brushSize by mutableFloatStateOf(8f)

    /**
     * A list of all finished paths drawn on the canvas,
     * each with its own stroke width.
     */
    val paths = mutableStateListOf<DrawnPath>()

    /**
     * The active path currently being drawn.
     *
     * This is updated as the user drags their finger across the canvas.
     * Once the gesture ends, it is copied to [paths] and reset.
     */
    val currentPath = Path()
}

/**
 * Remembers and returns a [DrawingState] instance scoped to the composition.
 *
 * This ensures the state persists across recompositions, so drawing is continuous
 * and not reset when the UI updates.
 *
 * @return A stable instance of [DrawingState] remembered by Compose.
 */
@Composable
fun rememberDrawingState(): DrawingState {
    return remember { DrawingState() }
}