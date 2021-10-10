package com.av.smoothviewpager.smoolider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewpager.widget.PagerAdapter
import com.av.smoothviewpager.databinding.SmooliderItemViewBinding
import com.av.smoothviewpager.utils.decodeSampledBitmapFromResource
import com.bumptech.glide.Glide

/**
 * Created by Astrit Veliu on 06, October, 2021
 */
class SmoothPagerAdapter(private val mContext: Context) : PagerAdapter() {

    private var feedItemList: List<PageModel>? = null

    private var onItemSelected: ((selectedItem: PageModel?) -> Unit)? = null

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val currentItem = feedItemList?.get(position)
        val binding = SmooliderItemViewBinding.inflate(LayoutInflater.from(mContext), container, false)
        binding.detailsChipTextView.isVisible = currentItem?.descriptionChipText != null
        currentItem?.descriptionChipText?.let { binding.detailsChipTextView.text = it }
        currentItem?.imageResource?.let { imageId ->
            binding.cardImageView.setImageBitmap(mContext.decodeSampledBitmapFromResource(imageId, 800, 650))
        }
        currentItem?.imageUrl?.let { imageUrl -> Glide.with(mContext).load(imageUrl).into(binding.cardImageView) }
        binding.root.setOnClickListener { onItemSelected?.invoke(currentItem) }
        container.addView(binding.root)
        return binding.root
    }

    fun setPages(feedItemList: List<PageModel>?){
        this.feedItemList = feedItemList
        notifyDataSetChanged()
    }

    override fun getCount(): Int = feedItemList?.size ?: 0

    override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
        container.removeView(item as View)
    }

    override fun isViewFromObject(view: View, itemObject: Any): Boolean = view == itemObject

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

    fun setOnItemSelected(onItemSelected: ((selectedItem: PageModel?) -> Unit)?) {
        this.onItemSelected = onItemSelected
    }
}