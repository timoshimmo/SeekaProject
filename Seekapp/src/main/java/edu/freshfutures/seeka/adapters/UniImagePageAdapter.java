package edu.freshfutures.seeka.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 5/1/2016.
 */
public class UniImagePageAdapter extends PagerAdapter {


    private Context mContext;
    int[] imageId;

    public UniImagePageAdapter(Context context, int[] uniImages) {
        mContext = context;
        this.imageId = uniImages;
    }

    @Override
    public int getCount() {
        return imageId.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.image_views, container, false);

        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imgUniImages);
        Glide.with(mContext).load(imageId[position]).crossFade().into(imageView);

        container.addView(viewItem);

        return viewItem;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
