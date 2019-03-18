package edu.freshfutures.seeka.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import edu.freshfutures.seeka.OverscrollListView;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.SearchByCountryActivity;


public class CustomAdapter extends BaseAdapter implements Animation.AnimationListener {

    private static LayoutInflater inflater = null;
    Activity activity;
    private ArrayList<HashMap<String, int[]>> countryList;
    private HashMap<String, int[]> countryCourses;
    Animation slidein;
    Animation slideout;

    private static LruCache<String, Bitmap> mMemoryCache = null;
    private static int cacheSize = 1024 * 1024 * 10;

    public CustomAdapter(Activity a, ArrayList<HashMap<String, int[]>> cl) {
        this.activity = a;
        this.countryList = cl;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return this.countryList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final Holder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.search_list_structure, parent, false);

            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.imgSearchCityMonuments);
            holder.tvCountry = (TextView) convertView.findViewById(R.id.txtSearchCountry);
            holder.tvNumAvailable = (TextView) convertView.findViewById(R.id.txtNumCoursesAvailable);
          //  holder.btnSearchResults = (ImageButton) convertView.findViewById(R.id.imgBtnSearchIcon);
            holder.searchBody = (RelativeLayout) convertView.findViewById(R.id.searchBody);
            holder.imgSearchCourses = (ImageView) convertView.findViewById(R.id.img_search_courses);
            holder.btnSearchInfo = (ImageButton) convertView.findViewById(R.id.btnSearchInfo);

            convertView.setTag(holder);
        }

        else {

            holder = (Holder) convertView.getTag();

        }

        slidein = AnimationUtils.loadAnimation(activity, R.anim.slide_left);
        slidein.setAnimationListener(this);

        slideout = AnimationUtils.loadAnimation(activity, R.anim.slide_out);
        slideout.setAnimationListener(this);

        if (mMemoryCache == null) {

            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return (bitmap.getRowBytes() * bitmap.getHeight());
                }
            };
        }

        countryCourses = countryList.get(position);

        holder.pos = position;
        holder.tvCountry.setText(activity.getResources().getString(countryCourses.get(OverscrollListView.COUNTRY_NAME)[position]));
        holder.tvNumAvailable.setText(activity.getResources().getString(countryCourses.get(OverscrollListView.COURSE_AVAILABLE)[position]));
        holder.tvNumAccepted.setText(activity.getResources().getString(countryCourses.get(OverscrollListView.COURSE_ACCEPTED)[position]));
        holder.imgSearchCourses.setImageDrawable(activity.getResources().getDrawable(countryCourses.get(OverscrollListView.COUNTRY_BACKGROUND)[position]));
        holder.img.setImageDrawable(activity.getResources().getDrawable( countryCourses.get(OverscrollListView.COUNTRY_IMAGE)[position]));
        holder.btnSearchInfo.setImageDrawable(activity.getResources().getDrawable( countryCourses.get(OverscrollListView.COURSE_INFO)[position]));
        holder.searchBody.setTag(position);
        holder.tvCountry.setTag(activity.getResources().getString(countryCourses.get(OverscrollListView.COUNTRY_NAME)[position]));

        holder.btnSearchResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String country = (String) holder.tvCountry.getTag();

                Intent intent = new Intent(activity, SearchByCountryActivity.class);
                intent.putExtra("COUNTRY", country);
                activity.startActivity(intent);

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String country = (String) holder.tvCountry.getTag();

                Intent intent = new Intent(activity, SearchByCountryActivity.class);
                intent.putExtra("COUNTRY", country);
                activity.startActivity(intent);

            }
        });

        return convertView;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public class Holder {
        ImageView img;
        TextView tvCountry;
        TextView tvNumAvailable;
        TextView tvNumAccepted;
        ImageButton btnSearchResults;
        ImageButton btnSearchInfo;
        RelativeLayout searchBody;
        ImageView imgSearchCourses;
        Bitmap bitmap;
        int pos;
    }

    private class imageDownloaderTask extends AsyncTask<Holder,Void,Holder> {

        @Override
        protected Holder doInBackground(Holder... params) {

            Holder viewHolder = params[0];
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 1;
            viewHolder.bitmap = BitmapFactory.decodeResource(activity.getResources(),
                    countryCourses.get(OverscrollListView.COUNTRY_BACKGROUND)[viewHolder.pos], options);


            return viewHolder;
        }

        @Override
        protected void onPostExecute(Holder result) {
            // TODO Auto-generated method stub

            if(result.bitmap != null) {
                result.imgSearchCourses.setImageBitmap(result.bitmap);
            }

        }

    }

    private class iconDownloaderTask extends AsyncTask<Holder,Void,Holder> {

        @Override
        protected Holder doInBackground(Holder... params) {

            Holder viewHolder = params[0];
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 1;
            viewHolder.bitmap = BitmapFactory.decodeResource(activity.getResources(),
                    countryCourses.get(OverscrollListView.COUNTRY_IMAGE)[viewHolder.pos], options);

            return viewHolder;
        }

        @Override
        protected void onPostExecute(Holder result) {
            // TODO Auto-generated method stub

            if(result.bitmap != null) {
                result.img.setImageBitmap(result.bitmap);
            }

        }

    }

}