package aau.ss25.apde.complocalsvsexplparampassing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserProfileScreen() {
    val name = "John Doe"
    val address = "123 Main Street"
    val phoneNumber = "555-123-4567"

    Column(
        modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        UserProfile(name, address, phoneNumber)

        Spacer(modifier = Modifier.height(32.dp))

        UserProfileExplicit(name, address, phoneNumber)
    }
}
