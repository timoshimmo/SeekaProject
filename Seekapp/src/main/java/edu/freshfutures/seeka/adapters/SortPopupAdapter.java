package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 4/16/2016.
 */
public class SortPopupAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    public ArrayList<String> sortTitle = new ArrayList<String>();
    public int[] sortImage;

    public SortPopupAdapter(Context ctx, ArrayList<String> it) {

        this.context = ctx;
        this.sortTitle = it;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return this.sortTitle.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.sort_popup_list_structure, parent, false);
        }

        holder.sortTitles = (TextView) convertView.findViewById(R.id.txtInterestTitle);
        holder.sortImages = (ImageButton) convertView.findViewById(R.id.imgUpButton);

        holder.sortTitles.setText(sortTitle.get(position));
        holder.sortTitles.setTag(position);
        holder.sortImages.setTag(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.sortTitles.getCurrentTextColor() == Color.parseColor("#BEC8C8")) {

                    holder.sortTitles.setTextColor(Color.parseColor("#7F8EC7"));
                    holder.sortImages.setVisibility(View.VISIBLE);

                } else {
                    holder.sortTitles.setTextColor(Color.parseColor("#BEC8C8"));
                    holder.sortImages.setVisibility(View.INVISIBLE);
                }

            }
        });

        holder.sortImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tags = (int) holder.sortImages.getTag();
                int tagsUp = tags - 1;

                if(tags > 0) {

                    String currentValue = sortTitle.get(tags);
                    String previous_value = sortTitle.get(tagsUp);
                    sortTitle.set(tagsUp, currentValue);
                    sortTitle.set(tags, previous_value);

                    notifyDataSetChanged();
                }

            }
        });

        return convertView;
    }

    public class Holder {
        ImageButton sortImages;
        TextView sortTitles;
    }
}
