package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 8/18/2016.
 */
public class HobbySelectAdapter  extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    public ArrayList<String> courseName = new ArrayList<>();

    JSONArray objArray;

    public HobbySelectAdapter(Context ctx, ArrayList<String> cn) {
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
        objArray = new JSONArray();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.hobby_list_structure, parent, false);
        }

        holder.courses = (TextView) convertView.findViewById(R.id.txtCourseName);
        holder.addButton = (ImageButton) convertView.findViewById(R.id.btnAddRemoveCourse);

        holder.courses.setText(courseName.get(position));
        holder.courses.setTag(position);
        holder.addButton.setTag("unselected");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.addButton.getTag() == "unselected") {

                    if(Build.VERSION.SDK_INT < 21) {
                        holder.addButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.filter_delete_button));
                    }

                    else {
                        holder.addButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.filter_delete_button, null));
                    }

                    holder.addButton.setTag("selected");
                    try {
                        objArray.put(position, courseName.get(position));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                else {
                    if(Build.VERSION.SDK_INT < 21) {
                        holder.addButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.filter_add_button));
                    }

                    else {
                        holder.addButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.filter_add_button, null));
                    }

                    holder.addButton.setTag("unselected");

                    if(objArray.length() > 0) {
                        if(Build.VERSION.SDK_INT < 19) {
                            RemoveJSONArray(objArray, position);
                        }
                        else {
                            objArray.remove(position);
                        }

                    }

                }
            }
        });

        return convertView;

    }

    public class ViewHolder {
        ImageButton addButton;
        TextView courses;
    }

    public static JSONArray RemoveJSONArray( JSONArray jarray,int pos) {

        JSONArray Njarray=new JSONArray();
        try{
            for(int i=0;i<jarray.length();i++){
                if(i!=pos)
                    Njarray.put(jarray.get(i));
            }
        }catch (Exception e){e.printStackTrace();}
        return Njarray;
    }

    public JSONArray setJsonData() {

        return objArray;
    }
}
