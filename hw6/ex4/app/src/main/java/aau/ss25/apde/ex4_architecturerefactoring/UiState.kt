package aau.ss25.apde.ex4_architecturerefactoring

data class UiState(
    val isLoading: Boolean = true,
    val data: List<String> = emptyList()
)
