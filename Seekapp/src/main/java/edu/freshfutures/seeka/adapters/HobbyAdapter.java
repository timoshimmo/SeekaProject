package edu.freshfutures.seeka.adapters;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import edu.freshfutures.seeka.Models.ProfileInterestModel;
import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 8/16/2016.
 */

public class HobbyAdapter extends RecyclerView.Adapter<HobbyAdapter.ViewHolder> {

    private ArrayList<ProfileInterestModel> mHobbyInterest;
    private ArrayList<String> mHobbyList;
    private Context mContext;

    private ProfileInterestModel model;


    public HobbyAdapter(Context ctx, ArrayList<ProfileInterestModel> hl) {
        this.mHobbyInterest = hl;
        this.mContext = ctx;
    }



    @Override
    public HobbyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.profile_data_structure, parent, false);

        ViewHolder viewholder = new ViewHolder(v);
        return viewholder;

    }

    @Override
    public void onBindViewHolder(HobbyAdapter.ViewHolder holder, int position) {

        model = mHobbyInterest.get(position);

        String hobbyValue = model.getHobbyInterest();
        holder.profileData.setText(hobbyValue);


    }

    @Override
    public int getItemCount() {
        return mHobbyInterest.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView profileData;

        public ViewHolder(View itemView) {
            super(itemView);

            profileData = (TextView) itemView.findViewById(R.id.txtProfileData);

        }
    }
}
