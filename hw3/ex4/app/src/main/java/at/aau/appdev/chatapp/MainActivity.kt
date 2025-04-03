package at.aau.appdev.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.aau.appdev.chatapp.ui.theme.ChatAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val sampleMessages = listOf(
    Message("You", "Hey, how are you?", "10:00 AM", true),
    Message("Bob", "I'm good, how about you?", "10:01 AM", false),
    Message("You", "Doing well!", "10:02 AM", true)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppTheme {
                ChatScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview //allows for live preview during development (very nice)
@Composable
fun ChatScreen() {
    var messages by remember { mutableStateOf(sampleMessages) }
    var messageInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simple Chat App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(153, 188, 133)
                )
            )
        },
        containerColor = Color(237, 232, 220)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f) //has weight 1 to push the input to the bottom
                    .fillMaxWidth()
            ) {
                //populate the lazy column
                items(messages) { message ->
                    ChatBubble(message)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                TextField(
                    value = messageInput,
                    onValueChange = { messageInput = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text("Type a message...") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(250, 241, 230),
                        focusedContainerColor = Color(250, 241, 230),
                    )
                )
                FloatingActionButton(
                    onClick = {
                        if (messageInput.isNotBlank()) {
                            messages = messages + Message(
                                sender = "You",
                                text = messageInput,
                                date = getCurrentTimeString(),
                                isSender = true
                            )
                            messageInput = ""
                        }
                    },
                    containerColor = Color(153, 188, 133)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                }
            }
        }
    }
}

//Simple function to create a new time stamp when the message is created
fun getCurrentTimeString(): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date())
}