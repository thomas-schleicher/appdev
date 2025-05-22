package at.aau.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import at.aau.mvvm.ui.theme.MVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVVMTheme {
                Scaffold { innerPadding ->
                    TodoApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TodoApp(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel()
) {
    var input by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        Row {
            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            Button(onClick = {
                viewModel.addTodo(input)
                input = ""
            }) {
                Text("Add")
            }
        }

        Spacer(Modifier.height(16.dp))

        for (todo in viewModel.todos) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.toggleDone(todo.id) }
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = if (todo.isDone) "✔️ ${todo.text}" else todo.text,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

data class Todo(
    val id: Int,
    val text: String,
    val isDone: Boolean = false
)

class TodoViewModel : ViewModel() {

    private var nextId = 0
    private val todoItems = mutableStateListOf<Todo>()
    val todos: List<Todo> = todoItems

    fun addTodo(text: String) {
        if (text.isNotBlank()) {
            todoItems.add(Todo(id = nextId++, text = text))
        }
    }

    fun toggleDone(id: Int) {
        val index = todoItems.indexOfFirst { it.id == id }
        if (index != -1) {
            val todo = todoItems[index]
            todoItems[index] = todo.copy(isDone = !todo.isDone)
        }
    }
}