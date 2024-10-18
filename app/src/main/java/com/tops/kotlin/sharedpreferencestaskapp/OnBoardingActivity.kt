package com.tops.kotlin.sharedpreferencestaskapp

import android.content.Intent
import android.os.Bundle
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

        //add item to list
        onboardingItemList.add(
            OnBoardingItem(
                R.drawable.illustration1,
                "Search",
                "Lorem ipsum dolor sit amet. Sed maxime nihil est voluptas quos ut architecto tenetur."
            )
        )
        onboardingItemList.add(
            OnBoardingItem(
                R.drawable.illustration2,
                "Order",
                "Lorem ipsum dolor sit amet. Sed maxime nihil est voluptas quos ut architecto tenetur."
            )
        )
        onboardingItemList.add(
            OnBoardingItem(
                R.drawable.illustration3,
                "Delivery",
                "Lorem ipsum dolor sit amet. Sed maxime nihil est voluptas quos ut architecto tenetur."
            )
        )

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
                // Navigate to the login/register activity
                startActivity(Intent(this, LoginActivity::class.java))
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

                if (currentIndex == 0) {
                    binding.btnPrevious.visibility = View.INVISIBLE
                } else {
                    binding.btnPrevious.visibility = View.VISIBLE
                }

                if (currentIndex < onBoardingItemAdapter.count - 1) {
                    binding.btnNext.text = "Next"
                } else {
                    binding.btnNext.text = "Finish"
                }

                updateIndicator(currentIndex)
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })
    }

    private fun updateIndicator(index: Int) {

        binding.layoutIndicator.removeAllViews()

        for (i in 0 until onBoardingItemAdapter.count) {
            var imageView = ImageView(this)

            if (index == i) {
                // active
                imageView.setImageResource(R.drawable.active_indicator)
            } else {
                // inactive
                imageView.setImageResource(R.drawable.inactive_indicator)
            }
            var params = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 12, 0)

            binding.layoutIndicator.addView(imageView, params)
        }

    }
}