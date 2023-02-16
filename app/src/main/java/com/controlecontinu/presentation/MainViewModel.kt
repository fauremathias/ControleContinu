package com.controlecontinu.presentation

import android.content.Context
import com.controlecontinu.data.Todo
import com.controlecontinu.domain.MainRepository

class MainViewModel {
    val repository = MainRepository()
    lateinit var listener: IRefreshTodoListener

    fun getStoredTodos(context: Context): ArrayList<Todo> {
        return repository.getStoredTodos(context)
    }

    fun saveTodo(context: Context, todo: Todo) {
        repository.saveTodo(context, todo)
        listener.refreshTodos(repository.getStoredTodos(context))
    }

    fun deleteTodo(context: Context, todo: Todo) {
        repository.deleteTodo(context, todo)
        listener.refreshTodos(repository.getStoredTodos(context))
    }


    fun changeTodo(context: Context, todo: Todo, index: Int) {
        repository.changeTodo(context, todo, index)
    }

    fun createTodo(text: String) : Todo {
            return Todo(
                finish = false,
                task = text
            )
    }
}