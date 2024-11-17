package me.alexnaupay.todox.presentation.screens.home

import me.alexnaupay.todox.domain.Task

sealed interface HomeScreenAction {
    data class OnToggleTask(val task: Task) : HomeScreenAction
    data class OnDeleteTask(val task: Task) : HomeScreenAction
    data object OnDeleteAllTasks : HomeScreenAction
    data object OnAddTask : HomeScreenAction
}