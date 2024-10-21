package com.tops.kotlin.sharedpreferencestaskapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tops.kotlin.sharedpreferencestaskapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val email = binding.edtEmailAddress.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            // Call the login function
            login(email, password)
        }

        binding.txtCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    //login function
    private fun login(email: String, password: String) {
        // Check if the credentials are correct
        val sharedPref: SharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE)
        val savedEmail = sharedPref.getString("email", null)
        val savedPassword = sharedPref.getString("password", null)

        if (email == savedEmail && password == savedPassword) {
            // Save login status in SharedPreferences
            val editor = sharedPref.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.apply()

            // Login successful, redirect to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Close the login screen
        } else {
            // Show error if credentials are incorrect
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }
}