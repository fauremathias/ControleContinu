package com.controlecontinu.presentation

import android.content.Context
import com.controlecontinu.data.Todo
import com.controlecontinu.domain.MainRepository

/**
 * ViewModel pour l'écran principal de l'application.
 * Gère la récupération et la manipulation des données Todo via le MainRepository.
 */
class MainViewModel {

    private val repository = MainRepository()
    lateinit var listener: IRefreshTodoListener

    /**
     * Récupère toutes les tâches stockées dans la base de données.
     * @param context Le contexte de l'application.
     * @return Une liste de Todo stockées dans la base de données.
     */
    fun getStoredTodos(context: Context): ArrayList<Todo> {
        return repository.getStoredTodos(context)
    }

    /**
     * Sauvegarde une tâche dans la base de données.
     * @param context Le contexte de l'application.
     * @param todo La tâche à sauvegarder.
     */
    fun saveTodo(context: Context, todo: Todo) {
        repository.saveTodo(context, todo)
        listener.refreshTodos(repository.getStoredTodos(context))
    }

    /**
     * Supprime une tâche de la base de données.
     * @param context Le contexte de l'application.
     * @param todo La tâche à supprimer.
     */
    fun deleteTodo(context: Context, todo: Todo) {
        repository.deleteTodo(context, todo)
        listener.refreshTodos(repository.getStoredTodos(context))
    }

    /**
     * Modifie une tâche dans la base de données.
     * @param context Le contexte de l'application.
     * @param todo La tâche à modifier.
     * @param index L'index de la tâche dans la liste.
     */
    fun changeTodo(context: Context, todo: Todo, index: Int) {
        repository.changeTodo(context, todo, index)
    }

    /**
     * Crée une nouvelle tâche avec le texte donné.
     * @param text Le texte de la tâche à créer.
     * @return La nouvelle tâche créée.
     */
    fun createTodo(text: String) : Todo {
        return Todo(
            finish = false,
            task = text
        )
    }
}
