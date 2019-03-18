package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 7/12/2016.
 */
public class InterestExpListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListChild;

    TextView listTitles;
    String listTitle;

    JSONArray objArray;

    public InterestExpListAdapter(Context context, List<String> expandableListTitle,
                                 HashMap<String, List<String>> expandableListChild) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListChild = expandableListChild;

    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableListChild.get(this.expandableListTitle.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListChild.get(this.expandableListTitle.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        listTitle = (String) getGroup(groupPosition);
        int childNo = getChildrenCount(groupPosition);
        String citiesNo = String.valueOf(childNo);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.interest_explist_head_structure, null);
        }
        listTitles = (TextView) convertView
                .findViewById(R.id.txtInterestGroup);

        ImageView indicator = (ImageView) convertView.findViewById(R.id.imgIndicator);

        listTitles.setText(listTitle);

        if(isExpanded) {
            indicator.setImageResource(R.mipmap.filter_up_arrow);
            listTitles.setTextColor(Color.parseColor("#0091f0"));

        }
        else {
            indicator.setImageResource(R.mipmap.filter_down_arrow);
            listTitles.setTextColor(Color.parseColor("#646969"));
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View childView, ViewGroup parent) {

        final String expandedListText = (String) getChild(groupPosition, childPosition);
        final String headerTitle = (String) getGroup(groupPosition);

        objArray = new JSONArray();

        if (childView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            childView = layoutInflater.inflate(R.layout.interest_exp_list_child_structure, null);
        }
        final TextView ListChildTextView = (TextView) childView
                .findViewById(R.id.txtInterestChild);

        final CheckBox country_checkbox = (CheckBox) childView.findViewById(R.id.checkInterestChild);
        ListChildTextView.setText(expandedListText);

        country_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    try {
                        objArray.put(childPosition, expandedListText);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (objArray.length() > 0) {
                        if (Build.VERSION.SDK_INT < 19) {
                            RemoveJSONArray(objArray, childPosition);
                        } else {
                            objArray.remove(childPosition);
                        }

                    }
                }
            }
        });

        return childView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
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

    public JSONArray setJsonCountryData() {
        return objArray;
    }

}
