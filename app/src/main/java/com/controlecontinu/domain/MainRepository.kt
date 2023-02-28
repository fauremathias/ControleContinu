package com.controlecontinu.domain

import android.content.Context
import com.controlecontinu.data.Todo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class MainRepository {

    // Cette fonction permet de récupérer la liste des tâches stockées dans les préférences de l'application
    fun getStoredTodos(context: Context): ArrayList<Todo>{
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val todosStr = preferences.getString("todos", "") // Récupération de la chaîne de caractères représentant la liste des tâches stockées
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<Todo?>?>() {}.type // Création d'un objet Type qui représente le type d'objet à désérialiser
        return try {
            gson.fromJson<Any>(todosStr, type) as ArrayList<Todo> // Désérialisation de la chaîne de caractères et conversion en ArrayList<Todo>
        } catch (e: NullPointerException) {
            ArrayList() // Si la chaîne de caractères est nulle, on retourne une liste vide
        }
    }

    // Cette fonction permet de sauvegarder une nouvelle tâche dans les préférences de l'application
    fun saveTodo(context: Context, todo: Todo) {
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val todos = getStoredTodos(context) // Récupération de la liste des tâches stockées
        todos.add(todo) // Ajout de la nouvelle tâche
        editor.putString("todos", Gson().toJson(todos)) // Sérialisation de la liste des tâches et stockage dans les préférences de l'application
        editor.apply() // Application des modifications
    }

    // Cette fonction permet de modifier une tâche existante dans les préférences de l'application
    fun changeTodo(context: Context, todo: Todo, index: Int) {
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val todosStr = preferences.getString("todos", "")
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<Todo?>?>() {}.type
        var todos = ArrayList<Todo>()
        try {
            todos = gson.fromJson<Any>(todosStr, type) as ArrayList<Todo>
        } catch (e: NullPointerException) { e.printStackTrace() }
        todos[index] = todo // Modification de la tâche à l'index donné
        editor.putString("todos", gson.toJson(todos)) // Sérialisation de la liste des tâches et stockage dans les préférences de l'application
        editor.apply() // Application des modifications
    }

    // Cette fonction permet de supprimer une tâche existante dans les préférences de l'application
    fun deleteTodo(context: Context, todo: Todo) {
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val todosStr = preferences.getString("todos", "")
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<Todo?>?>() {}.type
        try {
            val todos = gson.fromJson<Any>(todosStr, type) as ArrayList<Todo>
            todos.remove(todo) // Suppression de la tâche donnée
            editor.putString("todos", gson.toJson(todos))
            editor.apply()
        } catch (e: NullPointerException) { e.printStackTrace() }
    }

}