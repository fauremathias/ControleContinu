package com.controlecontinu.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.controlecontinu.R
import com.controlecontinu.data.Todo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ITodoListener, IRefreshTodoListener {

    // Déclaration des propriétés de la classe
    private lateinit var addButton: FloatingActionButton
    private lateinit var recycler: RecyclerView
    private lateinit var data: ArrayList<Todo>
    private lateinit var adapter: TodoAdapter
    private var viewModel = MainViewModel()
    private lateinit var imageVide: ImageView
    private lateinit var textTitle: TextView
    //private lateinit var datePicker: DatePicker

    // Méthode onCreate appelée à la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*val datePicker = findViewById<DatePicker>(R.id.date_Picker)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }*/


        // Définition du layout pour l'activité
        setContentView(R.layout.activity_main)

        // Configuration du ViewModel
        viewModel.listener = this
        data = viewModel.getStoredTodos(this)

        // Récupération des vues depuis le layout
        addButton = findViewById(R.id.add_button)
        recycler = findViewById(R.id.recycler_list)
        imageVide = findViewById(R.id.imageVide)
        textTitle = findViewById(R.id.textTitle)

        // Affichage de l'image vide si la liste de tâches est vide
        showImg()

        // Configuration de l'adaptateur pour le RecyclerView
        adapter = TodoAdapter(data)
        adapter.listener = this
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        // Ajout d'un listener pour le bouton d'ajout de tâches
        addButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    // Affichage de l'image vide si la liste de tâches est vide
    private fun showImg() {
        if (data.isEmpty()) {
            imageVide.visibility = View.VISIBLE
            textTitle.visibility = View.VISIBLE
        } else {
            imageVide.visibility = View.INVISIBLE
            textTitle.visibility = View.INVISIBLE
        }
    }

    // Affichage de la boîte de dialogue pour l'ajout de tâches
    @SuppressLint("InflateParams", "NotifyDataSetChanged")
    fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.edittext_todo_dialog, null)
        val editText: EditText = layout.findViewById(R.id.editTextTodo)

        builder.apply {
            setTitle("Add task")
            setPositiveButton("Add") { _, _ ->
                try {
                    // Création d'une nouvelle tâche et sauvegarde dans la base de données
                    val todo = viewModel.createTodo(editText.text.toString())
                    viewModel.saveTodo(this@MainActivity, todo)

                    // Mise à jour de l'adaptateur
                    adapter.notifyDataSetChanged()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this@MainActivity, "Failed task", Toast.LENGTH_SHORT).show()
                }
            }
            setNegativeButton("Cancel") { _, _ ->
                // Annulation de l'ajout de tâche
            }
            setView(layout)
            show()
        }
    }

    // Suppression d'une tâche
    override fun deleteTodo(todo: Todo) {
        viewModel.deleteTodo(this, todo)
    }

    // Modification d'une tâche
    override fun changeTodo(todo: Todo, index: Int) {
        viewModel.changeTodo(this, todo, index )
    }

    // Rafraîchissement de la liste de tâches
    @SuppressLint("NotifyDataSetChanged")
    override fun refreshTodos(data: List<Todo>) {
        this.data.clear()
        this.data.addAll(data)
        showImg()
        this.adapter.notifyDataSetChanged()
    }
}