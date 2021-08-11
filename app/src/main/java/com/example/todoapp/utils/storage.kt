package com.example.todoapp.utils

import android.content.Context
import android.text.TextUtils
import com.example.todoapp.blueprints.Todo
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

fun persistData(context : Context, todo : Todo){
    if (TextUtils.isEmpty(todo.filename)){
        todo.filename = UUID.randomUUID().toString() + ".mytodo"
    }

    val fileOutput = context.openFileOutput(todo.filename, Context.MODE_PRIVATE)
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(todo)
    outputStream.close()
}

fun loadTodo(context : Context, filename : String) : Todo {
    val fileInput = context.openFileInput(filename)
    val inputStream = ObjectInputStream(fileInput)
    val todo = inputStream.readObject() as Todo
    inputStream.close()

    return todo
}

fun loadTodos(context: Context) : MutableList<Todo> {
    val todos = mutableListOf<Todo>()
    val todosDir = context.filesDir
    for (filename in todosDir.list()){
        val todo = loadTodo(context, filename)
        todos.add(todo)
    }
    return todos
}

fun markTodoAsChecked(context: Context, todo : Todo){
    val todosDir = context.filesDir
    if (todo.filename in todosDir.list()){
        persistData(context, todo)
    }
}

fun loadCompletedTodos(context: Context) : MutableList<Todo> {
    val todos = mutableListOf<Todo>()
    val todosDir = context.filesDir
    for (filename in todosDir.list()){
        val todo = loadTodo(context, filename)
        if (todo.checked){
            todos.add(todo)
        }
    }
    return todos
}

fun deleteTodo(context: Context, todo : Todo){
    context.deleteFile(todo.filename)
}