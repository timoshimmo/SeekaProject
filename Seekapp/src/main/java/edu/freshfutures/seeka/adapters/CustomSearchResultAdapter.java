/*package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeCallbacks;

import java.util.List;

import edu.freshfutures.seeka.CustomWidgets.CustomButtonTextRegular;
import edu.freshfutures.seeka.CustomWidgets.CustomTextViewLight;
import edu.freshfutures.seeka.CustomWidgets.CustomTextViewMedium;
import edu.freshfutures.seeka.CustomWidgets.CustomTextViewRegular;
import edu.freshfutures.seeka.FragmentUnlock;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.RequirementsDialogFragment;
import edu.freshfutures.seeka.UnlockCourseDialogFragment;


public class CustomSearchResultAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    AppCompatActivity context;
    String[] inLocation;
    String[] inRanking;
   // String[] courseName;
  //  String[] courseDuration;
   // String[] courseFigure;
    String[] mValue;
    String[] regValue;
    String[] empValue;
    Float[] rateValue;
    String[] types;

    public CustomSearchResultAdapter() {
    }

    public CustomSearchResultAdapter(AppCompatActivity ctx, String[] il, String[] ir,  String[] mv,
                                     String[] regv, String[] empv, String[] tp) {

        this.context = ctx;
        this.inLocation = il;
        this.inRanking = ir;
     //   this.courseName = cn;
    //    this.courseDuration = cd;
        this.mValue = mv;
        this.regValue = regv;
        this.empValue = empv;
        this.types = tp;

        inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public int getItemViewType(int position) {
        return Integer.parseInt(types[position]);
    }

    @Override
    public int getCount() {
        return this.inLocation.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomSearchResultAdapter.Holder holder;

        int type = getItemViewType(position);

            holder = new CustomSearchResultAdapter.Holder();

            if(type == 0) {

                if(convertView == null) {

                convertView = inflater.inflate(R.layout.search_result_list_structure, null);
                holder.institution_location = (CustomTextViewLight)convertView.findViewById(R.id.txtInstitutionTitle);
                holder.institution_ranking = (CustomTextViewMedium)convertView.findViewById(R.id.txtInstitutionRankingNumber);
                //  holder.course_name = (CustomTextViewLight)convertView.findViewById(R.id.txtCouseDescription);
                //   holder.course_duration = (CustomTextViewLight)convertView.findViewById(R.id.txtCourseDuration);
                //   holder.course_figure = (CustomTextViewLight)convertView.findViewById(R.id.txtCourseDetailsFigure);
                holder.match_value = (CustomTextViewRegular)convertView.findViewById(R.id.txtMatchingFigure);
                holder.recognition_value = (CustomTextViewRegular)convertView.findViewById(R.id.txtRecognitionFigure);
                holder.employability_value = (CustomTextViewRegular)convertView.findViewById(R.id.txtEmployabilityFigure);
                //   holder.rate_institution = (RatingBar)convertView.findViewById(R.id.ratingBar);
                holder.txtRef = (CustomTextViewLight) convertView.findViewById(R.id.txtSearchResultReq);
                holder.btnUnlock = (CustomButtonTextRegular) convertView.findViewById(R.id.btnUnlockcourse);

                convertView.setTag(holder);
            }
                else {

                    holder = (Holder)convertView.getTag();

                }

                holder.institution_location.setText(this.inLocation[position]);
                holder.institution_ranking.setText(this.inRanking[position]);
                //  holder.course_name.setText(this.courseName[position]);
                // holder.course_duration.setText(this.courseDuration[position]);
                //  holder.course_figure.setText(this.courseFigure[position]);
                holder.match_value.setText(this.mValue[position]);
                holder.recognition_value.setText(this.regValue[position]);
                holder.employability_value.setText(this.empValue[position]);
                //  holder.rate_institution.setRating(this.rateValue[position]);

                holder.txtRef.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentManager fragmentManager = context.getSupportFragmentManager();
                        RequirementsDialogFragment reqFragments = new RequirementsDialogFragment();

                        FragmentTransaction transactions = fragmentManager.beginTransaction();

                        reqFragments.show(transactions, "reqFragments");

                    }
                });

                holder.btnUnlock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentManager fragmentManager = context.getSupportFragmentManager();
                        UnlockCourseDialogFragment unlockFragments = new UnlockCourseDialogFragment();

                        FragmentTransaction transactions = fragmentManager.beginTransaction();

                        unlockFragments.show(transactions, "unlockCoursesFragments");
                    }
                });
        }

        else {

                if(convertView == null) {

                    convertView = inflater.inflate(R.layout.locked_ad, null);

                    final View finalConvertView = convertView;
                    final Holder finalHolder = holder;

                    String appKey = "7cb45f61d30be13ce08d2e9b0cc5e4c8047ab035212fcb71";

                    Appodeal.setTesting(true);
                    Appodeal.setAutoCacheNativeIcons(true);
                    Appodeal.setAutoCacheNativeImages(false);
                    Appodeal.initialize(context, appKey, Appodeal.NATIVE);


                    Appodeal.setNativeCallbacks(new NativeCallbacks() {
                        @Override
                        public void onNativeLoaded(List<NativeAd> list) {


                            Log.d("Appodeal", "onNativeLoaded");
                            Toast.makeText(context, "onNativeLoaded", Toast.LENGTH_SHORT).show();

                            LinearLayout nativeView = (LinearLayout) finalConvertView.findViewById(R.id.search_result_curved_body);
                            NativeAd nativeAd = list.get(0);

                            finalHolder.adTitle = (TextView) nativeView.findViewById(R.id.txtTitle);
                            finalHolder.adDesc = (TextView) nativeView.findViewById(R.id.txtDesc);
                            finalHolder.sponText = (TextView) nativeView.findViewById(R.id.txtSpons);

                            finalHolder.callToAction = (Button) nativeView.findViewById(R.id.btnCallToAction);
                            finalHolder.adImage = (ImageView) nativeView.findViewById(R.id.imgAd);

                            finalHolder.adTitle.setText(nativeAd.getTitle());
                            finalHolder.adDesc.setText(nativeAd.getDescription());
                            finalHolder.adImage.setImageBitmap(nativeAd.getImage());
                            finalHolder.callToAction.setText(nativeAd.getCallToAction());

                            View providerView = nativeAd.getProviderView(context);
                            if (providerView != null) {
                                FrameLayout providerViewContainer = (FrameLayout) nativeView.findViewById(R.id.adBody);
                                providerViewContainer.addView(providerView);
                            }

                            nativeAd.registerViewForInteraction(nativeView);
                            nativeView.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onNativeFailedToLoad() {
                            Toast.makeText(context, "onNativeFailedToLoad", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNativeShown(NativeAd nativeAd) {

                            Toast.makeText(context, "onNativeShown", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNativeClicked(NativeAd nativeAd) {

                            Toast.makeText(context, "onNativeClicked", Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }


        return convertView;
    }

    public class Holder {
        CustomTextViewLight institution_location;
        CustomTextViewMedium institution_ranking;
        CustomTextViewRegular match_value;
        CustomTextViewRegular recognition_value;
        CustomTextViewRegular employability_value;
        CustomTextViewLight txtRef;
        Button btnUnlock;
        TextView sponText;
        TextView adTitle;
        TextView adDesc;
        ImageView adImage;
        Button callToAction;

        public Holder() {
        }
    }
}

*/