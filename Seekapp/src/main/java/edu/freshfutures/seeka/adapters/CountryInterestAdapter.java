package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.freshfutures.seeka.Models.CountryInterestModel;
import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 8/16/2016.
 */
public class CountryInterestAdapter extends RecyclerView.Adapter<CountryInterestAdapter.ViewHolder> {
    private ArrayList<CountryInterestModel> mCtryInterest;
    private Context mContext;

    private CountryInterestModel model;

    public CountryInterestAdapter(Context ctx, ArrayList<CountryInterestModel> cl) {
        this.mCtryInterest = cl;
        this.mContext = ctx;
    }

    @Override
    public CountryInterestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.profile_data_structure, parent, false);

        ViewHolder viewholder = new ViewHolder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(CountryInterestAdapter.ViewHolder holder, int position) {

        model = mCtryInterest.get(position);
        holder.profileData.setText(model.getCtryInterest());
    }

    @Override
    public int getItemCount() {
        return mCtryInterest.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView profileData;

        public ViewHolder(View itemView) {
            super(itemView);

            profileData = (TextView) itemView.findViewById(R.id.txtProfileData);

        }
    }

}
