package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.freshfutures.seeka.R;

/**
 * Created by freshfuturesmy on 26/10/16.
 */

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.ViewHolder> {


    private ArrayList<Integer> mFacultyIcons;
    private Context mContext;

    public FacultyAdapter(Context ctx, ArrayList<Integer> fi) {

        this.mFacultyIcons = fi;
        this.mContext = ctx;

    }



    @Override
    public FacultyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.faculty_desgin_structure, parent, false);

        FacultyAdapter.ViewHolder viewholder = new FacultyAdapter.ViewHolder(v);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(FacultyAdapter.ViewHolder holder, int position) {

        holder.imgFaculty.setImageResource(mFacultyIcons.get(position));

    }

    @Override
    public int getItemCount() {
        return this.mFacultyIcons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFaculty;

        public ViewHolder(View itemView) {
            super(itemView);

            imgFaculty = (ImageView) itemView.findViewById(R.id.imgFaculty);
        }
    }
}
