package com.controlecontinu.presentation

import com.controlecontinu.data.Todo

interface IRefreshTodoListener {
    // Déclaration d'une méthode nommée refreshTodos qui prend en paramètre une liste d'objets de type Todo
    fun refreshTodos(data: List<Todo>)
}