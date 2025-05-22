package aau.ss25.apde.ex4_architecturerefactoring.ui.theme

import aau.ss25.apde.ex4_architecturerefactoring.DataRepository
import aau.ss25.apde.ex4_architecturerefactoring.DataViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DataViewModelFactory(
    private val repository: DataRepository // Repo wird als Abhängigkeit übergeben und gespeichert
) : ViewModelProvider.Factory {
    // wird von ViewModel-System aufgerufen, wenn ein ViewModel benötigt wird
    // modelClass gibt an, welche ViewModel-Klasse erzeugt werden soll
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // isAssignableFrom prüft, ob modelClass vom Typ DataViewModel ist oder davon erbt
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
