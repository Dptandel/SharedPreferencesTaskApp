package com.tops.kotlin.sharedpreferencestaskapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tops.kotlin.sharedpreferencestaskapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve user data from SharedPreferences
        val sharedPref: SharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE)
        val email = sharedPref.getString("email", "No Email")
        val name = sharedPref.getString("name", "No Name")

        // Display user data
        binding.tvUserDetails.text = """
            Name: $name
            Email: $email
        """.trimIndent()

        // Logout button listener
        binding.btnLogout.setOnClickListener {
            // Call the logout function
            logout()
        }
    }

    // Logout function
    private fun logout() {
        // Clear the SharedPreferences
        val sharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Redirect to login screen
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()  // Close the current activity
    }
}