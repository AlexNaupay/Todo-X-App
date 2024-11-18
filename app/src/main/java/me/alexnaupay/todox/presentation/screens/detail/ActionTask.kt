package me.alexnaupay.todox.presentation.screens.detail

import me.alexnaupay.todox.domain.Category

sealed interface ActionTask {
    data object SaveTask : ActionTask
    data object Back : ActionTask
    data class ChangeTaskCategory(val category: Category?) : ActionTask
    data class ChangeTaskDone(val isTaskDone: Boolean) : ActionTask
}