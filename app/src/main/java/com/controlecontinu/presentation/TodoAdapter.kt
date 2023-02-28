package com.controlecontinu.presentation

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.controlecontinu.R
import com.controlecontinu.data.Todo

// Cette classe est responsable de l'adaptation des données Todo à la vue RecyclerView.
class TodoAdapter (
    private val data: List<Todo>
): RecyclerView.Adapter<TodoAdapter.ViewHolder>(){

    // Référence à l'objet listener qui implémente ITodoListener.
    lateinit var listener: ITodoListener

    // Crée un ViewHolder en utilisant le layout todo_item_layout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item_layout,parent, false)
        return ViewHolder(view)
    }

    // Retourne le nombre d'éléments dans la liste de données.
    override fun getItemCount(): Int {
        return data.size
    }

    // Lie les données à la vue en utilisant un ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder,  position: Int) {
        // Récupère l'élément correspondant à la position.
        var item = data[position]

        // Met à jour le texte et l'état de la case à cocher pour l'élément.
        holder.taskText.setText(item.task)
        holder.finishCheckBox.isChecked = item.finish

        // Gère les événements lorsque la case à cocher est cliquée.
        holder.finishCheckBox.setOnClickListener {
            item = data[holder.adapterPosition]
            item.finish = holder.finishCheckBox.isChecked
            listener.changeTodo(item, holder.adapterPosition)
        }

        // Gère les événements lorsque le texte de la tâche est modifié.
        holder.taskText.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                item = data[holder.adapterPosition]
                item.task = holder.taskText.text.toString()
                listener.changeTodo(item, holder.adapterPosition)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        // Gère les événements lorsque le bouton de suppression est cliqué.
        holder.deleteButton.setOnClickListener{
            listener.deleteTodo(item)
        }
    }

    // ViewHolder qui contient les vues pour chaque élément de la liste.
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val taskText: EditText = view.findViewById(R.id.textTask)
        val finishCheckBox: CheckBox = view.findViewById(R.id.checkBoxFinish)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
    }
}
