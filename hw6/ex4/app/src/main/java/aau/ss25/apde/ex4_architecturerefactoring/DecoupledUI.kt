package aau.ss25.apde.ex4_architecturerefactoring

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DecoupledUI(viewModel: DataViewModel = viewModel()) { // holt viewModel, welches UI-State verwaltet
    // beobachtet StateFlow aus dem viewModel
    // durch collectAsState() wird der Flow in einen Compose-kompatiblen State umgewandelt
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DataContent(uiState) // zeigt Ladezustand oder Daten

        RetryButton(
            onRetry = { viewModel.retry() }
        )
    }
}

@Composable // BoxScope.xy() -> wegen align, da dieses nur innerhalb eines Boxscopes funktioniert
private fun BoxScope.DataContent(uiState: UiState) {
    if (uiState.isLoading) {
        // "Ladekreis"
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    } else {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = "Data Loaded:")
            Spacer(modifier = Modifier.height(8.dp))
            uiState.data.forEach { item ->
                Text(text = item)
            }
        }
    }
}

@Composable
private fun BoxScope.RetryButton(onRetry: () -> Unit) {
    Button(
        onClick = onRetry,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 16.dp)
    ) {
        Text(text = "Retry")
    }
}
