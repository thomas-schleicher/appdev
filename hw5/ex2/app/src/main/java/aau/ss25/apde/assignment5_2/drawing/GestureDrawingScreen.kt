package aau.ss25.apde.assignment5_2.drawing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * The main screen that combines the drawing canvas, top app bar, and brush size slider.
 *
 * Uses [Scaffold] to lay out a top bar, bottom bar (slider), and the full-screen drawing area.
 * Manages the shared drawing state via [rememberDrawingState].
 */
@Composable
fun GestureDrawingScreen() {
    val drawingState = rememberDrawingState()

    Scaffold(
        topBar = { DrawingTopBar() },
        bottomBar = {
            BrushSizeSlider(
                brushSize = drawingState.brushSize,
                onBrushSizeChange = { drawingState.brushSize = it }
            )
        }
    ) { padding ->
        DrawingCanvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = drawingState
        )
    }
}

/**
 * Displays a simple top app bar with the title of the screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingTopBar() {
    TopAppBar(
        title = { Text("Gesture Drawing") }
    )
}

/**
 * Displays a brush size slider with a label.
 *
 * This slider allows the user to select the stroke width used for future drawings.
 *
 * @param brushSize The current selected brush size.
 * @param onBrushSizeChange Callback to update the brush size.
 */
@Composable
fun BrushSizeSlider(
    brushSize: Float,
    onBrushSizeChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.6f))
            .padding(12.dp)
    ) {
        Text("Brush Size: ${brushSize.toInt()}")
        Slider(
            value = brushSize,
            onValueChange = onBrushSizeChange,
            valueRange = 1f..30f
        )
    }
}