package aau.ss25.apde.ex1_viewmodelbasics

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Was macht StateFlow bzw. LiveData?
// ==================================
// Sowohl StateFlow als auch LiveData sind Observable-Datencontainer, also Klassen, mit denen man
// Zustandsänderungen reaktiv beobachten kann. Sie werden verwendet, um Daten vom ViewModel zur
// UI zu "streamen" – z.B. in Jetpack Compose oder bei XML-Views.

// Unterschiede StateFlow vs. LiveData:
// +------------------------------------------------------------------------------------------+
// | Feature              | LiveData                    | StateFlow                           |
// +------------------------------------------------------------------------------------------+
// | Lifecycle-aware      | Ja                          | Ja (in collectAsStateWithLifecycle) |
// | Coroutine support    | Nein                        | Ja                                  |
// | Compose-Integration  | Gut (observeAsState)        | Besser (collectAsState)             |
// | Legacy Compatibility | Besser mit XML-basiertem UI | Eher Compose/modern                 |
// +------------------------------------------------------------------------------------------+

class ScoreViewModel : ViewModel() {
    // StateFlow:
    private val _teamAScore = MutableStateFlow(0) // erzeugt einen reaktiven Wert mit Startwert 0
    val teamAScore: StateFlow<Int> = _teamAScore // ist ein öffentlich lesbarer Stream

    private val _teamBScore = MutableStateFlow(0)
    val teamBScore: StateFlow<Int> = _teamBScore

    // LiveData:
    // private val _teamAScore = MutableLiveData(0)
    // val teamAScore: LiveData<Int> = _teamBScore

    // private val _teamBScore ...

    fun increaseTeamA() { // erhöht intern den Wert, wodurch alle Beobachter sofort benachrichtigt werden.
        // StateFlow:
        _teamAScore.value++

        // LiveData:
        // _teamAScore.value = (_teamAScore.value ?: 0) + 1
    }

    fun increaseTeamB() {
        _teamBScore.value++
    }

    fun resetScores() {
        _teamAScore.value = 0
        _teamBScore.value = 0
    }
}
