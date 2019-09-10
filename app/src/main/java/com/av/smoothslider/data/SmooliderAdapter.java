package com.av.smoothslider.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.astritveliu.boom.Boom;
import com.av.smoothslider.R;
import com.google.android.material.snackbar.Snackbar;
import com.rishabhharit.roundedimageview.RoundedImageView;

import java.util.List;

import static com.av.smoothviewpager.utils.Smoolider_Utils.decodeSampledBitmapFromResource;
import static com.av.smoothviewpager.utils.Smoolider_Utils.openWebPage;


/**
 * Created by Astrit Veliu on 09,September,2019
 */
public class SmooliderAdapter extends PagerAdapter {
    private Context mContext;
    private List<ModelSmoolider> feedItemList;

    public SmooliderAdapter(List<ModelSmoolider> feedItemList, Context mContext) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
    }

    @Override
    public int getCount() {
        return feedItemList.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final ModelSmoolider card_gift = feedItemList.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.structure_gift, container, false);

        RoundedImageView img_slider = (RoundedImageView) view.findViewById(R.id.img_slider);
        TextView txt_details = (TextView) view.findViewById(R.id.txt_details);
        new Boom(txt_details);//optional

        txt_details.setText(card_gift.getPoints());
        img_slider.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(),card_gift.getImage(), 800, 650));

        img_slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Slider action
                Snackbar.make(v, "Click the pin for more options", Snackbar.LENGTH_LONG).show();
            }
        });


        txt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(mContext,mContext.getString(card_gift.getUrl())+"");
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
        return view.equals(object);
    }
}