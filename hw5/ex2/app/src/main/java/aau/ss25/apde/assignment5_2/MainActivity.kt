package aau.ss25.apde.assignment5_2

import aau.ss25.apde.assignment5_2.drawing.GestureDrawingScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme

/**
 * NOTE: ChatGPT 4o was used to:
 *          - Get a general idea how this works
 *          - Suggestions how to make the project cleaner
 *          - Generating Documentation
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                GestureDrawingScreen()
            }
        }
    }
}