package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.adapter.RvAdapter_List
import com.example.todoapp.blueprints.Todo
import com.example.todoapp.constants.Constants
import com.example.todoapp.utils.deleteTodo
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CompletedTodosList : AppCompatActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    lateinit var adapter : RvAdapter_List

    lateinit var completedTodosList : ArrayList<Todo>
    lateinit var todosToDisplay : MutableList<Todo>
    lateinit var toDeleteTodos : MutableList<Int>

    lateinit var deleteBtn : FloatingActionButton
    lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_todos_list)

        /* Layout */
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        deleteBtn = findViewById<FloatingActionButton>(R.id.delete_btn)
        deleteBtn.setOnClickListener(this)

        /* Toolbar */
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /* Lists */
        toDeleteTodos = mutableListOf()
        todosToDisplay = mutableListOf()
        completedTodosList = intent.getParcelableArrayListExtra<Todo>(Constants.COMPLETED_TODOS_LIST)!!
        for (todo in completedTodosList){
            todosToDisplay.add(todo)
        }

        /* RecyclerView */
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = RvAdapter_List(todosToDisplay, this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }



    override fun onCheckedChanged(checkbox: CompoundButton?, checked: Boolean) {
        if (checked){
            var todoId = checkbox?.tag as Int
            toDeleteTodos.add(todoId)
            toggleDeleteBtn(true)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.delete_btn -> {
                for (id in toDeleteTodos){
                    todosToDisplay.removeAt(id)
                    deleteTodo(this, completedTodosList[id])
                    adapter.notifyDataSetChanged()
                    toggleDeleteBtn(false)
                }
                Toast.makeText(this, "${toDeleteTodos.size} todo(s) have been deleted", Toast.LENGTH_SHORT).show()
                clearList(toDeleteTodos)
            }
        }
    }

    private fun clearList(list: MutableList<Int>){
        list.clear()
    }

    private fun toggleDeleteBtn(displayBtn : Boolean){
        deleteBtn.animate().apply {
            duration = 150
            if(displayBtn){
                alpha(1f)
                toolbar.subtitle = "Click the trash icon to delete the todos"
                return
            }
            alpha(0f)
            toolbar.subtitle = ""
        }
    }
}