package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.ServicesActivity;

/**
 * Created by tokmang on 4/4/2016.
 */
public class ServicesAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    public String[] interestTitle;
    public int[] interestImage;
    public String[] imgDesc;
    public ArrayList<Integer> addAllImages = new ArrayList<>();

    public ServicesAdapter(Context ctx, String[] it, int[] im) {

        this.context = ctx;
        this.interestTitle = it;
        this.interestImage = im;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return this.interestTitle.length;
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

        final Holder holder = new Holder();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.services_list_structure, parent, false);
        }

        holder.interestTitles = (TextView) convertView.findViewById(R.id.txtInterestTitle);
        holder.interestsImages = (ImageView) convertView.findViewById(R.id.imgInterests);

        holder.interestTitles.setText(interestTitle[position]);
        holder.interestsImages.setImageResource(interestImage[position]);

        for(int i=0; i<interestImage.length; i++) {
            addAllImages.add(interestImage[i]);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int interests = addAllImages.get(position);

                if(holder.interestTitles.getCurrentTextColor() == Color.parseColor("#646969")) {

                    holder.interestTitles.setTextColor(Color.parseColor("#0091f0"));
                    holder.interestsImages.setImageResource(ServicesActivity.selectedIcons[position]);
                    ServicesActivity.interestsList.add(interests);
                }
                else {
                    holder.interestTitles.setTextColor(Color.parseColor("#646969"));
                    holder.interestsImages.setImageResource(ServicesActivity.interestIcons[position]);
                 /*   if(ServicesActivity.interestsList.get(position) == position) {

                        ServicesActivity.interestsList.remove(position);

                    }*/
                }


            }
        });

        return convertView;
    }

    public class Holder {
        ImageView interestsImages;
        TextView interestTitles;

    }
}
