package aau.ss25.apde.ex1_viewmodelbasics

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Scoreboard(viewModel: ScoreViewModel = viewModel()) {
    // StateFlow:
    // collectAsState() macht aus dem StateFlow ein State<T>
    // Compose kann das reaktiv beobachten.
    // Wenn sich der Score im ViewModel ändert, wird der Text(...) automatisch neu gezeichnet (Recomposition)
    // Das passiert ohne setState, notifyDataSetChanged() oder manuelles Neuladen!
    val teamAScore by viewModel.teamAScore.collectAsState()
    val teamBScore by viewModel.teamBScore.collectAsState()

    // LiveData:
    // val teamAScore by viewModel.teamAScore.observeAsState(0)
    // ...

    // Ohne ViewModel, StateFlow oder LiveData:
    // var teamAScore by rememberSaveable { mutableStateOf(0) }
    // ...
    // -> Button(onClick = { teamAScore++ }) { ... }
    // Vergleich StateFlow/LiveData vs. ohne (rememberSavable) siehe unten

    // Bonus: Show that the state persists across recompositions.
    var dummy by remember { mutableIntStateOf(0) }
    Log.d("Scoreboard", "Composed!") // Compose recomposed nur die Teile, die sich wirklich ändern müssen (Button Text)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Team A: $teamAScore", fontSize = 24.sp)
        Button(onClick = viewModel::increaseTeamA) {
            Text("Increase Team A")
        }

        Text("Team B: $teamBScore", fontSize = 24.sp)
        Button(onClick = viewModel::increaseTeamB) {
            Text("Increase Team B")
        }

        Button(onClick = viewModel::resetScores) {
            Text("Reset Scores")
        }

        Button(onClick = { dummy++ }) {
            Text("Trigger Dummy Recomposition ($dummy)")
        }
    }
}

// Vergleich StateFlow/LiveData vs. ohne
// +----------------------------------------------------------------------------+
// | Kriterium              | rememberSaveable | ViewModel + StateFlow/LiveData |
// +----------------------------------------------------------------------------+
// | Überlebt Recomposition | Ja               | Ja                             |
// | Überlebt Rotation      | Ja               | Ja                             |
// | Überlebt Prozess-Kill  | Nein             | teilweise bei SavedStateHandle |
// | Testbarkeit            | schwer           | gut                            |
// | Logik-Kapselung        | in der UI        | ViewModel                      |
// | Wiederverwendbarkeit   | gering           | hoch                           |
// +----------------------------------------------------------------------------+

// Explain why using ViewModel is beneficial here:
// Persönlich finde ich ein viewModel hier vorteilhaft, da es grundsätzlich einmal
// sauberer ist die Logik vom Composable zu entkapseln. In weiterer Folge denke ich
// das es so viel leichter ist das Projekt einmal zu erweitern