package com.tops.kotlin.sharedpreferencestaskapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Add a log to check if onboardingComplete flag is being read correctly
        Handler(Looper.getMainLooper()).postDelayed({
            if (isOnboardingComplete()) {
                // Onboarding is complete, now check if the user is logged in
                checkLoginStatus()
            } else {
                // Onboarding is not completed, redirect to OnBoardingActivity
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish() // Close the splash screen
            }
        }, 2000) // 2 second delay
    }

    // Function to check if onboarding is complete using SharedPreferences
    private fun isOnboardingComplete(): Boolean {
        val sharedPref = getSharedPreferences("appPreferences", Context.MODE_PRIVATE)
        val isComplete = sharedPref.getBoolean("onboardingComplete", false)

        // Log the value of onboardingComplete for debugging
        Log.d("TAG", "Onboarding complete status: $isComplete")

        return isComplete
    }

    // Function to check if the user is logged in
    private fun checkLoginStatus() {
        val sharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // User is logged in, navigate to the home screen
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Redirect to the login screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
