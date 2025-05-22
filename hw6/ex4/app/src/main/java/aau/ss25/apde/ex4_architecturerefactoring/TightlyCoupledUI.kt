package aau.ss25.apde.ex4_architecturerefactoring

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This composable demonstrates tightly coupled UI and logic.
 * The UI and business logic (data fetching) are all combined.
 */
@Composable
fun TightlyCoupledUI() {
    // --- UiState
    var data by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    // ---
    val scope = rememberCoroutineScope()

    // Simulate a data fetch
    LaunchedEffect(Unit) {
        scope.launch {
            // --- Data Repository ---
            delay(2000) // Simulate network delay
            data = listOf("Item 1", "Item 2", "Item 3")
            isLoading = false
            // ---
        }
    }

    // UI components
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Column {
                Text(text = "Data Loaded:")
                data.forEach { item ->
                    Text(text = item)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button that simulates a retry
        Button(
            onClick = {
                isLoading = true
                scope.launch {
                    // --- Data Repository ---
                    delay(2000)
                    data = listOf("Item A", "Item B", "Item C")
                    isLoading = false
                    // ---
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Retry")
        }
    }
}
