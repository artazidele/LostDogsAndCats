package com.example.lostdogsandcats

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

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
        register_button.setOnClickListener {
            when {
                TextUtils.isEmpty(emailToSave.text.toString())->{

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