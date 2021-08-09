package com.example.todoapp.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Layout
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.todoapp.R

class CreateDialogFragment : DialogFragment() {
    interface DialogListener{
        fun onPositiveClick()
        fun onNegativeClick()
    }

    var listener : DialogListener? = null
    var layoutET : EditText? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val inflater = activity?.layoutInflater
        val layout = inflater?.inflate(R.layout.create_dialog_layout, null)
        layoutET = layout?.findViewById<EditText>(R.id.edit_text)

        builder.setView(layout)
            .setPositiveButton("Create", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, id: Int) {
                    listener?.onPositiveClick()
                }
            })
            .setNegativeButton("Cancel", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    listener?.onNegativeClick()
                }

            })
        return builder.create()
    }
}