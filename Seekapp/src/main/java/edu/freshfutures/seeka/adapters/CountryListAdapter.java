package edu.freshfutures.seeka.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import edu.freshfutures.seeka.CountryDetailsActivity;
import edu.freshfutures.seeka.CountryInfoActivity;
import edu.freshfutures.seeka.OverscrollListView;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.SearchByCountryActivity;

/**
 * Created by tokmang on 6/30/2016.
 */
public class CountryListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Activity activity;
    private ArrayList<HashMap<String, int[]>> countryList;
    private HashMap<String, int[]> countryInfo;


    public CountryListAdapter(Activity a, ArrayList<HashMap<String, int[]>> cl) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.country_layout_structure, parent, false);

            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.imgSearchCityMonuments);
            holder.tvCountry = (TextView) convertView.findViewById(R.id.txtSearchCountry);
            holder.searchBody = (RelativeLayout) convertView.findViewById(R.id.searchBody);
            holder.imgSearchCourses = (ImageView) convertView.findViewById(R.id.img_search_courses);

            convertView.setTag(holder);

        }

        else {

            holder = (Holder) convertView.getTag();

        }

        countryInfo = countryList.get(position);

        holder.tvCountry.setText(activity.getResources().getString(countryInfo.get(CountryInfoActivity.COUNTRY_NAME)[position]));

    //    Glide.with(activity).load(activity.getResources().getDrawable(countryInfo.get(CountryInfoActivity.COUNTRY_IMAGE)[position]))
             //   .into(holder.img);

     //   Glide.with(activity).load(activity.getResources().getDrawable(countryInfo.get(CountryInfoActivity.COUNTRY_BACKGROUND)[position]))
            //    .into(holder.imgSearchCourses);
        holder.img.setImageDrawable(activity.getResources().getDrawable(countryInfo.get(CountryInfoActivity.COUNTRY_IMAGE)[position]));
        holder.imgSearchCourses.setImageDrawable(activity.getResources().getDrawable(countryInfo.get(CountryInfoActivity.COUNTRY_BACKGROUND)[position]));
        holder.searchBody.setTag(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, CountryDetailsActivity.class);
                intent.putExtra("POSITION", position);
                intent.putExtra("COUNTRYNAME", holder.tvCountry.getText().toString());
                activity.startActivity(intent);

            }
        });

        return convertView;
    }

    public class Holder {
        ImageView img;
        TextView tvCountry;
        RelativeLayout searchBody;
        ImageView imgSearchCourses;
    }

}
