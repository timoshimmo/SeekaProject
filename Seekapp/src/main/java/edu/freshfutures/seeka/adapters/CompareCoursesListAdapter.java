package edu.freshfutures.seeka.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.freshfutures.seeka.CompareCoursesActivity;
import edu.freshfutures.seeka.CompareCoursesListActivity;
import edu.freshfutures.seeka.ProfileActivity;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.Unidata;

/**
 * Created by tokmang on 7/1/2016.
 */
public class CompareCoursesListAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
  //  ArrayList<HashMap<String, ArrayList<String>>> coursesList;
    String[] uniName;
    String[] courseName;
    int[] uniLogo;

    String uni_Name;
    String course_Name;
    int uni_Logo;

    Context ctx;

    int RESULT_CODE = 100;

    public static ArrayList<Unidata> newData;

    public CompareCoursesListAdapter(Context context, String[] un, String[] cn, int[] ul) {
        this.ctx = context;
        this.uniName = un;
        this.courseName = cn;
        this.uniLogo = ul;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return uniName.length;
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
            convertView = inflater.inflate(R.layout.compare_courses_course_list_structure, parent, false);
        }

        holder.university = (TextView) convertView.findViewById(R.id.txtUniversityName);
        holder.courses = (TextView) convertView.findViewById(R.id.txtCourseName);
        holder.imgLogo = (ImageView) convertView.findViewById(R.id.imageUniLogo);

        holder.university.setText(uniName[position]);
        holder.courses.setText(courseName[position]);
        holder.imgLogo.setImageResource(uniLogo[position]);


        return convertView;

    }

    public void getNewCourse(int index) {

        uni_Name = uniName[index];
        course_Name = courseName[index];
        uni_Logo = uniLogo[index];

      //  setUniversityName(uni_Name);
      //  udt.setCourseName(course_Name);
      //  udt.setUniversityLogo(uni_Logo);

     //   newData.add(udt);

        Intent intent = new Intent(ctx, CompareCoursesActivity.class);
        intent.putExtra("UNINAME", uni_Name);
        intent.putExtra("UNICOURSE", course_Name);
        intent.putExtra("UNILOGO", uni_Logo);
        ((CompareCoursesListActivity)ctx).setResult(RESULT_CODE, intent);
        ((CompareCoursesListActivity)ctx).finish();
    }

    public class Holder {
        TextView university;
        TextView courses;
        ImageView imgLogo;
    }

}
