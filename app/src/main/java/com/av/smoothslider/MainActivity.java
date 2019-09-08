package com.av.smoothslider;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.astritveliu.boom.Boom;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.rishabhharit.roundedimageview.RoundedImageView;

import static com.av.smoothslider.ImageUtils.decodeSampledBitmapFromResource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 8000; // time in milliseconds between successive task executions.

    private final int[] pics = {R.drawable.img01, R.drawable.img02, R.drawable.img03, R.drawable.img04, R.drawable.img05};
    private final int[] descriptions = {R.string.description_1, R.string.description_2, R.string.description_3, R.string.description_4, R.string.description_5};
    private final int[] titles = {R.string.title_1, R.string.title_2, R.string.title_3, R.string.title_4, R.string.title_5};
    private final int[] url = {R.string.position_1, R.string.position_2, R.string.position_3, R.string.position_4, R.string.position_5};
    private final String[] car_hp = {"S 65", "400 4Matic", "GT 63S", "G 63", "C 63S"};
    private final String[] position = {"1 / 5", "2 / 5", "3 / 5", "4 / 5", "5 / 5"};

    private TextSwitcher positionSwitcher;

    private TextView country1TextView,txt_subtitle;
    private int currentPosition;
    //private SmoothViewpager viewPager;
    private ViewPager viewPager;

    private List<Model_Gift> feedItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initRecyclerView();
        initCountryText();
        initSwitchers();

        autoplay_viewpager(viewPager,feedItemList.size());
        manage_widgets_on_swipe(0);
    }

    private void initRecyclerView() {

        feedItemList = new ArrayList<>();
        for(int i=0;i<pics.length;i++){
            Model_Gift gift = new Model_Gift();
            gift.setImage(pics[i]);
            gift.setName(titles[i]);
            gift.setUrl(url[i]);
            gift.setDescription(descriptions[i]);
            gift.setPoints(car_hp[i]);

            feedItemList.add(gift);
        }

        viewPager = (ViewPager)findViewById(R.id.recycler_view);
        //viewPager.setScrollDurationFactor(3);
        ViewPager_Cards_Adapter pagerAdapter = new ViewPager_Cards_Adapter();
        viewPager.setAdapter(pagerAdapter);

        //Interpolator sInterpolator = new DecelerateInterpolator();

        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            //FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), sInterpolator);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext());
            // scroller.setFixedDuration(5000);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        Resources r = getResources();
        int partialWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());

        int viewPagerPadding = partialWidth + pageMargin;

        viewPager.setPageMargin(pageMargin);
        viewPager.setPadding(viewPagerPadding, 0, viewPagerPadding, 0);
        viewPager.setPageTransformer(false, new CardsPagerTransformerBasic(0, 1,  0.6f));

        //use this method if you want to interact with other widgets
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {

            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(final int position) {
                // Check if this is the page you want.
                manage_widgets_on_swipe(position);

            }
        });





    }



    private void initSwitchers() {
        positionSwitcher = (TextSwitcher) findViewById(R.id.ts_temperature);
        positionSwitcher.setFactory(new MainActivity.TextViewFactory(R.style.CustomTextView, true));
        positionSwitcher.setCurrentText(position[0]);
    }

    private void initCountryText() {
        country1TextView = (TextView) findViewById(R.id.tv_country_1);
        txt_subtitle = (TextView) findViewById(R.id.txt_subtitle);

        new Boom(country1TextView);
        new Boom(txt_subtitle);

        country1TextView.setText(titles[0]);
    }





    private void manage_widgets_on_swipe(int pos) {
        int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[] {R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }

        positionSwitcher.setInAnimation(MainActivity.this, animH[0]);
        positionSwitcher.setOutAnimation(MainActivity.this, animH[1]);
        positionSwitcher.setText(position[pos % position.length]);

        country1TextView.setVisibility(View.INVISIBLE);
        country1TextView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
        country1TextView.setVisibility(View.VISIBLE);
        country1TextView.setText(titles[pos % titles.length]);

        txt_subtitle.setVisibility(View.INVISIBLE);
        txt_subtitle.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
        txt_subtitle.setVisibility(View.VISIBLE);
        txt_subtitle.setText(descriptions[pos % descriptions.length]);

        currentPosition = pos;
    }



    private class TextViewFactory implements  ViewSwitcher.ViewFactory {

        @StyleRes
        final int styleId;
        final boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextView textView = new TextView(MainActivity.this);

            if (center) {
                textView.setGravity(Gravity.CENTER);
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(MainActivity.this, styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }

    }



    public class ViewPager_Cards_Adapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        @Override
        public int getCount() {
            return feedItemList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {


            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.structure_gift, container, false);

            RoundedImageView img_gift = (RoundedImageView) view.findViewById(R.id.img_gift);
            TextView txt_points = (TextView) view.findViewById(R.id.txt_points);

            new Boom(txt_points);//optional

            final Model_Gift card_gift = feedItemList.get(position);

            txt_points.setText(card_gift.getPoints());
            img_gift.setImageBitmap(decodeSampledBitmapFromResource(getResources(),card_gift.getImage(), 800, 650));

            img_gift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Slider action
                    Snackbar.make(v, "Click the pin for more options", Snackbar.LENGTH_LONG).show();
                }
            });


            txt_points.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = getString(card_gift.getUrl())+"";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem (ViewGroup container,int position, Object object){
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject (View view, Object object){
            return view == object;
        }
    }



    private void autoplay_viewpager(final ViewPager vPager, final int num_pages){

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == num_pages-1) {
                    currentPage = 0;
                }

                vPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }


}
