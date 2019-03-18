package edu.freshfutures.seeka.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import edu.freshfutures.seeka.CourseInfoActivity;
import edu.freshfutures.seeka.CustomWidgets.CustomViewPager;
import edu.freshfutures.seeka.EmailDialogFragment;
import edu.freshfutures.seeka.FragmentUnlock;
import edu.freshfutures.seeka.FragmentUnlockSendEmail;
import edu.freshfutures.seeka.HomeActivity;
import edu.freshfutures.seeka.Models.UnlockedModel;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.UnlockMapFragmentActivity;
import edu.freshfutures.seeka.volley.custom.CustomVolleyImageRequest;

/**
 * Created by tokmang on 7/20/2016.
 */
public class UnlockedRecycleAdapter extends RecyclerView.Adapter<UnlockedRecycleAdapter.ViewHolder> implements Animation.AnimationListener {

    private ArrayList<UnlockedModel> mUnlockedCourses;
    Animation swing_down;
    Animation swing_up;

    // Store the context for easy access
    private Context mContext;

    public UnlockedRecycleAdapter(Context ctx, ArrayList<UnlockedModel> ucs) {

        this.mUnlockedCourses = ucs;
        this.mContext = ctx;

    }

    @Override
    public int getItemCount() {
        return mUnlockedCourses.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.unlock_top_body, parent, false);

        ViewHolder viewholder = new ViewHolder(v);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        final UnlockedModel models = mUnlockedCourses.get(position);


        holder.rankValue.setText(models.getRankValue());

        holder.courseDuration.setText(models.getDurationTime() + " " + models.getDurationType());
        holder.costRange.setText(models.getCostRange());
        holder.costCurrency.setText(models.getCurrency());
        holder.courseTitle.setText(models.getCourseTitle());

        if(models.getCached() == 0) {
            Glide.with(mContext)
                    .load(models.getCtryImg())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.backImage);
        }

        else {
            Glide.with(mContext)
                    .load(models.getCtryImg())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.backImage);
        }

        holder.rowBody.setTag(models.getCourseId());

        holder.rowBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmailDialogFragment dialogFrag = (EmailDialogFragment) ((HomeActivity) mContext).getSupportFragmentManager().findFragmentByTag("dialogsEmail");

                if (dialogFrag != null && dialogFrag.isVisible()) {

                    if (holder.shade.getVisibility() == View.GONE) {
                        holder.shade.setVisibility(View.VISIBLE);
                    } else {
                        holder.shade.setVisibility(View.GONE);
                    }
                } else {

                    Intent intent = new Intent(mContext, CourseInfoActivity.class);
                    intent.putExtra("courseID", models.getCourseId());
                    mContext.startActivity(intent);

                }

            }

        });

        holder.imgShare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share data");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Share with others");
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }

        });

        holder.imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                swing_down = AnimationUtils.loadAnimation(mContext, R.anim.swing_down);
                swing_down.setAnimationListener(UnlockedRecycleAdapter.this);

                swing_up = AnimationUtils.loadAnimation(mContext, R.anim.swing_up);
                swing_up.setAnimationListener(UnlockedRecycleAdapter.this);

                if (holder.shareBack.getVisibility() == View.GONE) {

                    holder.shareBack.setVisibility(View.VISIBLE);
                    holder.shareBack.startAnimation(swing_down);

                } else {

                    holder.shareBack.setVisibility(View.GONE);
                    holder.shareBack.startAnimation(swing_up);

                }

            }
        });

        holder.ImgTwt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(
                        Intent.ACTION_CALL);
                callIntent.setData(Uri
                        .parse("tel:"
                                + "1800238494"));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mContext.startActivity(callIntent);

                PhoneCallListener phoneListener = new PhoneCallListener();
                TelephonyManager telephonyManager = (TelephonyManager) mContext
                        .getSystemService(
                                Context.TELEPHONY_SERVICE);
                telephonyManager
                        .listen(phoneListener,
                                PhoneStateListener.LISTEN_CALL_STATE);
            }
        });

        holder.imgFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v4.app.FragmentManager fragmentManager = ((HomeActivity) mContext).getSupportFragmentManager();
                FragmentUnlockSendEmail sendFragments = new FragmentUnlockSendEmail();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_up, R.anim.fade_away);

                transaction.add(android.R.id.content, sendFragments, "dialogsUnlockSendEmail")
                        .addToBackStack(null).commit();
            }

        });

        holder.imgLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((mContext), UnlockMapFragmentActivity.class);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        ImageButton imgPhone;
        ImageButton imgShare;
        TextView rankValue;
        ImageView backImage;

        CustomViewPager pager;
        ImageView shade;
        RelativeLayout shareBack;
        ImageButton imgFb;
        ImageButton ImgTwt;
        ImageButton imgLn;
        RelativeLayout rowBody;

        TextView courseDuration;
        TextView costRange;
        TextView costCurrency;
        TextView courseTitle;

        LinearLayout sponsLayout;


        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            backImage = (ImageView) itemView.findViewById(R.id.img_unlock_course);
            imgPhone = (ImageButton) itemView.findViewById(R.id.imageUnlockPhone);
            imgShare = (ImageButton) itemView.findViewById(R.id.imageUnlockShare);
            rankValue = (TextView) itemView.findViewById(R.id.txtWorldRankValueTop);
            courseDuration = (TextView) itemView.findViewById(R.id.txtUnlockCourseDuration);
            costRange = (TextView) itemView.findViewById(R.id.txtUnlockCourseCostPerYearValue);
            costCurrency = (TextView) itemView.findViewById(R.id.costCurrencyPerYear);
            courseTitle = (TextView) itemView.findViewById(R.id.textUnlockCourse);
            pager = (CustomViewPager) itemView.findViewById(R.id.pager);
            shade = (ImageView) itemView.findViewById(R.id.imgBackShadow);
            shareBack = (RelativeLayout) itemView.findViewById(R.id.unlock_share_container);
            imgFb = (ImageButton) itemView.findViewById(R.id.imgShareEmail);
            ImgTwt = (ImageButton) itemView.findViewById(R.id.imgShareCall);
            imgLn = (ImageButton) itemView.findViewById(R.id.imgShareLocation);
            rowBody = (RelativeLayout) itemView.findViewById(R.id.unlockBody);
            sponsLayout = (LinearLayout) itemView.findViewById(R.id.sponsLayout);

        }

    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart application
                    Intent i = (mContext).getPackageManager()
                            .getLaunchIntentForPackage(
                                    (mContext).getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }

}
