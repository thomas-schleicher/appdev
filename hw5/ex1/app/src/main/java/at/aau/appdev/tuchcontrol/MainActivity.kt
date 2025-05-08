package at.aau.appdev.tuchcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import at.aau.appdev.tuchcontrol.ui.theme.TuchControlTheme
import kotlin.math.hypot
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TuchControlTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CircleDistanceComponent(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CircleDistanceComponent(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val screenWidth = constraints.maxWidth.toFloat()
        val screenHeight = constraints.maxHeight.toFloat()

        var redCircle by remember { mutableStateOf(generateRandomOffset(screenWidth, screenHeight, 30f)) }
        var blueCircle by remember { mutableStateOf<Offset?>(null) }
        var distanceText by remember { mutableStateOf("") }
        Box(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { tapOffset ->
                        blueCircle = tapOffset
                        val distance = hypot(
                            tapOffset.x - redCircle.x,
                            tapOffset.y - redCircle.y
                        )
                        distanceText = "Distance: ${"%.1f".format(distance)} px"
                    },
                    onDoubleTap = { tapOffset ->
                        blueCircle?.let { blueCircle ->
                            redCircle = generateRandomOffset(screenWidth, screenHeight, 30f)
                            val distance = hypot(
                                blueCircle.x - redCircle.x,
                                blueCircle.y - redCircle.y
                            )
                            distanceText = "Distance: ${"%.1f".format(distance)} px"
                        } ?: run {
                            blueCircle = tapOffset
                            val distance = hypot(
                                tapOffset.x - redCircle.x,
                                tapOffset.y - redCircle.y
                            )
                            distanceText = "Distance: ${"%.1f".format(distance)} px"
                        }
                    },
                    onLongPress = { longPressOffset ->
                        redCircle = longPressOffset
                        blueCircle?.let { blueCircle ->
                            val distance = hypot(
                                blueCircle.x - redCircle.x,
                                blueCircle.y - redCircle.y
                            )
                            distanceText = "Distance: ${"%.1f".format(distance)} px"
                        } ?: run {
                            distanceText = ""
                        }
                    }
                )
            }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(Color.Red, radius = 30f, center = redCircle)
                blueCircle?.let { blueCircle ->
                    drawCircle(Color.Blue, radius = 30f, center = blueCircle)
                    drawLine(Color.Black, start = redCircle, end = blueCircle, strokeWidth = 5f)
                }
            }

            if (distanceText.isNotEmpty()) {
                Text(
                    text = distanceText,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        }
    }
}

fun generateRandomOffset(screenWidth: Float, screenHeight: Float, radius: Float): Offset {
    val x = Random.nextFloat() * (screenWidth - 2 * radius) + radius
    val y = Random.nextFloat() * (screenHeight - 2 * radius) + radius
    return Offset(x, y)
}