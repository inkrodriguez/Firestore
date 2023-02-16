package com.inkrodriguez.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.inkrodriguez.firestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.btnRegister.setOnClickListener {
            var email = binding.editUsername.text.toString()
            var senha = binding.editPassword.text.toString()

            if(email.isEmpty() || senha.isEmpty()){
                Toast.makeText(this, "preencha os campos", Toast.LENGTH_SHORT).show()
            }else {
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{

                }
            }
        }

    }
}