package me.alexnaupay.todox.presentation.screens.detail

sealed interface TaskEvent{
    data object TaskCreated: TaskEvent
}