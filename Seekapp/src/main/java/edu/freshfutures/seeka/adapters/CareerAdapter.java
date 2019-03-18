package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.freshfutures.seeka.Models.CareerModel;
import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 8/18/2016.
 */
public class CareerAdapter extends RecyclerView.Adapter<CareerAdapter.ViewHolder> {

    private ArrayList<CareerModel> mCrerInterest;
    private Context mContext;

    private CareerModel model;

    public CareerAdapter(Context ctx, ArrayList<CareerModel> cl) {
        this.mCrerInterest = cl;
        this.mContext = ctx;
    }

    @Override
    public CareerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.profile_data_structure, parent, false);

        ViewHolder viewholder = new ViewHolder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(CareerAdapter.ViewHolder holder, int position) {

        model = mCrerInterest.get(position);
        holder.profileData.setText(model.getCareerInterest());
    }

    @Override
    public int getItemCount() {
        return mCrerInterest.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView profileData;

        public ViewHolder(View itemView) {
            super(itemView);

            profileData = (TextView) itemView.findViewById(R.id.txtProfileData);

        }
    }
}
