package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import edu.freshfutures.seeka.FragmentProfileEdu;
import edu.freshfutures.seeka.FragmentProfileInfo;
import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 7/6/2016.
 */
public class NationalityAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    public ArrayList<HashMap<String, ArrayList<String>>> ctryNames;
    int selectedIndex = -1;
    String selectedItemCode;

    public NationalityAdapter(Context ctx, ArrayList<HashMap<String, ArrayList<String>>> ctry) {
        this.context = ctx;
        this.ctryNames = ctry;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return ctryNames.size();
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

        final ViewHolder holder = new ViewHolder();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.settings_nationality_popup_stucture, parent, false);
        }

        holder.rdCountry = (RadioButton) convertView.findViewById(R.id.radioCountry);
        holder.rdCountry.setText(ctryNames.get(position).get(FragmentProfileEdu.CTRY_NAME).get(position));

        if(selectedIndex == position)
        {
            holder.rdCountry.setChecked(true);

        }
        else
        {
            holder.rdCountry.setChecked(false);
        }

        return convertView;
    }

    public class ViewHolder {
        RadioButton rdCountry;
    }

    public void setSelectedIndex(int index)
    {
        selectedIndex = index;
    }

}
