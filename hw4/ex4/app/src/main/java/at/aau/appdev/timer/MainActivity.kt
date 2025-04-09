package at.aau.appdev.timer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.aau.appdev.timer.ui.theme.TimerTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimerTheme {
                Timer()
            }
        }
    }
}

@Preview
@Composable
fun Timer() {
    val tag = "TimerApplication"
    var timeLeft by remember { mutableIntStateOf(10) }
    var isRunning by remember { mutableStateOf(false) }
    var resetKey by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = isRunning, key2 = resetKey) {
        if (isRunning) {
            Log.d(tag, "Timer started")
            while (timeLeft > 0 && isRunning) {
                delay(1000L)
                timeLeft -= 1
            }
            if (timeLeft <= 0) {
                isRunning = false
                Log.d(tag, "Timer finished")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timeLeft.toString(),
            style = MaterialTheme.typography.displayLarge
        )

        Row {
            Button(
                onClick = {
                    isRunning = !isRunning
                },
                enabled = (timeLeft > 0)
            ) {
                Text(if (isRunning) "Pause" else "Start")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    timeLeft = 10
                    isRunning = false
                    resetKey++
                }
            ) {
                Text("Reset")
            }
        }
    }
}
