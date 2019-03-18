package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.freshfutures.seeka.CourseInfoActivity;
import edu.freshfutures.seeka.FragmentDetailedGradeRequirements;
import edu.freshfutures.seeka.FragmentExemptions;
import edu.freshfutures.seeka.FragmentVisa;
import edu.freshfutures.seeka.JobDetailsFragment;
import edu.freshfutures.seeka.Models.CourseInfoDataModel;
import edu.freshfutures.seeka.Models.CourseInfoModel;
import edu.freshfutures.seeka.OffersActivity;
import edu.freshfutures.seeka.ProfileActivity;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.ServicesActivity;
import edu.freshfutures.seeka.interfaces.ToolBarBack;
import edu.freshfutures.seeka.util.ListDividerItemDecoration;

/**
 * Created by tokmang on 8/6/2016.
 */
public class CourseInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CourseInfoModel> mCourses;
    private Context mContext;

    public static final int COURSE_INFO_TYPE = 0;
    public static final int TWINNING_TYPE = 1;
    public static final int MINIMUM_REQ_TYPE = 2;
    public static final int TOTAL_AVAILABLE_TYPE = 3;
    public static final int MATCH_TYPE = 4;
    public static final int TOUR_TYPE = 5;
    public static final int SCHOLARSHIP_TYPE = 6;
    public static final int UNI_INFO_TYPE = 7;
    public static final int SERVICES_TYPE = 8;
    public static final int VISA_TYPE = 9;
    public static final int MEDIA_TYPE = 10;
    public static final int APPLY_TYPE = 11;

    private static RecyclerView mYoutubeView;
    private ArrayList<CourseInfoDataModel> arrDataModel;


    public CourseInfoAdapter(Context ctx, ArrayList<CourseInfoModel> sm, ArrayList<CourseInfoDataModel> courseData) {
        this.mCourses = sm;
        this.mContext = ctx;
        this.arrDataModel = courseData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        Context context = parent.getContext();

        switch(viewType) {

            case COURSE_INFO_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.course_info_content_layout, parent, false);
                return new CourseInfoViewHolder(view);


            case TWINNING_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.twinning_program_view, parent, false);
                return new TwinningViewHolder(view);

            case MINIMUM_REQ_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.minimum_req_view, parent, false);
                return new MinReqViewHolder(view);

            case TOTAL_AVAILABLE_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.total_available_jobs, parent, false);
                return new TotalAvailableJobsViewHolder(view);

            case MATCH_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.matches_course_view, parent, false);
                return new MatchViewHolder(view);

            case TOUR_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.virtual_tour_view, parent, false);
                return new VirtualTourViewHolder(view);

            case SCHOLARSHIP_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.scholarship_availability_view, parent, false);
                return new ScholarshipViewHolder(view);

            case UNI_INFO_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.university_info_view, parent, false);
                return new UniInfoViewHolder(view);

            case SERVICES_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.services_view, parent, false);
                return new ServicesViewHolder(view);

            case VISA_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.visa_view, parent, false);
                return new VisaViewHolder(view);

            case MEDIA_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.media_view, parent, false);
                return new MediaViewHolder(view);

            case APPLY_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.apply_course_button_view, parent, false);
                return new ApplyViewHolder(view);

        }


        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        CourseInfoModel sModel = mCourses.get(position);
        final CourseInfoDataModel getModels = arrDataModel.get(0);

        if(sModel != null) {

            switch(sModel.getType()) {

                case COURSE_INFO_TYPE:

                    final String links = getModels.getUniCourseWebsite();

                    ((CourseInfoViewHolder) holder).txtFaculty.setText(getModels.getFaculty());
                    ((CourseInfoViewHolder) holder).txtCourseTitle.setText(getModels.getCourseTitle());
                    ((CourseInfoViewHolder) holder).txtCourseDuration.setText(getModels.getDurationType() + " " + getModels.getDurationTime());
                    ((CourseInfoViewHolder) holder).txtCourseFees.setText(getModels.getCostRange());
                    ((CourseInfoViewHolder) holder).txtCourseFeeCurrency.setText(getModels.getCurrency().trim() + "/Year");

                    ((CourseInfoViewHolder) holder).btnCourseInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = links;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            mContext.startActivity(i);
                        }
                    });

                    break;

                case MINIMUM_REQ_TYPE:

                    ((MinReqViewHolder) holder).aLvlGrades.setText(getModels.getaLvlSubj1() + ", " + getModels.getaLvlSubj2() +
                    ", " + getModels.getaLvlSubj3() + ", " + getModels.getaLvlSubj4() + ", " + getModels.getaLvlSubj5());


                    ((MinReqViewHolder) holder).ieltsOverall.setText(getModels.getIeltsOverall());
                    ((MinReqViewHolder) holder).ieltsReading.setText(getModels.getIeltsReading());
                    ((MinReqViewHolder) holder).ieltsListening.setText(getModels.getIeltsListening());
                    ((MinReqViewHolder) holder).ieltsWriting.setText(getModels.getIeltsWriting());
                    ((MinReqViewHolder) holder).ieltsSpeaking.setText(getModels.getIeltsSpeaking());

                    ((MinReqViewHolder) holder).toeflOverall.setText(getModels.getIeltsOverall());
                    ((MinReqViewHolder) holder).toeflReading.setText(getModels.getIeltsReading());
                    ((MinReqViewHolder) holder).toeflListening.setText(getModels.getIeltsListening());
                    ((MinReqViewHolder) holder).toeflWriting.setText(getModels.getIeltsWriting());
                    ((MinReqViewHolder) holder).toeflSpeaking.setText(getModels.getIeltsSpeaking());

                    ((MinReqViewHolder) holder).btnGradeReq.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String tag = "dialogsGradeReq";
                            FragmentManager fragmentManager = ((CourseInfoActivity)mContext).getSupportFragmentManager();
                            FragmentDetailedGradeRequirements gradeReq = FragmentDetailedGradeRequirements.newInstance(getModels.getRemarks());

                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                            transaction.add(android.R.id.content, gradeReq, tag)
                                    .addToBackStack(null).commit();


                        }
                    });

                    ((MinReqViewHolder) holder).btnExemptions.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String tag = "dialogsExemptions";
                            FragmentManager fragmentManager = ((CourseInfoActivity) mContext).getSupportFragmentManager();
                            FragmentExemptions fragExempt = new FragmentExemptions();

                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                            transaction.add(android.R.id.content, fragExempt, tag)
                                    .addToBackStack(null).commit();

                        }
                    });

                    break;

                case TOTAL_AVAILABLE_TYPE:

                    ((TotalAvailableJobsViewHolder) holder).totJobsDetails.setText(getModels.getCourseCity() + " - " + getModels.getJobsAvailable());
                    ((TotalAvailableJobsViewHolder) holder).btnJobDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(mContext, JobDetailsFragment.class);
                            mContext.startActivity(intent);

                        }
                    });

                    break;

                case MATCH_TYPE:
                    ((MatchViewHolder) holder).showInterests.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ProfileActivity.class);
                            intent.putExtra("view_pager_no", 2);
                            mContext.startActivity(intent);
                        }
                    });
                    break;

              case TOUR_TYPE:

                  final String tripAdvisorLink = getModels.getTripAdvisorLink();
                  ((VirtualTourViewHolder) holder).btnTripAdvisor.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          String url = tripAdvisorLink;
                          Intent i = new Intent(Intent.ACTION_VIEW);
                          i.setData(Uri.parse(url));
                          mContext.startActivity(i);
                      }
                  });


                  ((VirtualTourViewHolder) holder).btnGoogleEarth.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          Uri gmmIntentUri = Uri.parse("google.streetview:cbll=48.4321425,-123.4782358");
                          Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                          mapIntent.setPackage("com.google.android.apps.maps");
                          mContext.startActivity(mapIntent);
                      }
                  });

                  break;

                case SCHOLARSHIP_TYPE:

                    ((ScholarshipViewHolder) holder).btnScholarshipBody.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    break;

                case UNI_INFO_TYPE:

                    ((UniInfoViewHolder) holder).readMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = "http://www.royalroads.ca/about-royal-roads";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            mContext.startActivity(i);
                        }
                    });

                    ((UniInfoViewHolder) holder).webview.setWebViewClient(new WebViewClient());
                    ((UniInfoViewHolder) holder).webview.getSettings().setJavaScriptEnabled(true);
                    ((UniInfoViewHolder) holder).webview.loadUrl("http://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=14&size=600x720&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318&markers=color:red%7Ccolor:red%7Clabel:C%7C40.718217,-73.998284&sensor=false");

                    break;

                case SERVICES_TYPE:
                    ((ServicesViewHolder) holder).seeAllServices.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         Intent intent = new Intent(mContext, ServicesActivity.class);
                         mContext.startActivity(intent);

                     }
                 });
                    break;

                   case VISA_TYPE:
                       ((VisaViewHolder) holder).mVisaDetails.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               int vs = View.VISIBLE;
                               int ivs = View.INVISIBLE;

                               ToolBarBack toolBarCallBack = (ToolBarBack) mContext;

                               String tag = "dialogsMoreVisaInfo";
                               FragmentManager fragmentManager = ((CourseInfoActivity) mContext).getSupportFragmentManager();
                               FragmentVisa visaFrag = new FragmentVisa();

                               FragmentTransaction transaction = fragmentManager.beginTransaction();
                               transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                               transaction.add(android.R.id.content, visaFrag, tag)
                                       .addToBackStack(null).commit();

                               toolBarCallBack.switchBackVisible(vs, ivs);
                               toolBarCallBack.stringTag(tag);
                           }
                       });
                    break;

                case MEDIA_TYPE:
                    ((MediaViewHolder) holder).mYoutubeViewMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://www.youtube.com/user/monashunivideo/videos"));
                            mContext.startActivity(intent);
                        }
                    });
                    break;

                case APPLY_TYPE:
                    ((ApplyViewHolder) holder).btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, OffersActivity.class);
                            mContext.startActivity(intent);
                        }
                    });
                    break;

            }
        }

    }

    @Override
    public int getItemCount() {

        if(mCourses == null) {
            return 0;
        }
        else {
            return mCourses.size();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mCourses != null) {
            CourseInfoModel object = mCourses.get(position);
            if (object != null) {
                return object.getType();
            }
        }
        return 0;
    }

    public static class CourseInfoViewHolder extends RecyclerView.ViewHolder {

        Button btnCourseInfo;
        TextView txtFaculty;
        TextView txtCourseTitle;
        TextView txtCourseDuration;
        TextView txtCourseFees;
        TextView txtCourseFeeCurrency;


        public CourseInfoViewHolder(View itemView) {
            super(itemView);

            txtFaculty = (TextView) itemView.findViewById(R.id.txtCOurseInfoFaculty);
            txtCourseTitle = (TextView) itemView.findViewById(R.id.txtCourseInfoCourse);
            txtCourseDuration= (TextView) itemView.findViewById(R.id.txtCourseInfoDurationYears);
            txtCourseFees= (TextView) itemView.findViewById(R.id.txtCourseFeeValue);
            txtCourseFeeCurrency= (TextView) itemView.findViewById(R.id.txtCourseFeeCurrency);
            btnCourseInfo = (Button) itemView.findViewById(R.id.btnWebCourseInfo);

        }
    }

    public static class TwinningViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mUniName;
        ImageView imgLogo;


        public TwinningViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.txtTwinningTitle);
            mUniName = (TextView) itemView.findViewById(R.id.txtTwinningUni);
            imgLogo = (ImageView) itemView.findViewById(R.id.imgTwinningUniLogo);

        }
    }

    public static class MinReqViewHolder extends RecyclerView.ViewHolder {

        Button btnGradeReq;
        Button btnExemptions;
        TextView reqTitle;
        TextView aLvlGrades;
        TextView oLvlGrades;

        TextView ieltsOverall;
        TextView ieltsReading;
        TextView ieltsListening;
        TextView ieltsWriting;
        TextView ieltsSpeaking;

        TextView toeflOverall;
        TextView toeflReading;
        TextView toeflListening;
        TextView toeflWriting;
        TextView toeflSpeaking;


        public MinReqViewHolder(View itemView) {
            super(itemView);

            reqTitle = (TextView) itemView.findViewById(R.id.txtReqTitle);
            btnGradeReq = (Button) itemView.findViewById(R.id.btnDeatiledGradeReq);
            btnExemptions = (Button) itemView.findViewById(R.id.btnengReqExemptions);

            aLvlGrades = (TextView) itemView.findViewById(R.id.txtALevelsGrades);
            oLvlGrades = (TextView) itemView.findViewById(R.id.txtOLevelsGrades);

            ieltsOverall = (TextView) itemView.findViewById(R.id.txtIELTSOverallValue);
            ieltsReading = (TextView) itemView.findViewById(R.id.txtIELTSReading);
            ieltsListening = (TextView) itemView.findViewById(R.id.txtIELTSListening);
            ieltsWriting = (TextView) itemView.findViewById(R.id.txtIELTSWriting);
            ieltsSpeaking = (TextView) itemView.findViewById(R.id.txtIELTSSpeaking);

            toeflOverall = (TextView) itemView.findViewById(R.id.txtTOEFLOverallValue);
            toeflReading = (TextView) itemView.findViewById(R.id.txtTOEFLReading);
            toeflListening = (TextView) itemView.findViewById(R.id.txtTOEFListening);
            toeflWriting = (TextView) itemView.findViewById(R.id.txtTOEFLWriting);
            toeflSpeaking = (TextView) itemView.findViewById(R.id.txtTOEFLSpeaking);

        }
    }

    public static class TotalAvailableJobsViewHolder extends RecyclerView.ViewHolder {

        Button btnJobDetails;
        TextView totJobsDetails;


        public TotalAvailableJobsViewHolder(View itemView) {
            super(itemView);

            totJobsDetails = (TextView) itemView.findViewById(R.id.txtJobsDetails);
            btnJobDetails = (Button) itemView.findViewById(R.id.btnJobDetails);

        }
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {

        TextView matchValue;
        LinearLayout showInterests;


        public MatchViewHolder(View itemView) {
            super(itemView);

            matchValue = (TextView) itemView.findViewById(R.id.txtPercentageMatch);
            showInterests = (LinearLayout) itemView.findViewById(R.id.interestsBody);

        }
    }

    public static class VirtualTourViewHolder extends RecyclerView.ViewHolder {

        Button btnTripAdvisor;
        RelativeLayout btnGoogleEarth;


        public VirtualTourViewHolder(View itemView) {
            super(itemView);

            btnGoogleEarth = (RelativeLayout) itemView.findViewById(R.id.GoogleEarthContainer);
            btnTripAdvisor = (Button) itemView.findViewById(R.id.btnTripAdvisor);

        }
    }

    public static class ScholarshipViewHolder extends RecyclerView.ViewHolder {

        TextView txtScholarshipTitle;
        TextView txtScholarshipText;
        LinearLayout btnScholarshipBody;

        public ScholarshipViewHolder(View itemView) {
            super(itemView);

            txtScholarshipTitle = (TextView) itemView.findViewById(R.id.txtScholarshipTitle);
            txtScholarshipText = (TextView) itemView.findViewById(R.id.txtScholarshipText);
            btnScholarshipBody = (LinearLayout) itemView.findViewById(R.id.scholarshipAvaBody);
        }
    }

    public static class UniInfoViewHolder extends RecyclerView.ViewHolder {

        TextView txtTime;
        TextView txtPhone;
        TextView txtEmail;
        TextView txtWebsite;
        TextView totStudents;
        TextView txtUniDetails;
        TextView mTitle;
        WebView webview;

        Button readMore;


        public UniInfoViewHolder(View itemView) {
            super(itemView);


            mTitle = (TextView) itemView.findViewById(R.id.txtUniTitle);
            txtTime = (TextView) itemView.findViewById(R.id.txtOpenTime);
            txtPhone = (TextView) itemView.findViewById(R.id.txtUniInfoPhoneNo);
            txtEmail = (TextView) itemView.findViewById(R.id.txtUniInfoEmail);
            txtWebsite = (TextView) itemView.findViewById(R.id.txtUniInfoWebsite);
            totStudents = (TextView) itemView.findViewById(R.id.txtTotStudents);
            txtUniDetails = (TextView) itemView.findViewById(R.id.textUniInfoAbout);
            readMore = (Button) itemView.findViewById(R.id.btnWebAboutUs);
            webview = (WebView) itemView.findViewById(R.id.uni_mapview);

        }
    }

    public static class ServicesViewHolder extends RecyclerView.ViewHolder {

        ImageView imgServices1;
        ImageView imgServices2;
        ImageView imgServices3;
        ImageView imgServices4;
        ImageView imgServices5;
        ImageView imgServices6;
        ImageView imgServices7;
        ImageView imgServices8;
        TextView mTitle;
        Button seeAllServices;


        public ServicesViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.textServicesTitle);
            imgServices1 = (ImageView) itemView.findViewById(R.id.imgServices1);
            imgServices2 = (ImageView) itemView.findViewById(R.id.imgServices2);
            imgServices3 = (ImageView) itemView.findViewById(R.id.imgServices3);
            imgServices4 = (ImageView) itemView.findViewById(R.id.imgServices4);
            imgServices5 = (ImageView) itemView.findViewById(R.id.imgServices5);
            imgServices6 = (ImageView) itemView.findViewById(R.id.imgServices6);
            imgServices7 = (ImageView) itemView.findViewById(R.id.imgServices7);
            imgServices8 = (ImageView) itemView.findViewById(R.id.imgServices8);
            seeAllServices = (Button) itemView.findViewById(R.id.btnSeeAllServices);

        }
    }

    public static class VisaViewHolder extends RecyclerView.ViewHolder {


        TextView mTitle;
        TextView mVisaInfo;
        Button mVisaDetails;


        public VisaViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.textVisaTitle);
            mVisaInfo = (TextView) itemView.findViewById(R.id.txtVisaInfo);
            mVisaDetails = (Button) itemView.findViewById(R.id.btnReadMoreVisaInfo);

        }
    }

    public static class MediaViewHolder extends RecyclerView.ViewHolder {


        TextView mTitle;
        Button mYoutubeViewMore;
        YoutubeRecyclerAdapter adapters;

        String[] VideoID = new String[]{"ugdx4LAAPsc", "FGdp0xuO6vA", "RSiwdj3MIQA", "v7UqG3OPUSA"};
        String[] vTitle = new String[]{"Why did you choose Monash Arts?", "Monash Arts PAL Program",
                "Why should you study Arts at Monash?",
                "Double Degrees"};
        String[] videoViews = new String[]{"569 views", "454 views", "939 views", "1,915 views"};
        String[] videoPublished = new String[]{"4 months ago", "4 months ago", "4 months ago", "4 months ago"};
        String[] vDuration = new String[]{"0:46", "1:33", "3:36", "1:48"};


        public MediaViewHolder(View itemView) {
            super(itemView);

            Context context = itemView.getContext();

            mTitle = (TextView) itemView.findViewById(R.id.textMediaTitle);
            mYoutubeView = (RecyclerView) itemView.findViewById(R.id.youtube_list);
            mYoutubeViewMore = (Button) itemView.findViewById(R.id.btnViewMoreVideos);

            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mYoutubeView.setLayoutManager(linearLayoutManager);

            mYoutubeView.setHasFixedSize(true);

            Drawable dividerDrawable = ContextCompat.getDrawable(context, R.drawable.course_search_divider);

            RecyclerView.ItemDecoration itemDecoration = new
                    ListDividerItemDecoration(dividerDrawable);

            // recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
            mYoutubeView.addItemDecoration(itemDecoration);
            mYoutubeView.setItemAnimator(new DefaultItemAnimator());

            adapters =new YoutubeRecyclerAdapter(context, VideoID, vTitle, videoPublished, videoViews, vDuration);

            mYoutubeView.setAdapter(adapters);

        }
    }

    public static class ApplyViewHolder extends RecyclerView.ViewHolder {

        Button btnApply;


        public ApplyViewHolder(View itemView) {
            super(itemView);

            btnApply = (Button) itemView.findViewById(R.id.btnApplyForOffer);

        }
    }

}
