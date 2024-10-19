package com.tops.kotlin.sharedpreferencestaskapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager.LayoutParams
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.tops.kotlin.sharedpreferencestaskapp.adapters.OnBoardingItemListAdapter
import com.tops.kotlin.sharedpreferencestaskapp.databinding.ActivityOnBoardingBinding
import com.tops.kotlin.sharedpreferencestaskapp.models.OnBoardingItem

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private var onboardingItemList = mutableListOf<OnBoardingItem>()
    private lateinit var onBoardingItemAdapter: OnBoardingItemListAdapter
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add items to the onboarding list
        onboardingItemList.apply {
            add(
                OnBoardingItem(
                    R.drawable.illustration1,
                    "Search",
                    "Lorem ipsum dolor sit amet. Sed maxime nihil est voluptas quos ut architecto tenetur."
                )
            )
            add(
                OnBoardingItem(
                    R.drawable.illustration2,
                    "Order",
                    "Lorem ipsum dolor sit amet. Sed maxime nihil est voluptas quos ut architecto tenetur."
                )
            )
            add(
                OnBoardingItem(
                    R.drawable.illustration3,
                    "Delivery",
                    "Lorem ipsum dolor sit amet. Sed maxime nihil est voluptas quos ut architecto tenetur."
                )
            )
        }

        onBoardingItemAdapter = OnBoardingItemListAdapter(this, onboardingItemList)
        binding.viewPager.adapter = onBoardingItemAdapter
        binding.viewPager.currentItem = currentIndex
        updateIndicator(currentIndex)
        binding.btnPrevious.visibility = View.INVISIBLE

        binding.btnPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                binding.viewPager.currentItem = currentIndex
            }
        }

        binding.btnNext.setOnClickListener {
            if (currentIndex < onboardingItemList.size - 1) {
                currentIndex++
                binding.viewPager.currentItem = currentIndex
            } else {
                // User has finished onboarding, mark it complete and redirect to LoginActivity
                completeOnboarding()
            }
        }

        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentIndex = position

                // Show or hide the Previous button based on the current index
                binding.btnPrevious.visibility =
                    if (currentIndex == 0) View.INVISIBLE else View.VISIBLE

                // Change Next button text based on current page
                binding.btnNext.text =
                    if (currentIndex < onBoardingItemAdapter.count - 1) "Next" else "Finish"
                updateIndicator(currentIndex)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    // Function to mark onboarding as complete and store the value in SharedPreferences
    private fun completeOnboarding() {
        val sharedPref = getSharedPreferences("appPreferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("onboardingComplete", true)
            apply() // Apply changes asynchronously
        }

        // Log to confirm that onboardingComplete is being set
        Log.d("TAG", "Onboarding complete flag set to true")

        // After completing onboarding, redirect to the login screen
        startActivity(Intent(this, LoginActivity::class.java))
        finish() // Close onboarding screen
    }

    // Function to update the indicator
    private fun updateIndicator(index: Int) {
        binding.layoutIndicator.removeAllViews()
        for (i in 0 until onBoardingItemAdapter.count) {
            val imageView = ImageView(this)
            imageView.setImageResource(if (index == i) R.drawable.active_indicator else R.drawable.inactive_indicator)
            val params =
                LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, 12, 0)
            binding.layoutIndicator.addView(imageView, params)
        }
    }
}