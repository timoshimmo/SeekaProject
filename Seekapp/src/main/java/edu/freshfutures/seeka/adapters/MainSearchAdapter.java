package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import edu.freshfutures.seeka.Models.SearchTypeModel;
import edu.freshfutures.seeka.R;


/**
 * Created by tokmang on 6/15/2016.
 */
public class MainSearchAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    private ArrayList<String> lvlName = new ArrayList<>();
    private ArrayList<String> lvlCode = new ArrayList<>();

    private SearchTypeModel model;
    public ArrayList<SearchTypeModel> arrModel;

    public MainSearchAdapter(Context ctx, ArrayList<String> ln, ArrayList<String> lc) {
        this.context = ctx;
        this.lvlName = ln;
        this.lvlCode = lc;
        arrModel = new ArrayList<>();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return this.lvlName.size();
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

        holder.levels = (TextView) convertView.findViewById(R.id.txtCourseName);

        holder.levels.setText(lvlName.get(position));
        holder.levels.setTag(position);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model = new SearchTypeModel();

                String levelCode = lvlCode.get(position);
                String levelString = holder.levels.getText().toString();


                if(holder.levels.getCurrentTextColor() == Color.parseColor("#C3C8C8")) {
                    holder.levels.setTextColor(Color.parseColor("#00aff0"));

                    model.setSearchLevel(levelCode);
                    model.setSearchLevelFull(levelString);

                    arrModel.add(model);

                }

                else {

                    holder.levels.setTextColor(Color.parseColor("#C3C8C8"));

                    model.setSearchLevel(levelCode);
                    arrModel.remove(model);
                }

                /*Intent intent = new Intent(context, HomeActivity.class);
                intent.putExtra("SEARCH_RESULT", "searched");
                context.startActivity(intent);  */
            }
        });



        return convertView;
    }

    public class ViewHolder {
        TextView levels;

    }
}
