package com.example.lostdogsandcats.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.lostdogsandcats.databinding.ActivitySignUpBinding
import com.example.lostdogsandcats.ui.main.MainActivity
import com.example.lostdogsandcats.utils.EMAIL_ID
import com.example.lostdogsandcats.utils.USER_ID
import com.example.lostdogsandcats.utils.VALID_EMAIL_REGEX
import com.example.lostdogsandcats.utils.showConfirmDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * SignUpActivity
 */
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        binding.logInButton.setOnClickListener { onBackPressed() }
        binding.signUpButton.setOnClickListener { handleSignUp() }
    }

    private fun handleSignUp() {
        when {
            TextUtils.isEmpty(binding.email.text.toString()) -> {
                showConfirmDialog(this, "You have to write an email.")
            }
            !VALID_EMAIL_REGEX.matcher(binding.email.text.toString()).matches() -> {
                showConfirmDialog(this, "You have to write valid email.")
            }
            TextUtils.isEmpty(binding.password.text.toString()) || binding.password.text.length < 6 -> {
                showConfirmDialog(this, "Password must be at least 6 characters long.")
            }
            else -> handleFirebaseSignUp()
        }
    }

    private fun handleFirebaseSignUp() {
        val email: String = binding.email.text.toString()
        val passwordToSave: String = binding.password.text.toString()
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, passwordToSave)
            .addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        val fireBaseUser: FirebaseUser = task.result!!.user!!
                        val intent =
                            Intent(this@SignUpActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(USER_ID, fireBaseUser.uid)
                        intent.putExtra(EMAIL_ID, email)
                        startActivity(intent)
                        finish()
                    } else {
                        showConfirmDialog(
                            this,
                            "Looks like this email is already used. Try another email."
                        )
                    }
                })
    }
}
