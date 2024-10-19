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
            // Clear the user data from SharedPreferences
            with(sharedPref.edit()) {
                clear() // Clear all data
                apply() // Apply changes
            }

            // Redirect to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close HomeActivity
        }
    }
}