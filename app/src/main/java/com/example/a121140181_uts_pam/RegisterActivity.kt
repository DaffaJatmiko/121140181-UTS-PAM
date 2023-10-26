package com.example.a121140181_uts_pam

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a121140181_uts_pam.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        val progressBar = binding.progressBar
        val loginNowTextView = binding.loginNow
        val registerButton = binding.btnRegister

        loginNowTextView.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val username = binding.username.text.toString()
            val github = binding.github.text.toString()
            val nim = binding.nim.text.toString()

            progressBar.visibility = View.VISIBLE

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(username) || TextUtils.isEmpty(github) || TextUtils.isEmpty(nim)
            ) {
                Toast.makeText(this@RegisterActivity, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: database.child("Users").push().key!!
                        val user = User(username, password, github, nim, email)
                        database.child("Users").child(userId).setValue(user)
                            .addOnSuccessListener {
                                Log.d("FirebaseDatabase", "Data berhasil ditulis ke Realtime Database")
                                Toast.makeText(
                                    baseContext,
                                    "Registrasi berhasil, akun dibuat.",
                                    Toast.LENGTH_SHORT
                                ).show()


                            }
                            .addOnFailureListener { e ->
                                Log.e("FirebaseDatabase", "Gagal menyimpan data ke database.", e)
                                Toast.makeText(
                                    baseContext,
                                    "Gagal menyimpan data ke database: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            baseContext,
                            "Registrasi gagal.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}