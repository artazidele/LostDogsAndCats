package com.example.lostdogsandcats.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.lostdogsandcats.databinding.SignupBinding

/**
 * @param context app context
 * @param title text shown in dialog title field
 */
fun showConfirmDialog(context: Context, title: String) {
    val dialogBinding = SignupBinding.inflate(LayoutInflater.from(context))

    val dialogBuilder = AlertDialog.Builder(context).apply {
        setView(dialogBinding.root)
        setTitle(title)
    }
    val alertDialog = dialogBuilder.show()
    dialogBinding.okButton.setOnClickListener {
        alertDialog.dismiss()
    }
}
