package com.example.todoapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.blueprints.Todo

class RvAdapter_List(var Todos : MutableList<Todo>, var onCheckedChangeListener : CompoundButton.OnCheckedChangeListener, var context : Context) : RecyclerView.Adapter<RvAdapter_List.ViewHolder>() {
    class ViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val cardview = viewItem.findViewById<CardView>(R.id.card_view)
        val todo_content = viewItem.findViewById<TextView>(R.id.todo_text)
        val checkbox = viewItem.findViewById<CheckBox>(R.id.checkbox)
    }

    lateinit var todo : Todo

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // UI
        val inflater = LayoutInflater.from(parent.context)
        val viewItem = inflater.inflate(R.layout.todo_item, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Data
        todo = Todos[position]
        holder.todo_content.text = todo.content
        holder.checkbox.setOnCheckedChangeListener(onCheckedChangeListener)
        holder.checkbox.tag = position
        holder.setIsRecyclable(false)
        if (todo.important){
            holder.cardview.setCardBackgroundColor(Color.parseColor("#ffbd45"))
        }
    }

    override fun getItemCount(): Int {
        // List
        return Todos.size
    }
}