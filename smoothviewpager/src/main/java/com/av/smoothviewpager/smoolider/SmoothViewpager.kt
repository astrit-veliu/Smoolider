package com.av.smoothviewpager.smoolider

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.av.smoothviewpager.utils.FixedSpeedScroller
import android.util.TypedValue
import com.av.smoothviewpager.utils.CardsPagerTransformerBasic
import java.lang.IllegalArgumentException
import java.lang.reflect.Field
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Astrit Veliu on 09,September,2019
 * updated on 06, October, 2021
 */
class SmoothViewpager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    private var onPageClicked: ((page: PageModel) -> Unit)? = null
    private var onPageSelected: ((position: Int, page: PageModel) -> Unit)? = null

    private val baseElevation = 0
    private val raisingElevation = 1
    private val smallerScale = 0.6f
    private val viewpagerAdapter = SmoothPagerAdapter(context)
    private var items: MutableList<PageModel>? = null

    private var delayMs: Long = 500 //delay in milliseconds before task is to be executed
    private var periodMs: Long = 6000 // time in milliseconds between successive task executions.

    private var currentPage = 0
    private var timer: Timer? = null

    init {
        postInitViewPager()
        addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                //do nothing
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //do nothing
            }

            override fun onPageSelected(position: Int) {
                if (position < items?.size ?: 0) items?.get(position)?.let { onPageSelected?.invoke(position, it) }
            }
        })
    }

    fun setAdapter(itemList: List<PageModel>?) {
        if (this.items != itemList) {
            this.items = itemList?.toMutableList()
            itemList?.let { pages ->
                viewpagerAdapter.setPages(pages)
                viewpagerAdapter.setOnItemSelected { page -> page?.let { onPageClicked?.invoke(it) } }
                adapter = viewpagerAdapter
            }
        }
        this.post { itemList?.firstOrNull()?.let { onPageSelected?.invoke(0, it) } }
    }

    fun addPage(page: PageModel) {
        items?.add(page)
        viewpagerAdapter.setPages(items)
        currentItem = getItemCount() - 1
    }

    fun removePage(page: PageModel) {
        if (items?.contains(page) == true) {
            items?.remove(page)
            viewpagerAdapter.setPages(items)
            if (getItemCount() > 0) currentItem = 0
        }
    }

    private fun postInitViewPager() {
        try {
            val viewpager: Class<*> = ViewPager::class.java
            val mScroller: Field = viewpager.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            val scroller = FixedSpeedScroller(context)
            mScroller[this] = scroller
            transformViewpager()
        } catch (e: NoSuchFieldException) {
        } catch (e: IllegalArgumentException) {
        } catch (e: IllegalAccessException) {
        }
    }

    private fun transformViewpager() {
        val r = resources
        val partialWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, r.displayMetrics).toInt()
        val pageMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, r.displayMetrics).toInt()
        val viewPagerPadding = partialWidth + pageMargin
        setPageMargin(pageMargin)
        setPadding(viewPagerPadding, 0, viewPagerPadding, 0)
        setPageTransformer(false, CardsPagerTransformerBasic(baseElevation, raisingElevation, smallerScale))
    }


    fun startAutoplay() {
        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            if (currentPage == getItemCount()) {
                currentPage = 0
            }
            setCurrentItem(currentPage++, true)
        }
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, delayMs, periodMs)
    }

    fun stopAutoplayViewPager() {
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }

    fun setDelay(value: Long) {
        delayMs = value
    }

    fun setPeriod(value: Long) {
        periodMs = value
    }

    fun getItemCount(): Int = items?.size ?: 0

    fun getCurrentPage(): PageModel? = items?.getOrNull(currentItem)

    fun setOnPageClick(onPageClicked: ((page: PageModel) -> Unit)?) {
        this.onPageClicked = onPageClicked
    }

    fun setOnPageSelected(onPageSelected: ((position: Int, page: PageModel) -> Unit)?) {
        this.onPageSelected = onPageSelected
    }
}