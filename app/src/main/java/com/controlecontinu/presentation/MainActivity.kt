package com.controlecontinu.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.controlecontinu.R
import com.controlecontinu.data.Todo
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ITodoListener, IRefreshTodoListener{
    private lateinit var addButton: FloatingActionButton
    private lateinit var recycler: RecyclerView
    private lateinit var data: ArrayList<Todo>
    private lateinit var adapter: TodoAdapter
    private var viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.listener = this
        data = viewModel.getStoredTodos(this)

        addButton = findViewById(R.id.add_button)
        recycler = findViewById(R.id.recycler_list)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = TodoAdapter(data)
        adapter.listener = this

        recycler.adapter = adapter

        addButton.setOnClickListener {
            showAddTaskDialog()
        }


    }
    fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.edittext_todo_dialog, null)
        val editText: EditText = layout.findViewById(R.id.editTextTodo)

        builder.apply {
            setTitle("Add task")
            setPositiveButton("Add") { _, _ ->
                try {
                    val todo = viewModel.createTodo(editText.text.toString())
                    viewModel.saveTodo(this@MainActivity, todo)
                    adapter.notifyDataSetChanged()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this@MainActivity, "Failed task", Toast.LENGTH_SHORT).show()
                }
            }
            setNegativeButton("Cancel") { _, _ ->

            }
            setView(layout)
            show()
        }
    }

    override fun deleteTodo(todo: Todo) {
        viewModel.deleteTodo(this,todo)
    }

    override fun changeTodo(todo: Todo, index: Int) {
        viewModel.changeTodo(this, todo, index )
    }

    override fun refreshTodos(data: List<Todo>) {
        this.data.clear()
        this.data.addAll(data)
        this.adapter.notifyDataSetChanged()
    }
}