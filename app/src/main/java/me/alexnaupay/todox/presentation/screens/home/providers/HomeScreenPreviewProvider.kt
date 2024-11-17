package me.alexnaupay.todox.presentation.screens.home.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import me.alexnaupay.todox.domain.Category
import me.alexnaupay.todox.domain.Task
import me.alexnaupay.todox.presentation.screens.home.HomeDataState

class HomeScreenPreviewProvider : PreviewParameterProvider<HomeDataState> {
    override val values: Sequence<HomeDataState>
        get() = sequenceOf(
            HomeDataState(
                date = "March 9, 2024",
                summary = "5",
                completedTask = completedTasks,
                pendingTask = pendingTasks
            )
        )
}

val pendingTasks = mutableListOf<Task>()
    .apply {
        repeat(5) {
            add(
                Task(
                    id = (it + 30).toString(),
                    title = "Task ${it+30}",
                    description = "Description ${it+30}",
                    category = Category.OTHER,
                    isCompleted = false
                )
            )
        }
    }

val completedTasks = mutableListOf<Task>()
    .apply {
        repeat(3) {
            add(
                Task(
                    id = it.toString(),
                    title = "Task $it",
                    description = "Description $it",
                    category = Category.WORK,
                    isCompleted = true
                )
            )
        }
    }
