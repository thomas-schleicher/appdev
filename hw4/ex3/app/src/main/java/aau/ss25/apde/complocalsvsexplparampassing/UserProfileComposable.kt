package aau.ss25.apde.complocalsvsexplparampassing

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun UserProfile(name: String, address: String, phoneNumber: String) {
    Column {
        Text("User Profile")

        // pros:
        //      elegant
        UserDetails(name, address, phoneNumber)

        // set the global variables to different values, only in that block
        CompositionLocalProvider(
            // provides -> temporarily assigns CompositionLocal a new value
            LocalTextAlpha provides 0.3f,
            LocalTextStyle provides TextStyle(color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        ) {
            Greeting(name) // changes apply only for greeting
        }
    }
}

@Composable
fun UserDetails(name: String, address: String, phoneNumber: String) {
    Name(name)
    Address(address)
    PhoneNumber(phoneNumber)
}

@Composable
fun Name(name: String) {
    // LocalTextStyle.current -> styles the text with the current set CompositionLocal
    Text(text = name, style = LocalTextStyle.current, modifier = Modifier.alpha(LocalTextAlpha.current))
}

@Composable
fun Address(address: String) {
    Text(text = address, style = LocalTextStyle.current, modifier = Modifier.alpha(LocalTextAlpha.current))
}

@Composable
fun PhoneNumber(phone: String) {
    Text(text = phone, style = LocalTextStyle.current, modifier = Modifier.alpha(LocalTextAlpha.current))
}

@Composable
fun Greeting(name: String) {
    Text(text = "Welcome $name!", style = LocalTextStyle.current, modifier = Modifier.alpha(LocalTextAlpha.current))
}
