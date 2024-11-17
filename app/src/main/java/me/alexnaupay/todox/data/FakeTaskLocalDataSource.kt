package me.alexnaupay.todox.data


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import me.alexnaupay.todox.domain.Task
import me.alexnaupay.todox.domain.TaskLocalDataSource
import me.alexnaupay.todox.presentation.screens.home.providers.completedTasks
import me.alexnaupay.todox.presentation.screens.home.providers.pendingTasks

object FakeTaskLocalDataSource: TaskLocalDataSource {
    private val _tasksFlow = MutableStateFlow<List<Task>>(emptyList())

    init {
        _tasksFlow.value = completedTasks + pendingTasks
    }

    override val tasksFlow: Flow<List<Task>>
        get() = _tasksFlow

    override suspend fun addTask(task: Task) {
        val tasks = _tasksFlow.value.toMutableList()
        tasks.add(task)
        delay(1000L)
        _tasksFlow.value = tasks
    }

    override suspend fun updateTask(updatedTask: Task) {
        val tasks = _tasksFlow.value.toMutableList()
        val taskIndex = tasks.indexOfFirst { it.id == updatedTask.id }
        if (taskIndex != -1) {
            tasks[taskIndex] = updatedTask
            delay(350L)
            _tasksFlow.value = tasks
        }
    }

    override suspend fun removeTask(task: Task) {
        val tasks = _tasksFlow.value.toMutableList()
        tasks.remove(task)
        delay(250L)
        _tasksFlow.value = tasks
    }

    override suspend fun deleteAllTasks() {
        delay(1000L)
        _tasksFlow.value = emptyList()
    }

    override suspend fun getTaskById(taskId: String): Task? {
        delay(250L)
        return _tasksFlow.value.find { it.id == taskId }
    }
}