package aau.ss25.apde.ex4_architecturerefactoring

import kotlinx.coroutines.delay

class DataRepository {
    suspend fun fetchData(): List<String> {
        delay(2000)
        return listOf("Item 1", "Item 2", "Item 3")
    }

    suspend fun fetchRetryData(): List<String> {
        delay(2000)
        return listOf("Item A", "Item B", "Item C")
    }
}