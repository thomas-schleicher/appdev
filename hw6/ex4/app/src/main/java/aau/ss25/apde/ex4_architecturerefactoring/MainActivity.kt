package aau.ss25.apde.ex4_architecturerefactoring

import aau.ss25.apde.ex4_architecturerefactoring.ui.theme.DataViewModelFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import aau.ss25.apde.ex4_architecturerefactoring.ui.theme.Ex4_ArchitectureRefactoringTheme
import androidx.activity.viewModels

class MainActivity : ComponentActivity() {
    // Erstellt ein viewModel, aber nicht direkt
    // Erstellung wird einem Factory-Objekt überlassen
    // Normalerweise: val viewModel: DataViewModel by viewModels()
    //      Funktioniert nur, wenn das ViewModel einen leeren Konstruktor hat
    //      DataViewModel braucht aber beim Erzeugen ein DataRepository, also einen Parameter
    private val viewModel: DataViewModel by viewModels {
        DataViewModelFactory(DataRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ex4_ArchitectureRefactoringTheme {
                DecoupledUI(viewModel = viewModel)
            }
        }
    }
}