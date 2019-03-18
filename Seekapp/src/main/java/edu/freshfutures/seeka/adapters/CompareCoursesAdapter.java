package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.freshfutures.seeka.CompareCoursesActivity;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.Unidata;

/**
 * Created by tokmang on 5/19/2016.
 */
public class CompareCoursesAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context context;
    public ArrayList<Unidata> uinCoursesData;


    public CompareCoursesAdapter(Context ctx, ArrayList<Unidata> ucd) {

        this.context = ctx;
        this.uinCoursesData = ucd;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return uinCoursesData.size();
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
            convertView = inflater.inflate(R.layout.compare_courses_layout, parent, false);
        }

        holder.UniName = (TextView) convertView.findViewById(R.id.txtUniName);
        holder.uniLogo = (ImageView) convertView.findViewById(R.id.imgUniLogo);
        holder.removeCourse = (ImageButton) convertView.findViewById(R.id.btnRemoveCourse);
        holder.uniCourses = (TextView) convertView.findViewById(R.id.txtCourse);

        holder.UniName.setText(uinCoursesData.get(position).getUniName());
        holder.uniLogo.setImageResource(uinCoursesData.get(position).getUniLogo());
        holder.uniCourses.setText(uinCoursesData.get(position).getCourseName());
        holder.removeCourse.setTag(position);

        holder.removeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  int index = (int)v.getTag();
                uinCoursesData.remove(position);
               // ((CompareCoursesActivity) context).removeItem(index);
                CompareCoursesAdapter.this.notifyDataSetChanged();
            }
        });

        return convertView;

    }

/*    public void remove(int pos){
        konulist.remove(konulist.get(position));
        kimdenlist.remove(kimdenlist.get(position));
    }*/

    public class Holder {
        TextView UniName;
        ImageView uniLogo;
        ImageButton removeCourse;
        TextView uniCourses;
    }
}
