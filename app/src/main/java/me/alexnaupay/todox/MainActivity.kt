package me.alexnaupay.todox

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import me.alexnaupay.todox.data.FakeTaskLocalDataSource
import me.alexnaupay.todox.ui.theme.TodoXAppTheme
import me.alexnaupay.todox.domain.Task
import me.alexnaupay.todox.presentation.screens.home.HomeDataState
import me.alexnaupay.todox.presentation.screens.home.HomeScreen
import me.alexnaupay.todox.presentation.screens.home.providers.completedTask
import me.alexnaupay.todox.presentation.screens.home.providers.pendingTask

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoXAppTheme {
                val fakeDatasource = FakeTaskLocalDataSource

                var text by remember { mutableStateOf("") }
                LaunchedEffect(true) {

                    launch {
                        fakeDatasource.tasksFlow.collect {
                            Log.d("MainActivity", "Tasks: $it")
                            text = it.toString()
                        }
                    }

                    launch {
                        fakeDatasource.addTask(
                            Task(
                                id = "1",
                                title = "Task 1",
                                description = "Description 1"
                            )
                        )
                        fakeDatasource.addTask(
                            Task(
                                id = "2",
                                title = "Task 2",
                                description = "Description 2"
                            )
                        )

                        fakeDatasource.getTaskById("1")?.let {
                            val updatedTask = it.copy(title = "Updated Task 1")
                            fakeDatasource.updateTask(updatedTask)
                        }

                        fakeDatasource.deleteAllTasks()
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainUI(modifier: Modifier = Modifier) {
    val state = HomeDataState(
        date = "March 9, 2024",
        summary = "5 incomplete, 5 completed",
        completedTask = completedTask,
        pendingTask = pendingTask
    )
    HomeScreen(state = state)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoXAppTheme {
        MainUI()
    }
}