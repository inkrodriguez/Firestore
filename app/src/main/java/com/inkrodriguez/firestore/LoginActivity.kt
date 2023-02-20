package com.inkrodriguez.firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.inkrodriguez.firestore.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var auth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()

        val usuario = FirebaseAuth.getInstance().currentUser

        if (usuario != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            var email = binding.editUsername.text.toString()
            var senha = binding.editPassword.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show()
                }


            }
        }

        binding.btnRegisterLoginActivity.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}