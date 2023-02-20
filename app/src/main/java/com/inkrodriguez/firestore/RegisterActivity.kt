package com.inkrodriguez.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.inkrodriguez.firestore.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.btnRegister.setOnClickListener {
            var email = binding.editUsername.text.toString()
            var senha = binding.editPassword.text.toString()

            if(email.isEmpty() || senha.isEmpty()){
                Toast.makeText(this, "Preencha os campos", Toast.LENGTH_SHORT).show()
            }else {
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                        binding.editUsername.setText("")
                        binding.editPassword.setText("")
                    }
                }.addOnFailureListener{
                    val msgErro = when(it){
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres."
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email válido."
                        is FirebaseAuthUserCollisionException -> "Esta conta já está cadastrada."
                        is FirebaseNetworkException -> "Sem conexão com a internet."
                        else -> "Erro ao cadastrar usuário."
                    }

                    Toast.makeText(this, msgErro, Toast.LENGTH_SHORT).show()

                }
            }
        }

    }
}