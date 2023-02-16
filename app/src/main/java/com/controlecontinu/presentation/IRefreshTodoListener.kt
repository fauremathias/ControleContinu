package com.controlecontinu.presentation

import com.controlecontinu.data.Todo

interface IRefreshTodoListener {
    fun refreshTodos(data: List<Todo>)
}