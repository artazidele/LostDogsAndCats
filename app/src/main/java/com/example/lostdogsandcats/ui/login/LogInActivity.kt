package com.example.lostdogsandcats.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.lostdogsandcats.databinding.ActivityLogInBinding
import com.example.lostdogsandcats.ui.main.MainActivity
import com.example.lostdogsandcats.ui.signup.SignUpActivity
import com.example.lostdogsandcats.utils.EMAIL_ID
import com.example.lostdogsandcats.utils.USER_ID
import com.example.lostdogsandcats.utils.showConfirmDialog
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        binding.registerButton.setOnClickListener {
            startActivity(Intent(this@LogInActivity, SignUpActivity::class.java))
        }
        binding.buttonSave.setOnClickListener { handleLogin() }
    }

    private fun handleLogin() {
        when {
            TextUtils.isEmpty(binding.emailLogIn.text.toString()) -> {
                showConfirmDialog(this, "You have to write an email.")
            }
            TextUtils.isEmpty(binding.passwordLogIn.text.toString()) -> {
                showConfirmDialog(this, "You have to write a password.")
            }
            else -> {
                handleFirebaseSaving()
            }
        }
    }

    private fun handleFirebaseSaving() {
        val email: String = binding.emailLogIn.text.toString()
        val password: String = binding.passwordLogIn.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LogInActivity, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra(
                        USER_ID,
                        FirebaseAuth.getInstance().currentUser!!.uid
                    )
                    intent.putExtra(EMAIL_ID, email)
                    startActivity(intent)
                    finish()
                } else {
                    showConfirmDialog(this, "You have to write correct email and password.")
                }
            }
    }
}
