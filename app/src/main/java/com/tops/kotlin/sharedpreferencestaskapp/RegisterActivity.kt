package com.tops.kotlin.sharedpreferencestaskapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tops.kotlin.sharedpreferencestaskapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createAccountBtn.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmailAddress.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            // Call the Register Function
            register(name, email, password)
        }
        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun register(name: String, email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
            // Save user data to SharedPreferences
            val sharedPref: SharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("email", email)
                putString("password", password)
                putString("name", name)
                putBoolean("isLoggedIn", true)  // Set login state to true
                apply()
            }

            // Show success message
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

            // Redirect to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()  // Close RegisterActivity
        } else {
            // Show error message
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }
}