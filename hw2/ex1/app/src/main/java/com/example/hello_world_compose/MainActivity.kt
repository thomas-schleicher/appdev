package com.example.hello_world_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hello_world_compose.ui.theme.Hello_World_ComposeTheme

class MainActivity : ComponentActivity() { // Start Activity
    override fun onCreate(savedInstanceState: Bundle?) { // first Method called when starting running
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // content does not overlap system bars
        /*
        setContent {
            Hello_World_ComposeTheme { // predefined Theme
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> // Layout-Container (topBar, bottomBar, content, ...), that fills out the whole screen
                    Greeting( // Creating the UI Composable "Greeting"
                        name = stringResource(id = R.string.world),
                        modifier = Modifier.padding(innerPadding) // Layout-
                    )
                }
            }
        }*/


        setContentView(R.layout.activity_main) // sets layout of the Activity as activity_main.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets -> // sets an Inset (system bar space) by getting the Root-View
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()) // And the insets of the system bars (sizes of the edges)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom) // sets these spacings in the view
            insets
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) { // Composable with 1 input and
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true) // Preview of the Composable Greeting
@Composable
fun GreetingPreview() {
    Hello_World_ComposeTheme {
        Greeting("World")
    }
}