package aau.ss25.apde.assignment5_2.drawing

import androidx.compose.ui.graphics.Path

/**
 * Represents a single completed stroke on the canvas.
 *
 * Each instance holds the path geometry and the stroke width
 * that was used when the stroke was originally drawn.
 *
 * This allows strokes to retain their original appearance,
 * even if the brush size changes later.
 *
 * @property path The geometric path of the stroke.
 * @property strokeWidth The brush size used when drawing the stroke.
 */
data class DrawnPath(
    val path: Path,
    val strokeWidth: Float
)