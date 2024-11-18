package me.alexnaupay.todox.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.alexnaupay.todox.data.FakeTaskLocalDataSource
import me.alexnaupay.todox.presentation.screens.home.HomeScreenAction.OnAddTask
import me.alexnaupay.todox.presentation.screens.home.HomeScreenAction.OnDeleteAllTasks
import me.alexnaupay.todox.presentation.screens.home.HomeScreenAction.OnDeleteTask
import me.alexnaupay.todox.presentation.screens.home.HomeScreenAction.OnToggleTask
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.toString

class HomeScreenViewModel : ViewModel() {

    // Get data source
    private val taskLocalDataSource = FakeTaskLocalDataSource

    var state by mutableStateOf(HomeDataState())
        private set

    // Events
    private val eventChannel = Channel<HomeScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        state = state.copy(
            date = LocalDate.now().let {
                // https://developer.android.com/reference/kotlin/java/time/format/DateTimeFormatter
                DateTimeFormatter.ofPattern("EEEE, MMM dd").format(it)
            }
        )

        taskLocalDataSource.tasksFlow.onEach {
            val completedTasks = it.filter { task -> task.isCompleted }.sortedByDescending { it.date }
            val pendingTasks = it.filter { task -> !task.isCompleted }.sortedByDescending { task -> task.date }

            state = state.copy(
                summary = pendingTasks.size.toString(),
                completedTask = completedTasks,
                pendingTask = pendingTasks,
            )
        }.launchIn(viewModelScope)

    }


    fun onAction(action: HomeScreenAction) {
        viewModelScope.launch {
            when (action) {
                is OnDeleteTask -> {
                    taskLocalDataSource.removeTask(action.task)
                    eventChannel.send(HomeScreenEvent.DeletedTask)
                }

                is OnToggleTask -> {
                    val updatedTask = action.task.copy(isCompleted = !action.task.isCompleted)
                    taskLocalDataSource.updateTask(updatedTask)
                    eventChannel.send(HomeScreenEvent.UpdatedTask)
                }

                OnDeleteAllTasks -> {
                    taskLocalDataSource.deleteAllTasks()
                    eventChannel.send(HomeScreenEvent.AllTaskDeleted)
                }

                else -> Unit
            }
        }
    }

}