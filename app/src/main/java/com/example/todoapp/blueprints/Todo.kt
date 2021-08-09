package com.example.todoapp.blueprints

import java.io.Serializable

data class Todo (
    var content : String,
    var filename : String,
    var checked : Boolean,
    var important : Boolean
        ) : Serializable