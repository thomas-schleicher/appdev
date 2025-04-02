package at.aau.appdev.chatapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class Message(
    val sender: String,
    val text: String,
    val date: String,
    val isSender: Boolean
)

@Composable
fun ChatBubble(message: Message) {
    val backgroundColor = if (message.isSender) Color.hsl(219f, 0.45f, 0.39f) else Color.hsl(355f, 0.76f, 0.36f)
    val alignment = if (message.isSender) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = alignment
    ) {
        Column(
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(10.dp))
                .padding(8.dp)
                .widthIn(max = 250.dp)
        ) {
            Text(text = message.sender, color = Color.White, textAlign = TextAlign.Start)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = message.text, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = message.date, color = Color.White, style = MaterialTheme.typography.labelSmall)
        }
    }
}
