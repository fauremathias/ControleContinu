package com.controlecontinu.presentation

import com.controlecontinu.data.Todo

interface ITodoListener {
    fun deleteTodo(todo: Todo)
    fun changeTodo(todo: Todo, index: Int)
}