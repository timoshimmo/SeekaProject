package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 4/4/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    TextView listTitles;
    String listTitle;

    public ExpandableListAdapter(Context context, List<String> expandableListTitle,
                                 HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;

    }


    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
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
        String subtitle = citiesNo + " cities available with 19,000 Courses";

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.explist_country_head_structure, null);
        }
        listTitles = (TextView) convertView
                .findViewById(R.id.txtCountryGroup);
        TextView listSubTitleTextView = (TextView) convertView
                .findViewById(R.id.txtCountrySubtitle);
        ImageView indicator = (ImageView) convertView.findViewById(R.id.imgIndicator);

        listTitles.setText(listTitle);
        listSubTitleTextView.setText(subtitle);

        if(isExpanded) {
            indicator.setImageResource(R.mipmap.filter_up_arrow);

        }
        else {
            indicator.setImageResource(R.mipmap.filter_down_arrow);

        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View childView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition, childPosition);
        final String headerTitle = (String) getGroup(groupPosition);
        if (childView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            childView = layoutInflater.inflate(R.layout.country_child_strucure, null);
        }
        final TextView ListChildTextView = (TextView) childView
                .findViewById(R.id.txtCityChild);

        final CheckBox country_checkbox = (CheckBox) childView.findViewById(R.id.checkCities);
        ListChildTextView.setText(expandedListText);

        country_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    //selected = true;
                    //String group_string = (String)
                    Toast.makeText(context, "Header Title: " + headerTitle + " "
                            + "Expanded List Text: " + expandedListText + " Country :" + listTitle, Toast.LENGTH_LONG).show();

                }
            }
        });

        return childView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
