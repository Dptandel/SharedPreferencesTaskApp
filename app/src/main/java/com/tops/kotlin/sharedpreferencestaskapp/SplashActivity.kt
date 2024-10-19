package com.tops.kotlin.sharedpreferencestaskapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
                // Onboarding is completed, redirect to LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                // Onboarding is not completed, redirect to OnBoardingActivity
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }
            finish() // Close the splash screen
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
}
