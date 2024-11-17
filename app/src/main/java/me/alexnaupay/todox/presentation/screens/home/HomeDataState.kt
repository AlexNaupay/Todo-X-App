package me.alexnaupay.todox.presentation.screens.home

import me.alexnaupay.todox.domain.Task

data class HomeDataState(
    val date:String = "",
    val summary:String = "",
    val completedTask:List<Task> = emptyList(),
    val pendingTask:List<Task> = emptyList(),
)
