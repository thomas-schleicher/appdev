package aau.ss25.apde.ex4_architecturerefactoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// bekommt im Konstruktor ein DataRepository, das für das Laden von Daten verantwortlich ist
class DataViewModel(private val repository: DataRepository) : ViewModel() {

    // intern beschreibbarer Zustand mit MutableStateFlow (kann verändert werden)
    private val _uiState = MutableStateFlow(UiState())
    // öffentlich lesbarer Zustand als StateFlow (nur Beobachtung, keine Änderung von außen)
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadData() // bei Erstellung des vm, sofortiges Laden der Daten
    }

    fun loadData() {
        viewModelScope.launch { // startet Coroutine
            // löst aktualisierung in UI aus, da sich _uiState.value ändert
            //      - Prinzip von reactive state updates
            // copy, da DataClasses in Kotlin im Grunde immutable (unveränderlich) sind
            // d.h. Objekt selbst kann man nicht ändern, deshalb neues Objekt erstellen
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = repository.fetchData()
            _uiState.value = UiState(isLoading = false, data = result)
        }
    }

    fun retry() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = repository.fetchRetryData()
            _uiState.value = UiState(isLoading = false, data = result)
        }
    }
}
