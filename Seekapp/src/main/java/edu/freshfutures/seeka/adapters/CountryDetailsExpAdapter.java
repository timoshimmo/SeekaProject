package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 5/20/2016.
 */
public class CountryDetailsExpAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private int[] groupIcons;
    private String[] groupTitle;
    private String[] childText;

    TextView titles;
    String listTitle;

    ImageView listIcon;

    public CountryDetailsExpAdapter(Context context, String[] gt,
                                    String[] cht, int[] gpIcons) {
        this.context = context;
        this.groupTitle = gt;
        this.childText = cht;
        this.groupIcons = gpIcons;
    }

    @Override
    public int getGroupCount() {
        return this.groupTitle.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.groupTitle[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childText[childPosition];
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

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.country_about_details_list_structure, null);
        }

        titles = (TextView) convertView
                .findViewById(R.id.txtAboutCountryOptions);
        listIcon = (ImageView) convertView.findViewById(R.id.imgCtryOptionIcon);

        titles.setText(listTitle);
        listIcon.setImageResource(groupIcons[groupPosition]);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View childView, ViewGroup parent) {

        //final String expandedListText = (String) getChild(groupPosition, childPosition);

        if (childView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            childView = layoutInflater.inflate(R.layout.country_info_child_structure, null);
        }

        final TextView ListChildTextView = (TextView) childView
                .findViewById(R.id.txtCountryInfoDetails);

        ListChildTextView.setText(this.childText[childPosition]);

        return childView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
