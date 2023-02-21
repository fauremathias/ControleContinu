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

class TodoAdapter (
    private val data: List<Todo>
): RecyclerView.Adapter<TodoAdapter.ViewHolder>(){

    lateinit var listener: ITodoListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item_layout,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder,  position: Int) {
        var item = data[position]
        holder.taskText.setText(item.task)
        holder.finishCheckBox.isChecked = item.finish

        holder.finishCheckBox.setOnClickListener {
            item = data[holder.adapterPosition]
            item.finish = holder.finishCheckBox.isChecked
            listener.changeTodo(item, holder.adapterPosition)
        }

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

        holder.deleteButton.setOnClickListener{
            listener.deleteTodo(item)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val taskText: EditText = view.findViewById(R.id.textTask)
        val finishCheckBox: CheckBox = view.findViewById(R.id.checkBoxFinish)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
    }
}