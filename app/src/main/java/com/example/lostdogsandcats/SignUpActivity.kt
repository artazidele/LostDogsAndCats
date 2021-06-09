package com.example.lostdogsandcats

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val emailToSave = findViewById<EditText>(R.id.email)
        val passwordOne = findViewById<EditText>(R.id.password)

        val to_log_in_button = findViewById<Button>(R.id.to_log_in)
        to_log_in_button.setOnClickListener {
            onBackPressed()
        }

        val register_button = findViewById<Button>(R.id.register)
        val validEmail = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+")
        register_button.setOnClickListener {
            when {
                TextUtils.isEmpty(emailToSave.text.toString())->{
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.signup, null)
                    val builder = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setTitle("You have to write an email.")
                    val alertDialog = builder.show()
                    dialogView.findViewById<Button>(R.id.ok_button).setOnClickListener {
                        alertDialog.dismiss()
                    }
                }

                !validEmail.matcher(emailToSave.text.toString()).matches() -> {
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.signup, null)
                    val builder = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setTitle("You have to write valid email.")
                    val alertDialog = builder.show()
                    dialogView.findViewById<Button>(R.id.ok_button).setOnClickListener {
                        alertDialog.dismiss()
                    }
                }

                TextUtils.isEmpty(passwordOne.text.toString()) || passwordOne.text.length<6 ->{
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.signup, null)
                    val builder = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setTitle("Password must be at least 6 characters long.")
                    val alertDialog = builder.show()
                    dialogView.findViewById<Button>(R.id.ok_button).setOnClickListener {
                        alertDialog.dismiss()
                    }
                }

                else -> {
                    val email: String = emailToSave.text.toString()
                    val passwordToSave: String = passwordOne.text.toString()
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email, passwordToSave)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    val fireBaseUser: FirebaseUser = task.result!!.user!!
                                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", fireBaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    //
                                }
                            })
                }
            }

        }
    }
}