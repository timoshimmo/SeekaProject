package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 5/22/2016.
 */
public class SettingsPopupAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private String[] settingsList;

    public SettingsPopupAdapter(Context ctx, String[] st) {

        this.context = ctx;
        this.settingsList = st;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return settingsList.length;
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
            convertView = inflater.inflate(R.layout.settings_popup, parent, false);
        }

        holder.settingsTitles = (TextView) convertView.findViewById(R.id.txtSettingsTitle);
        holder.settingsTitles.setText(settingsList[position]);

        return convertView;
    }

    public class Holder {
        TextView settingsTitles;

    }
}
