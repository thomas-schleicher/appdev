package at.aau.appdev.currencyconversionapp.ui.log

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import at.aau.appdev.currencyconversionapp.domain.ConversionLogger

@Composable
fun ConversionHistoryScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val logEntries = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        logEntries.clear()
        logEntries.addAll(ConversionLogger.read(context))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Conversion History", style = MaterialTheme.typography.headlineMedium)
            TextButton(onClick = onBackClick) {
                Text("Back")
            }
        }

        Spacer(Modifier.height(8.dp))

        if (logEntries.isEmpty()) {
            Text("No entries yet.")
        } else {
            Column(modifier = Modifier.weight(1f)) {
                logEntries.forEach {
                    Text(it)
                    Spacer(Modifier.height(4.dp))
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                ConversionLogger.clear(context)
                logEntries.clear()
            }) {
                Text("Clear Log")
            }
            Button(onClick = {
                val intent = ConversionLogger.share(context)
                if (intent != null) {
                    context.startActivity(intent)
                }
            }) {
                Text("Share Log")
            }
        }
    }
}