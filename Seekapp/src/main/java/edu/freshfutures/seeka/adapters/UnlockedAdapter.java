package edu.freshfutures.seeka.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.net.Uri;

import android.os.AsyncTask;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.util.LruCache;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeCallbacks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.freshfutures.seeka.CourseInfoActivity;
import edu.freshfutures.seeka.CustomWidgets.CustomTextViewLight;
import edu.freshfutures.seeka.CustomWidgets.CustomTextViewRegular;
import edu.freshfutures.seeka.CustomWidgets.CustomViewPager;
import edu.freshfutures.seeka.EmailDialogFragment;
import edu.freshfutures.seeka.FragmentUnlock;
import edu.freshfutures.seeka.FragmentUnlockSendEmail;
import edu.freshfutures.seeka.HomeActivity;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.UnlockMapFragmentActivity;


/**
 * Created by tokmang on 4/20/2016.
 */
/*
public class UnlockedAdapter extends BaseAdapter implements Animation.AnimationListener {

    private static LayoutInflater inflater = null;
    Activity activity;
    private HashMap<String, int[]> courses;
    private ArrayList<HashMap<String, int[]>> courseList;
    private ImageButton clickedButton;
    private TextView tabText;
    Animation swing_down;
    Animation swing_up;

    int listPosition;

    public UnlockedAdapter(Activity a, ArrayList<HashMap<String, int[]>> cl, ImageButton btn, TextView txt) {

        this.activity = a;
        this.courseList = cl;
        this.clickedButton = btn;
        this.tabText = txt;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public int getItemViewType(int position) {
        courses = courseList.get(position);
        return activity.getResources().getInteger(courses.get(FragmentUnlock.SPONSORED_STATUS)[position]);
    }

    @Override
    public int getCount() {
        return courseList.size();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Holder holder;
        int type = getItemViewType(position);

        if(convertView == null) {

            holder = new Holder();

            if(type == 1) {
                convertView = inflater.inflate(R.layout.unlock_top_body, parent, false);

            }
            else {
                convertView = inflater.inflate(R.layout.unlock_bottom, parent, false);

            }

            holder.backImage = (ImageView) convertView.findViewById(R.id.img_unlock_course);

            holder.imgPhone = (ImageButton) convertView.findViewById(R.id.imageUnlockPhone);
            holder.imgShare = (ImageButton) convertView.findViewById(R.id.imageUnlockShare);
            holder.rankValue = (TextView) convertView.findViewById(R.id.txtWorldRankValueTop);

            holder.pager = (CustomViewPager) convertView.findViewById(R.id.pager);
            holder.shade = (ImageView) convertView.findViewById(R.id.imgBackShadow);
            holder.shareBack = (RelativeLayout) convertView.findViewById(R.id.unlock_share_container);


            holder.imgFb = (ImageButton) convertView.findViewById(R.id.imgShareEmail);
            holder.ImgTwt = (ImageButton) convertView.findViewById(R.id.imgShareCall);
            holder.imgLn = (ImageButton) convertView.findViewById(R.id.imgShareLocation);
            holder.ImgGp = (ImageButton) convertView.findViewById(R.id.imgShareSeekaContact);

            holder.pager.setAdapter(new PacksPagerAdapter(activity));
            holder.pager.setPagingEnabled(false);

            convertView.setTag(holder);

        }

        else {
            holder = (Holder) convertView.getTag();

        }


        swing_down = AnimationUtils.loadAnimation(activity, R.anim.swing_down);
        swing_down.setAnimationListener(this);

        swing_up = AnimationUtils.loadAnimation(activity, R.anim.swing_up);
        swing_up.setAnimationListener(this);


        courses = courseList.get(position);

        holder.pos = position;
        holder.rankValue.setText(activity.getResources().getString(courses.get(FragmentUnlock.RANK_VALUE)[position]));
        holder.pager.setTag(position);

        if(type == 1) {
            new SpnsimageDownloaderTask().execute(holder);
        }
        else {
            new imageDownloaderTask().execute(holder);
        }

        clickedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomViewPager myPager;
                int rowSize = parent.getChildCount();

                holder.pager.setPagingEnabled(true);

                    if (tabText.getCurrentTextColor() == Color.parseColor("#646969")) {
                        clickedButton.setImageResource(R.mipmap.unlock_packs_icon_active);
                        tabText.setTextColor(Color.parseColor("#0091f0"));

                        holder.pager.setCurrentItem(1, true);
                    } else {
                        clickedButton.setImageResource(R.mipmap.unlock_packs_icon);
                        tabText.setTextColor(Color.parseColor("#646969"));

                        holder.pager.setCurrentItem(0, true);
                    }

                holder.pager.setPagingEnabled(false);


            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // listPosition = ((ListView)parent).getPositionForView(v);


                EmailDialogFragment dialogFrag = (EmailDialogFragment) ((HomeActivity)activity).getSupportFragmentManager().findFragmentByTag("dialogsEmail");

                    if(dialogFrag != null && dialogFrag.isVisible()) {

                        if(holder.shade.getVisibility() ==  View.GONE) {
                            holder.shade.setVisibility(View.VISIBLE);
                        }
                        else {
                            holder.shade.setVisibility(View.GONE);
                        }
                    }

                    else {

                        Intent intent = new Intent(activity, CourseInfoActivity.class);
                        activity.startActivity(intent);
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
                activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }

        });

        holder.imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.shareBack.getVisibility() == View.GONE) {

                    holder.shareBack.setVisibility(View.VISIBLE);
                    holder.shareBack.startAnimation(swing_down);

                }

                else {

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
                activity.startActivity(callIntent);

                PhoneCallListener phoneListener = new PhoneCallListener();
                TelephonyManager telephonyManager = (TelephonyManager) activity
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

                android.support.v4.app.FragmentManager fragmentManager = ((HomeActivity)activity).getSupportFragmentManager();
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
                Intent intent = new Intent((activity), UnlockMapFragmentActivity.class);
                activity.startActivity(intent);
            }
        });

        return convertView;
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

    public class Holder {
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
        ImageButton ImgGp;
        Bitmap bitmap;

        RoundedBitmapDrawable rndbitmap;
        int pos;

        public Holder() {

        }

    }

    private class SpnsimageDownloaderTask extends AsyncTask<Holder,Void,Holder> {


        @Override
        protected Holder doInBackground(Holder... params) {

            Holder viewHolder = params[0];
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 1;
            options.inScaled = true;

            viewHolder.bitmap = BitmapFactory.decodeResource(activity.getResources(),
                    courses.get(FragmentUnlock.UNI_BACKGROUND)[viewHolder.pos], options);

          //  String key = "search_images" + String.valueOf(viewHolder.pos);
         //   addBitmapToMemoryCache(key,
         //           viewHolder.bitmap);

            return viewHolder;
        }

        @Override
        protected void onPostExecute(Holder result) {
            // TODO Auto-generated method stub

            if(result.bitmap != null) {
                result.backImage.setImageBitmap(result.bitmap);
            }
        }
    }

    /*

    private class SpnscacheDownloaderTask extends AsyncTask<Holder,Void,Holder> {

        @Override
        protected Holder doInBackground(Holder... params) {

            Holder viewHolder = params[0];
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 1;
            options.inScaled = true;

            String key = "search_images" + String.valueOf(viewHolder.pos);
            viewHolder.bitmap = getBitmapFromMemCache(key);

            return viewHolder;
        }

        @Override
        protected void onPostExecute(Holder result) {
            // TODO Auto-generated method stub

            if(result.bitmap != null) {
                result.backImage.setImageBitmap(result.bitmap);
            }
        }
    }




    private class imageDownloaderTask extends AsyncTask<Holder,Void,Holder> {


        @Override
        protected Holder doInBackground(Holder... params) {

            Holder viewHolder = params[0];
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 1;
            options.inScaled = true;

            viewHolder.bitmap = BitmapFactory.decodeResource(activity.getResources(),
                    courses.get(FragmentUnlock.UNI_BACKGROUND)[viewHolder.pos], options);

           // String key = "search_images" + String.valueOf(viewHolder.pos);

          //  addBitmapToMemoryCache(key,
           //         viewHolder.bitmap);

            viewHolder.rndbitmap = RoundedBitmapDrawableFactory.create(activity.getResources(), viewHolder.bitmap);
            float roundPx = 9.00f;
            viewHolder.rndbitmap.setCornerRadius(roundPx);

            return viewHolder;
        }

        @Override
        protected void onPostExecute(Holder result) {
            // TODO Auto-generated method stub

            if(result.bitmap != null) {
                result.backImage.setBackgroundDrawable(result.rndbitmap); //setImageBitmap(result.bitmap);
            }
        }
    }

  /*  private class cacheDownloaderTask extends AsyncTask<Holder,Void,Holder> {

        @Override
        protected Holder doInBackground(Holder... params) {

            Holder viewHolder = params[0];
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 1;
            options.inScaled = true;

            String key = "search_images" + String.valueOf(viewHolder.pos);
            viewHolder.bitmap = getBitmapFromMemCache(key);

            viewHolder.rndbitmap = RoundedBitmapDrawableFactory.create(activity.getResources(), viewHolder.bitmap);
            float roundPx = 13.50f;
            viewHolder.rndbitmap.setCornerRadius(roundPx);

            return viewHolder;
        }

        @Override
        protected void onPostExecute(Holder result) {
            // TODO Auto-generated method stub

            if(result.bitmap != null) {
                result.backImage.setBackgroundDrawable(result.rndbitmap);
            }
        }
    }


    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    // getting Bitmap from memory cache
    @SuppressLint("NewApi")
    public static Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap)mMemoryCache.get(key);
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
                    Intent i = (activity).getBaseContext()
                            .getPackageManager()
                            .getLaunchIntentForPackage(
                                    (activity).getBaseContext()
                                            .getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }

}
    */