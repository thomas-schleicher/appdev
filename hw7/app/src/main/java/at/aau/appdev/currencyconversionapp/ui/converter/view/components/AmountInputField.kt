package at.aau.appdev.currencyconversionapp.ui.converter.view.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

/**
 * A text input field for entering the amount to be converted.
 *
 * This field allows only numerical input (including decimals) and uses
 * a numeric keyboard by default. It is typically used within a currency
 * converter form to let the user specify how much they want to convert.
 *
 * @param amount The current value entered by the user.
 * @param onAmountChange Callback invoked when the user changes the input.
 * @param modifier Optional [Modifier] for styling and layout customization.
 */
@Composable
fun AmountInputField(
    amount: String,
    onAmountChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = amount,
        onValueChange = onAmountChange,
        label = { Text("Amount") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}
