package com.inkrodriguez.firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.inkrodriguez.firestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var auth = FirebaseAuth.getInstance()
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnGravar.setOnClickListener {
            var nome = binding.editNome.text.toString()
            var sobrenome = binding.editSobrenome.text.toString()
            var cpf = binding.editCpf.text.toString()
            var endereco = binding.editEndereco.text.toString()

            var userMap = hashMapOf(
                "nome" to nome,
                "sobrenome" to sobrenome,
                "cpf" to cpf,
                "endereco" to endereco
            )

            db.collection("users").document(nome).set(userMap).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this, "user cadastrado", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {

            }
        }

        binding.btnLer.setOnClickListener {
            var nome = binding.editNome.text.toString()
            db.collection("users").document(nome).addSnapshotListener { it, error ->
                if (it != null) {
                    var cpf = it.getString("cpf")
                    binding.editCpf.setText(cpf)
                }
            }
        }



        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}