package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.WelcomeActivity;

/**
 * Created by tokmang on 6/13/2016.
 */
public class FilterCourseAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    private ArrayList<String> courseName = new ArrayList<>();
    private ArrayList<String> courseNameList = new ArrayList<>();

    private JSONArray jsonArrayCourses;

    public FilterCourseAdapter(Context ctx, ArrayList<String> cn) {
        this.context = ctx;
        this.courseName = cn;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return this.courseName.size();
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
            convertView = inflater.inflate(R.layout.filter_course_list_structure, parent, false);
        }

        holder.coursesRow = (CheckBox) convertView.findViewById(R.id.ckFilterCourse);

        String courseTitle = courseName.get(position);

        holder.coursesRow.setText(courseTitle);

        Typeface secface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        holder.coursesRow.setTypeface(secface);

        holder.coursesRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String courseRowText = holder.coursesRow.getText().toString();
                courseNameList.add(courseRowText);

            /*    if(holder.addButton. == position) {

                    if(Build.VERSION.SDK_INT < 21) {
                        holder.addButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.filter_delete_button));
                    }

                    else {
                        holder.addButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.filter_delete_button, null));
                    }



                }

                else {
                    if(Build.VERSION.SDK_INT < 21) {
                        holder.addButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.filter_add_button));
                    }

                    else {
                        holder.addButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.filter_add_button, null));
                    }

                    holder.addButton.setTag(-1);
                } */
            }
        });

        return convertView;
    }

    public void setAllCourses() {

        jsonArrayCourses = new JSONArray(courseNameList);

        SharedPreferences settings = context.getSharedPreferences("PREFCOURSESFILTER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("filterCourses", jsonArrayCourses.toString());
        editor.apply();

    }

    public class ViewHolder {
        CheckBox coursesRow;
    }
}
