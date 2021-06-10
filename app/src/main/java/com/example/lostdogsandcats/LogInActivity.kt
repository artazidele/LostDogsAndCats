package com.example.lostdogsandcats

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val register_button = findViewById<Button>(R.id.to_register)
        register_button.setOnClickListener {
            startActivity(Intent(this@LogInActivity, SignUpActivity::class.java))
        }

        val log_in_button = findViewById<Button>(R.id.button_save)
        val emailView = findViewById<EditText>(R.id.email_log_in)
        val passwordView = findViewById<EditText>(R.id.password_log_in)
        log_in_button.setOnClickListener {
            when {
                TextUtils.isEmpty(emailView.text.toString()) -> {

                    val dialogView = LayoutInflater.from(this).inflate(R.layout.signup, null)
                    val builder = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setTitle("You have to write an email.")
                    val alertDialog = builder.show()
                    dialogView.findViewById<Button>(R.id.ok_button).setOnClickListener {
                        alertDialog.dismiss()
                    }
                }
                TextUtils.isEmpty(passwordView.text.toString()) -> {

                    val dialogView = LayoutInflater.from(this).inflate(R.layout.signup, null)
                    val builder = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setTitle("You have to write a password.")
                    val alertDialog = builder.show()
                    dialogView.findViewById<Button>(R.id.ok_button).setOnClickListener {
                        alertDialog.dismiss()
                    }
                }
                else -> {
                    val email: String = emailView.text.toString()
                    val password: String = passwordView.text.toString()
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful) {
                                val intent = Intent(this@LogInActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                val dialogView = LayoutInflater.from(this).inflate(R.layout.signup, null)
                                val builder = AlertDialog.Builder(this)
                                    .setView(dialogView)
                                    .setTitle("You have to write correct email and password.")
                                val alertDialog = builder.show()
                                dialogView.findViewById<Button>(R.id.ok_button).setOnClickListener {
                                    alertDialog.dismiss()
                                }

                            }
                        }
                }
            }
        }



    }
}