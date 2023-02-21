package com.controlecontinu.domain

import android.content.Context
import com.controlecontinu.data.Todo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class MainRepository {
    fun getStoredTodos(context: Context): ArrayList<Todo>{
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val todosStr = preferences.getString("todos", "")
        //preferences.edit().clear().apply();
        val gson = Gson()

        val type: Type = object : TypeToken<ArrayList<Todo?>?>() {}.type
        return try {
            gson.fromJson<Any>(todosStr, type) as ArrayList<Todo>
        } catch (e: NullPointerException) {
            ArrayList()
        }
    }

    fun saveTodo(context: Context, todo: Todo) {
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val todos = getStoredTodos(context)
        todos.add(todo)
        editor.putString("todos", Gson().toJson(todos))
        editor.apply()
    }

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
        todos[index] = todo
        editor.putString("todos", gson.toJson(todos))
        editor.apply()
    }

    fun deleteTodo(context: Context, todo: Todo) {
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val todosStr = preferences.getString("todos", "")
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<Todo?>?>() {}.type
        try {
            val todos = gson.fromJson<Any>(todosStr, type) as ArrayList<Todo>
            todos.remove(todo)
            //editor.clear().apply();
            editor.putString("todos", gson.toJson(todos))
            editor.apply()
        } catch (e: NullPointerException) { e.printStackTrace() }
    }

}