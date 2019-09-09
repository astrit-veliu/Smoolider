package com.av.smoothslider.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.astritveliu.boom.Boom;
import com.av.smoothslider.R;
import com.av.smoothslider.data.Model_Smoolider;
import com.av.smoothslider.data.Smoolider_Adapter;
import com.av.smoothviewpager.Smoolider.SmoothViewpager;
import com.av.smoothviewpager.utils.Txt_Factory;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.av.smoothviewpager.utils.Smoolider_Utils.autoplay_viewpager;
import static com.av.smoothviewpager.utils.Smoolider_Utils.stop_autoplay_ViewPager;
import static com.av.smoothviewpager.utils.Smoolider_Utils.openWebPage;

/**
 * Created by Astrit Veliu on 09,September,2019
 */
public class MainActivity extends AppCompatActivity {

    private final int[] pics = {R.drawable.img01, R.drawable.img02, R.drawable.img03, R.drawable.img04, R.drawable.img05};
    private final int[] descriptions = {R.string.description_1, R.string.description_2, R.string.description_3, R.string.description_4, R.string.description_5};
    private final int[] titles = {R.string.title_1, R.string.title_2, R.string.title_3, R.string.title_4, R.string.title_5};
    private final int[] url = {R.string.position_1, R.string.position_2, R.string.position_3, R.string.position_4, R.string.position_5};
    private final String[] car_hp = {"S 65", "400 4Matic", "GT 63S", "G 63", "C 63S"};
    private final String[] position = {"1 / 5", "2 / 5", "3 / 5", "4 / 5", "5 / 5"};

    private TextSwitcher txt_swithcher_position;
    private TextView txt_title,txt_subtitle;
    private boolean is_autoplay = false;
    private int currentPosition;
    private SmoothViewpager viewPager;
    private ImageView img_github;
    private LottieAnimationView animationView;
    private List<Model_Smoolider> feedItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        init_widgets();
        generate_items();

        viewPager.setAdapter( new Smoolider_Adapter(feedItemList,getApplicationContext()));

        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manage_autoplay();
                Snackbar.make(view, " ▶ Autoplay :  "+is_autoplay, Snackbar.LENGTH_LONG).show();
            }
        });


        img_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(getApplicationContext(),"https://github.com/astrit-veliu");
            }
        });



        //use this method if you want to interact with other widgets
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {

            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(final int position) {
                // handle operations with current page
                manage_widgets_on_swipe(position);

            }
        });
    }

    private void init_widgets() {

        getSupportActionBar().hide();

        viewPager = (SmoothViewpager)findViewById(R.id.smoolider);
        img_github = (ImageView)findViewById(R.id.img_github);
        txt_swithcher_position = (TextSwitcher) findViewById(R.id.txt_swithcher_position);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_subtitle = (TextView) findViewById(R.id.txt_subtitle);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setAnimation(R.raw.autoplay_animation);

        new Boom(txt_title);
        new Boom(txt_subtitle);

        txt_swithcher_position.setFactory(new Txt_Factory(R.style.CustomTextView, true,getApplicationContext()));
        txt_swithcher_position.setCurrentText(position[0]);
        txt_title.setText(titles[0]);

        manage_widgets_on_swipe(0);
    }

    private void generate_items(){
        feedItemList = new ArrayList<>();
        for(int i=0;i<pics.length;i++){
            Model_Smoolider gift = new Model_Smoolider();
            gift.setImage(pics[i]);
            gift.setName(titles[i]);
            gift.setUrl(url[i]);
            gift.setDescription(descriptions[i]);
            gift.setPoints(car_hp[i]);

            feedItemList.add(gift);
        }
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

        txt_swithcher_position.setInAnimation(MainActivity.this, animH[0]);
        txt_swithcher_position.setOutAnimation(MainActivity.this, animH[1]);
        txt_swithcher_position.setText(position[pos % position.length]);

        txt_title.setVisibility(View.INVISIBLE);
        txt_title.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(titles[pos % titles.length]);

        txt_subtitle.setVisibility(View.INVISIBLE);
        txt_subtitle.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
        txt_subtitle.setVisibility(View.VISIBLE);
        txt_subtitle.setText(descriptions[pos % descriptions.length]);

        currentPosition = pos;
    }


    private void manage_autoplay(){
        animationView.playAnimation();
        if(is_autoplay){
            is_autoplay = false;
            stop_autoplay_ViewPager();
        } else {
            is_autoplay = true;
            autoplay_viewpager(viewPager,feedItemList.size());
        }

    }
}
