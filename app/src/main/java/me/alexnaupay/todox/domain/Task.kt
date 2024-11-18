package me.alexnaupay.todox.domain

import java.time.LocalDateTime

data class Task(
    val id: String,
    val title:String,
    val description:String?,
    val isCompleted:Boolean = false,
    val category: Category? = Category.PERSONAL,
    val date: LocalDateTime = LocalDateTime.now()
)
