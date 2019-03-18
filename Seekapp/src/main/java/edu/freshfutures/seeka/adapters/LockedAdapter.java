package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.freshfutures.seeka.HomeActivity;
import edu.freshfutures.seeka.Models.LockedModel;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.RequirementsDialogFragment;
import edu.freshfutures.seeka.UnlockCourseDialogFragment;

/**
 * Created by tokmang on 7/26/2016.
 */
public class LockedAdapter extends RecyclerView.Adapter<LockedAdapter.ViewHolder> {

    private ArrayList<LockedModel> mLockedCourses;

    // Store the context for easy access
    private Context mContext;

    public LockedAdapter(Context ctx, ArrayList<LockedModel> lm) {

        this.mLockedCourses = lm;
        this.mContext = ctx;

    }

    @Override
    public LockedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.search_result_list_structure, parent, false);

        ViewHolder viewholder = new ViewHolder(v);
        return viewholder;

    }

    @Override
    public void onBindViewHolder(final LockedAdapter.ViewHolder holder, int position) {

        final LockedModel models = mLockedCourses.get(position);

        holder.institution_location.setText(models.getInstitution());
        holder.institution_ranking.setText(models.getRankValue());
       // holder.match_value.setText(models.getMatchValue());
        holder.recognition_value.setText(models.getRecgnitionValue());
        holder.cost_saving_value.setText(models.getCostSavingValue());
        holder.cost_saving_currency.setText(models.getCostSavingCurrency());
        holder.recognitionDesc.setText(models.getRecognitionType());
        holder.costRange.setText(models.getCostRange());
        holder.courseDuration.setText(models.getDurationTime() + " " + models.getDurationType());
        holder.courseName.setText(models.getCourseTitle());
        holder.btnUnlock.setTag(models.getCourseID());
        holder.currencyCostRange.setText(models.getCostSavingCurrency());

      /*  if(models.getSponsOption() == 1) {
            holder.sponsBody.setVisibility(View.VISIBLE);
            holder.sponsName.setText(models.getSponsName());
        } */

        //Glide.with(mContext).load(models.getCountryImage()).into(holder.imgCtry);

        holder.txtRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((HomeActivity)mContext).getSupportFragmentManager();
                RequirementsDialogFragment reqFragments = RequirementsDialogFragment.newInstance(models.getRemarks());

                FragmentTransaction transactions = fragmentManager.beginTransaction();

                reqFragments.show(transactions, "reqFragments");

            }
        });

        holder.btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((HomeActivity)mContext).getSupportFragmentManager();
                UnlockCourseDialogFragment unlockFragments = UnlockCourseDialogFragment.newInstance((int)holder.btnUnlock.getTag());

                FragmentTransaction transactions = fragmentManager.beginTransaction();

                unlockFragments.show(transactions, "unlockCoursesFragments");
            }
        });

    }

    @Override
    public int getItemCount() {
        return mLockedCourses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView institution_location;
        TextView institution_ranking;
        TextView match_value;
        TextView recognition_value;
        TextView cost_saving_value;
        TextView cost_saving_currency;
        TextView txtRef;
        Button btnUnlock;
        ImageView imgCtry;
        TextView recognitionDesc;
        TextView costRange;
        TextView courseDuration;
        TextView currencyCostRange;
        TextView courseType;
        TextView courseName;
        TextView sponsName;
        LinearLayout sponsBody;

        public ViewHolder(View itemView) {
            super(itemView);

            institution_location = (TextView)itemView.findViewById(R.id.txtInstitutionTitle);
            institution_ranking = (TextView)itemView.findViewById(R.id.txtInstitutionRankingNumber);
            match_value = (TextView)itemView.findViewById(R.id.txtMatchingFigure);
            recognition_value = (TextView)itemView.findViewById(R.id.txtRecognitionFigure);
            cost_saving_value = (TextView)itemView.findViewById(R.id.txtCostSavingFigure);
            cost_saving_currency = (TextView)itemView.findViewById(R.id.txtCostSavingCurrency);
            txtRef = (TextView) itemView.findViewById(R.id.txtSearchResultReq);
            btnUnlock = (Button) itemView.findViewById(R.id.btnUnlockcourse);
            imgCtry = (ImageView) itemView.findViewById(R.id.imgLockedCountries);
            recognitionDesc = (TextView) itemView.findViewById(R.id.txtRecognitionDescription);
            costRange = (TextView) itemView.findViewById(R.id.txtUnlockCourseCostPerYearValue);
            currencyCostRange = (TextView) itemView.findViewById(R.id.costCurrencyPerYear);
            courseDuration = (TextView) itemView.findViewById(R.id.txtUnlockCourseDuration);
            courseType = (TextView) itemView.findViewById(R.id.txtUnlockCourseTitleString);
            courseName = (TextView) itemView.findViewById(R.id.txtCourseName);
            sponsName = (TextView) itemView.findViewById(R.id.txtSponsorName);
            sponsBody = (LinearLayout) itemView.findViewById(R.id.sponsLayout);

        }
    }
}
