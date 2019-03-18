package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.freshfutures.seeka.HomeActivity;
import edu.freshfutures.seeka.Models.SearchTypeModel;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.SearchByCountryActivity;

/**
 * Created by tokmang on 6/15/2016.
 */
public class SearchCoursesAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    public ArrayList<String> countryName = new ArrayList<>();
    public ArrayList<String> countryCode = new ArrayList<>();

    public String arrModel;
    public String ctryStringModel;

    public SearchCoursesAdapter(Context ctx, ArrayList<String> cn, ArrayList<String> cc) {
        this.context = ctx;
        this.countryName = cn;
        this.countryCode = cc;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return this.countryName.size();
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

        final ViewHolder holder = new ViewHolder();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.search_courses_list_structure_adpt, parent, false);
        }

        holder.countries = (TextView) convertView.findViewById(R.id.txtCourseName);

        holder.countries.setText(countryName.get(position));
        holder.countries.setTag(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.countries.getCurrentTextColor() == Color.parseColor("#C3C8C8")) {

                    holder.countries.setTextColor(Color.parseColor("#00aff0"));

                    arrModel = countryCode.get(position);
                    ctryStringModel = holder.countries.getText().toString();
                }

                else {

                    holder.countries.setTextColor(Color.parseColor("#C3C8C8"));

                }


            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView countries;
    }
}
