package com.example.todoapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.adapter.RvAdapter_List
import com.example.todoapp.blueprints.Todo
import com.example.todoapp.constants.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class TodoList : AppCompatActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener,
NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar : Toolbar

    lateinit var adapterList : RvAdapter_List

    lateinit var todos : MutableList<Todo>
    lateinit var todosBin : MutableList<Todo>

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        var createTodo = findViewById<FloatingActionButton>(R.id.floatingActionBtn)
        createTodo.setOnClickListener(this)

        /* Drawer Layout */
        var drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        // Link toggle to the drawerLayout, for it to know which drawerLayout to act on
        drawerLayout.addDrawerListener(toggle)
        // Sync toggle to activate it
        toggle.syncState()
        // Add a button on the left toolbar to open the drawer
        // Si il y a un ActionBarDawerToggle, icone = 3 barres !
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get clicks inside the menu
        var navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        /* Recycler View */
        todos = mutableListOf<Todo>(
            Todo("Faire des courses", "", true, false),
            Todo("Coder une application", "", false, true),
            Todo("Caresser Jules", "", false, false),
        )

        todosBin = mutableListOf<Todo>()

        adapterList = RvAdapter_List(todos, this, this)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterList


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCheckedChanged(checkbox: CompoundButton?, isChecked: Boolean) {
        if(checkbox!!.tag != null){
            checkTodo(checkbox!!.tag as Int)
        }
    }

    private fun checkTodo(todoId : Int){
        val todo = todos[todoId]
        todo.checked = true
        todos.removeAt(todoId)
        todosBin.add(0, todo)
        adapterList.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.floatingActionBtn -> {
                openCreateTodoActivity()
            }
        }
    }

    private fun openCreateTodoActivity(){
        val intent = Intent(this, CreateTodo::class.java)
        startActivityForResult(intent, Constants.REQUEST_CREATE_TODO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null){
            return
        }

        when(requestCode){
            Constants.REQUEST_CREATE_TODO -> processCreateTodoResult(data)
        }
    }

    private fun processCreateTodoResult(data: Intent?){
        var todoContent = data?.getStringExtra(Constants.TODO_CONTENT)
        var todoImportant = data?.getBooleanExtra(Constants.TODO_IMPORTANT, false)
        var todo = Todo(todoContent.toString(),"", false, todoImportant!!)
        todos.add(0, todo)
        adapterList.notifyDataSetChanged()
        Toast.makeText(this, "Your todo has been created.", Toast.LENGTH_LONG).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navItem1 -> {
                Toast.makeText(this, "Clicked!", Toast.LENGTH_LONG).show()
            }
        }
        // True means we handled the click
        return true
    }
}