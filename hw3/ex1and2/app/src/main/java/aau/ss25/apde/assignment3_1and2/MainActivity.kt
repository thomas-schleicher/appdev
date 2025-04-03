package aau.ss25.apde.assignment3_1and2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import aau.ss25.apde.assignment3_1and2.ui.theme.Assignment3_1and2Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.SolidColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment3_1and2Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    Greeting(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}

// @Composable -> UI-Function: Jetpack Compose automatically calls this function again if something
// changes
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    // remember "remembers" the state, even when Compose freshly draws the UI-Part
    var showMessage by remember { mutableStateOf(true) }
    var textColor by remember { mutableStateOf(Color.Black) }
    var backgroundColor by remember { mutableStateOf(Color.White) }

    // EX2
    var isEnabled by remember { mutableStateOf(true) }
    var boxColor by remember { mutableStateOf(Color.Gray) }

    // Use Column to arrange items vertically
    Column( // like a vertical list, everything is drawn from top to bottom
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GreetingMessage(showMessage, textColor)

        Spacer(modifier = Modifier.height(16.dp)) // adds a vertical space of 16dp

        Row {
            ToggleMessageButton(showMessage, isEnabled) {
                showMessage = !showMessage
                isEnabled = false // EX2: Make the button disabled after clicking once
            }

            StyleButton(textColor, backgroundColor) {
                textColor = if (textColor == Color.Black) Color.Red else Color.Black
                backgroundColor = if (backgroundColor == Color.White) Color.LightGray else Color.White
            }

            ColorBox(boxColor) {
                boxColor = if (boxColor == Color.Gray) Color.Green else Color.Gray
            }
        }
    }
}

@Composable
fun GreetingMessage(showMessage: Boolean, textColor: Color) {
    if (showMessage) {
        // Display a text element with a default message ("Hello, Compose!").
        Text(
            text = "Hello, Compose!",
            // EX2: Enhance the text with a larger font, a custom color, and a background.
            color = textColor,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
                .background(Color.Yellow)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center) // centers within it's width / height
        )
    }
}

@Composable
fun ToggleMessageButton(showMessage: Boolean, isEnabled: Boolean, onClick: () -> Unit) {
    // Add a button below the text that changes the message when clicked
    // (show and hide, or "Hello, Compose!" and "Hello, YourName!").
    Button(
        onClick = onClick,
        // EX2: Enhance the button(s) with a background color, padding, and rounded corners.
        modifier = Modifier.padding(8.dp), // adds 8dp space from the inside (border - text)
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        enabled = isEnabled
    ) { // BC: Make the button text change as well
        Text(text = if (showMessage) "Hide Message" else "Show Message")
    }
}

@Composable
fun StyleButton(textColor: Color, backgroundColor: Color, onClick: () -> Unit) {
    // BC: Use Row to add a second button
    Button(
        // BC: The second button changes the text color and its background dynamically
        onClick = onClick,
        // EX2: Enhance the button(s) with a background color, padding, and rounded corners.
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        // apply background color
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Text("Change Style", color = textColor)
    }
}

@Composable
fun ColorBox(boxColor: Color, onClick: () -> Unit) {
    // EX2: Add a clickable box with a dashed border that changes its color when clicked.
    Box(
        modifier = Modifier
            .size(45.dp)
            .border(
                width = 2.dp,
                brush = SolidColor(Color.Black),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .background(boxColor)
    )
}

/**
 * Find out which units of measurement are supported in Android, and which ones should be used in
 * which use cases.
 *
 * +----------------------------------------------------------------------------------------------------------------------------------------+
 * | Unit       | Name                       | Use Case                                               | Compose Equivalent                  |
 * +----------------------------------------------------------------------------------------------------------------------------------------+
 * | dp         | Density-independent-Pixels | Layout dimensions (width, height, padding, margins)    | dp (e.g. 16.dp)                     |
 * | sp         | Scale-independent Pixels   | Font sizes (scales with user’s font settings)          | sp (e.g. 18.sp)                     |
 * | px         | Pixels                     | Not recommended for layout (depends on screen density) | Not used directly                   |
 * | %          | Percent                    | Only in special containers (e.g. ConstraintLayout)     | Use fillMaxWidth() or fillMaxSize() |
 * | in, mm, pt | Inch, Millimeter, Point    | Rarely used – for printing, maps, or physical size     | Not used in Compose                 |
 * +----------------------------------------------------------------------------------------------------------------------------------------+
 */
