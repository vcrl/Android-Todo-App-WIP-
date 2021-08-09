package com.example.todoapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.todoapp.constants.Constants

class CreateTodo : AppCompatActivity(), View.OnClickListener {

    lateinit var editText : EditText
    lateinit var submitBtn : Button
    lateinit var checkBox : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo)

        editText = findViewById(R.id.edit_text)

        submitBtn = findViewById(R.id.submit_btn)
        submitBtn.setOnClickListener(this)

        checkBox = findViewById(R.id.checkbox)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.submit_btn -> sendData()
        }
    }

    private fun sendData(){
        var intent = Intent()
        intent.putExtra(Constants.TODO_CONTENT, editText.text.toString())
        if (checkBox.isChecked){
            intent.putExtra(Constants.TODO_IMPORTANT, true)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}