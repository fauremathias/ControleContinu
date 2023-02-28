package com.controlecontinu.presentation

import com.controlecontinu.data.Todo

/**
 * Interface décrivant un auditeur pour les événements liés aux tâches à accomplir.
 */
interface ITodoListener {

    /**
     * Appelé lorsqu'une tâche doit être supprimée.
     * @param todo La tâche à supprimer.
     */
    fun deleteTodo(todo: Todo)

    /**
     * Appelé lorsqu'une tâche doit être modifiée.
     * @param todo La tâche à modifier.
     * @param index L'index de la tâche dans la liste.
     */
    fun changeTodo(todo: Todo, index: Int)
}