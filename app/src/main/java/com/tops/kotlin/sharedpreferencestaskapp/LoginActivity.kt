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

        // Check if the user is already logged in
        userLoggedIn()

        binding.loginBtn.setOnClickListener {
            val email = binding.edtEmailAddress.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            // Check if the credentials are correct
            val sharedPref: SharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE)
            val savedEmail = sharedPref.getString("email", null)
            val savedPassword = sharedPref.getString("password", null)

            if (email == savedEmail && password == savedPassword) {
                // Login successful, redirect to HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish() // Close the login screen
            } else {
                // Show error if credentials are incorrect
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // Function to mark user logged in as complete and store the value in SharedPreferences
    private fun userLoggedIn() {
        val sharedPref = getSharedPreferences("appPreferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", true)
            apply() // Apply changes asynchronously
        }

        // After completing onboarding, redirect to the login screen
        startActivity(Intent(this, HomeActivity::class.java))
        finish() // Close onboarding screen
    }
}