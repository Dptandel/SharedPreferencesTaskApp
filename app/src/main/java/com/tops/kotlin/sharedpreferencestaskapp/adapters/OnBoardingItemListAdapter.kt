package com.tops.kotlin.sharedpreferencestaskapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.tops.kotlin.sharedpreferencestaskapp.databinding.OnboardingItemLayoutBinding
import com.tops.kotlin.sharedpreferencestaskapp.models.OnBoardingItem

class OnBoardingItemListAdapter(
    var context: Context, var onBoardingItemList: MutableList<OnBoardingItem>
) : PagerAdapter() {

    override fun getCount(): Int {
        return onBoardingItemList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            OnboardingItemLayoutBinding.inflate(LayoutInflater.from(context), container, false)
        val onBoardingItem = onBoardingItemList[position]

        binding.ivThumbnail.setImageResource(onBoardingItem.image)
        binding.tvTitle.text = onBoardingItem.title
        binding.tvDescription.text = onBoardingItem.description

        // add item view to container
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}