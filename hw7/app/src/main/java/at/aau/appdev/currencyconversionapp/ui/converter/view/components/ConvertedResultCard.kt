package at.aau.appdev.currencyconversionapp.ui.converter.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Displays the result of a successful currency conversion inside a styled card.
 *
 * If the [convertedResult] is not empty, this composable shows a card
 * with a heading and the formatted converted value in large font.
 * Otherwise, it renders nothing.
 *
 * @param convertedResult The formatted result string of the currency conversion.
 */
@Composable
fun ConvertedResultCard(
    convertedResult: String
) {
    if (convertedResult.isNotEmpty()) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Converted Value:",
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = convertedResult,
                    fontSize = 32.sp
                )
            }
        }
    }
}
