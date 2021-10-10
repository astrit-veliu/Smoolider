package com.av.smoothslider

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar
import com.astritveliu.boom.utils.BoomUtils
import com.av.smoothslider.R.anim
import com.av.smoothslider.R.style
import com.av.smoothslider.databinding.ActivityMainBinding
import com.av.smoothviewpager.smoolider.PageModel
import com.av.smoothviewpager.utils.TextFactory
import com.av.smoothviewpager.utils.openWebPage

/**
 * Created by Astrit Veliu on 09,September,2019
 * * updated on 06, October, 2021
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var is_autoplay = false
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        initWidgets()
        binding.viewpager.setAdapter(getDemoData())
        binding.viewpager.setOnPageClick { page ->
            binding.viewpager.removePage(page)
        }
        binding.viewpager.setOnPageSelected { position, page ->
            manageWidgetsOnSwipe(position, page)
        }
        binding.animationView.setOnClickListener { view ->
            manageAutoplay()
            Snackbar.make(view, " ▶ Autoplay :  $is_autoplay", Snackbar.LENGTH_LONG).show()
        }
        binding.githubImageView.setOnClickListener { openWebPage("https://github.com/astrit-veliu") }

        binding.titleTextView.setOnClickListener {
            binding.viewpager.addPage(getSinglePage())
        }
    }

    private fun initWidgets() {
        supportActionBar?.hide()
        BoomUtils.boomAll(binding.titleTextView, binding.subTitleTextView)
        binding.positionTextSwitcher.setFactory(TextFactory(style.CustomTextView, true, applicationContext))
    }

    private fun manageWidgetsOnSwipe(position: Int, page: PageModel) {
        page.data?.let { selectedData ->
            if (selectedData is DemoDataModel) {
                val animH = intArrayOf(anim.slide_in_right, anim.slide_out_left)
                val animV = intArrayOf(anim.slide_in_top, anim.slide_out_bottom)
                val left2right = position < currentPosition
                if (left2right) {
                    animH[0] = anim.slide_in_left
                    animH[1] = anim.slide_out_right
                    animV[0] = anim.slide_in_bottom
                    animV[1] = anim.slide_out_top
                }
                binding.positionTextSwitcher.setInAnimation(this@MainActivity, animH[0])
                binding.positionTextSwitcher.setOutAnimation(this@MainActivity, animH[1])
                binding.positionTextSwitcher.setText(
                    applicationContext.getString(R.string.position_text, (position + 1), binding.viewpager.getItemCount())
                )
                binding.titleTextView.visibility = View.INVISIBLE
                binding.titleTextView.startAnimation(AnimationUtils.loadAnimation(applicationContext, anim.fade_in))
                binding.titleTextView.visibility = View.VISIBLE
                binding.titleTextView.setText(selectedData.name)
                binding.subTitleTextView.visibility = View.INVISIBLE
                binding.subTitleTextView.startAnimation(AnimationUtils.loadAnimation(applicationContext, anim.fade_in))
                binding.subTitleTextView.visibility = View.VISIBLE
                binding.subTitleTextView.setText(selectedData.description)
                currentPosition = position
            }
        }
    }

    private fun manageAutoplay() {
        binding.animationView.playAnimation()
        if (is_autoplay) binding.viewpager.stopAutoplayViewPager()
        else binding.viewpager.startAutoplay()
        is_autoplay = !is_autoplay
    }
}