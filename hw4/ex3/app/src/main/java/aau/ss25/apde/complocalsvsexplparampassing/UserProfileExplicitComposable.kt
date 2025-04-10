package aau.ss25.apde.complocalsvsexplparampassing

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun UserProfileExplicit(name: String, address: String, phoneNumber: String) {
    Column {
        Text("User Profile")

        // explicitly give the style info
        UserDetailsExplicit(name, address, phoneNumber, 1.0f, TextStyle.Default)

        // pros:
        //      clarity what styling is given
        // cons:
        //      too much parameters in calls
        //      not nice to look at (in my opinion)
        GreetingExplicit(name, 0.3f, TextStyle(color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 16.sp))
    }
}

@Composable
fun UserDetailsExplicit(name: String, address: String, phoneNumber: String, alpha: Float, style: TextStyle) {
    NameExplicit(name, alpha, style)
    AddressExplicit(address, alpha, style)
    PhoneNumberExplicit(phoneNumber, alpha, style)
}

@Composable
fun NameExplicit(name: String, alpha: Float, style: TextStyle) {
    Text(text = name, style = style, modifier = Modifier.alpha(alpha))
}

@Composable
fun AddressExplicit(address: String, alpha: Float, style: TextStyle) {
    Text(text = address, style = style, modifier = Modifier.alpha(alpha))
}

@Composable
fun PhoneNumberExplicit(phone: String, alpha: Float, style: TextStyle) {
    Text(text = phone, style = style, modifier = Modifier.alpha(alpha))
}

@Composable
fun GreetingExplicit(name: String, alpha: Float, style: TextStyle) {
    Text(text = "Welcome $name!", style = style, modifier = Modifier.alpha(alpha))
}
