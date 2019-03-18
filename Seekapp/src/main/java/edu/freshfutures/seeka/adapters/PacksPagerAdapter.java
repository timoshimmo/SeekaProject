package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.freshfutures.seeka.LayoutModels;

/**
 * Created by tokmang on 4/18/2016.
 */
public class PacksPagerAdapter extends PagerAdapter {

    LayoutInflater inflater;

    private Context mContext;

    public PacksPagerAdapter(Context context) {
        mContext = context;
    }

    public Object instantiateItem(ViewGroup container, int position) {

        LayoutModels modelObject = LayoutModels.values()[position];

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), container, false);

        container.addView(layout);
       // View enrolmentLayout = inflater.inflate(R.layout.enrolment_layout, null);
       // View courseLayout = inflater.inflate(R.layout.courses_layout, null);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return LayoutModels.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
