package edu.freshfutures.seeka;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;

import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;

import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import edu.freshfutures.seeka.adapters.CustomAdapter;

/**
 * Created by tokmang on 3/20/2016.
 */
public class OverscrollListView  extends ListView implements AbsListView.OnScrollListener {

    public int nHeaders = 1, nFooters = 1, divHeight = 0, delay = 15;
    private int firstVis, visibleCnt, lastVis, totalItems, scrollstate;

    LayoutInflater inflater;
    public View mProgressView;
    private LoadingTask mAuthTask = null;
    View footer;

    private static final int MAX_Y_OVERSCROLL_DISTANCE = 100;

    private Context mContext;
    private Activity mCtx;
    private int mMaxYOverscrollDistance;
    private int previousTotal = 0;
    private final int MAXIMUM_ITEMS = 10;


    public static final String COUNTRY_BACKGROUND = "country_background";
    public static final String COUNTRY_NAME = "country_name";
    public static final String COUNTRY_IMAGE = "country_icons";
    public static final String COURSE_AVAILABLE = "available_courses";
    public static final String COURSE_ACCEPTED = "accepted_courses";
    public static final String COURSE_INFO = "info_courses";
    static CustomAdapter cdpt;
    static HashMap<String, int[]> map;
    static ArrayList<HashMap<String, int[]>> searchList = null;

    public OverscrollListView(Context context)
    {
        super(context);
        mContext = context;
        initBounceListView(mContext);
    }

    public OverscrollListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        initBounceListView(mContext);
    }

    public OverscrollListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mContext = context;
        initBounceListView(mContext);
    }

    private void initBounceListView(Context context)
    {
        //get the density of the screen and do some maths with it on the max overscroll distance
        //variable so that you get similar behaviors no matter what the screen size

        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;

        final View v = new View(context);

        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);

        v.setMinimumHeight(Math.min(display.getWidth(), 1));
        addHeaderView(v, null, false);

    }

    public void initializeValues(Activity ctx) {

        mCtx = ctx;

        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        nHeaders = getHeaderViewsCount();
        nFooters = getFooterViewsCount();
        divHeight = getDividerHeight();
        firstVis = 0;
        visibleCnt = 0;
        lastVis = 0;
        totalItems = 0;
        scrollstate = 0;
        footer = inflater.inflate(
                R.layout.load_progress, null);

        mProgressView = footer.findViewById(R.id.load_data);
        addFooterView(footer, null, false);

        searchList = new ArrayList<>();

        int[] CountryBackground = new int[] {R.mipmap.search_australia, R.mipmap.search_austria, R.mipmap.search_brazil,
                R.mipmap.search_canada, R.mipmap.search_china, R.mipmap.search_czech_republic, R.mipmap.search_denmark,
                R.mipmap.search_finland, R.mipmap.search_france, R.mipmap.search_germany, R.mipmap.search_iceland,
                R.mipmap.search_india, R.mipmap.search_ireland, R.mipmap.search_italy, R.mipmap.search_japan,
                R.mipmap.search_malaysia, R.mipmap.search_mexico, R.mipmap.search_netherlands,
                R.mipmap.search_new_zealand, R.mipmap.search_poland, R.mipmap.search_russia,
                R.mipmap.search_singapore, R.mipmap.search_slovenia, R.mipmap.search_south_africa,
                R.mipmap.search_spain, R.mipmap.search_sweden, R.mipmap.search_switzerland,
                R.mipmap.search_u_a_e, R.mipmap.search_u_s_a, R.mipmap.search_united_kingdom};

        int[] CountryLists = new int[] {R.string.country_australia, R.string.country_austria, R.string.country_brazil,
                R.string.country_canada, R.string.country_china, R.string.country_czech, R.string.country_denmark,
                R.string.country_finland, R.string.country_france, R.string.country_germany, R.string.country_iceland,
                R.string.country_india, R.string.country_ireland, R.string.country_italy, R.string.country_japan,
                R.string.country_malaysia, R.string.country_mexico, R.string.country_netherlands, R.string.country_new_zealand,
                R.string.country_poland, R.string.country_russia, R.string.country_singapore,
                R.string.country_slovenia, R.string.country_south_africa, R.string.country_spain,
                R.string.country_sweden, R.string.country_switzerland, R.string.country_u_a_e, R.string.country_u_s_a,
                R.string.country_uk};

        int[] MonumentListImg = new int[] {R.mipmap.sydney_building, R.mipmap.austria_building, R.mipmap.austria_building,
                R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.austria_building, R.mipmap.austria_building, R.mipmap.austria_building,
                R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.austria_building, R.mipmap.austria_building,
                R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.austria_building,
                R.mipmap.austria_building, R.mipmap.sydney_building, R.mipmap.austria_building,
                R.mipmap.austria_building, R.mipmap.austria_building, R.mipmap.austria_building,
                R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.austria_building,
                R.mipmap.austria_building, R.mipmap.sydney_building, R.mipmap.austria_building,
                R.mipmap.austria_building};

        int[] CAvailableLists = new int[] {R.string.australia_available, R.string.austria_available, R.string.belgium_available,
                R.string.brazil_available, R.string.canada_available, R.string.denmark_available,
                R.string.england_available, R.string.france_available,
                R.string.germany_available, R.string.holland_available, R.string.england_available, R.string.france_available,
                R.string.germany_available, R.string.holland_available, R.string.australia_available,
                R.string.austria_available, R.string.belgium_available, R.string.australia_available,
                R.string.austria_available, R.string.belgium_available, R.string.germany_available, R.string.holland_available, R.string.australia_available,
                R.string.austria_available, R.string.belgium_available,  R.string.holland_available, R.string.england_available, R.string.france_available,
                R.string.brazil_available, R.string.canada_available};

        int[] CAcceptedLists = new int[] {R.string.australia_accepted, R.string.austria_accepted, R.string.belgium_accepted,
                R.string.brazil_accepted, R.string.canada_accepted, R.string.denmark_accepted, R.string.england_accepted,
                R.string.france_accepted, R.string.germany_accepted, R.string.holland_accepted, R.string.england_accepted,
                R.string.france_accepted, R.string.germany_accepted, R.string.holland_accepted, R.string.australia_accepted,
                R.string.austria_accepted, R.string.belgium_accepted, R.string.australia_accepted,
                R.string.austria_accepted, R.string.belgium_accepted,
                R.string.france_accepted, R.string.germany_accepted, R.string.holland_accepted, R.string.australia_accepted,
                R.string.austria_accepted, R.string.belgium_accepted, R.string.australia_accepted,
                R.string.austria_accepted, R.string.belgium_accepted, R.string.australia_accepted};

        int[] coursesInfo = new int[] {R.mipmap.search_info_icon, R.mipmap.search_info_red_icon, R.mipmap.search_info_icon,
        R.mipmap.search_info_red_icon,R.mipmap.search_info_icon, R.mipmap.search_info_icon,R.mipmap.search_info_icon,
                R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon,
                R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon,
                R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon,
                R.mipmap.search_info_icon,  R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon,
                R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon,
                R.mipmap.search_info_icon, R.mipmap.search_info_icon};

        map = new HashMap<>();

        for(int i=0; i < CountryLists.length; i++) {
            map.put(COUNTRY_BACKGROUND, CountryBackground);
            map.put(COUNTRY_IMAGE, MonumentListImg);
            map.put(COUNTRY_NAME, CountryLists);
            map.put(COURSE_AVAILABLE, CAvailableLists);
            map.put(COURSE_ACCEPTED, CAcceptedLists);
            map.put(COURSE_INFO, coursesInfo);

            searchList.add(map);
        }

        cdpt = new CustomAdapter(ctx, searchList);
        setAdapter(cdpt);

    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)
    {
        //This is where the magic happens, we have replaced the incoming maxOverScrollY with our own custom variable mMaxYOverscrollDistance;
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
         firstVis = firstVisibleItem;
         visibleCnt = visibleItemCount;
         totalItems = totalItemCount;
         lastVis = firstVisibleItem + visibleItemCount;


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public class LoadingTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

            boolean newData;

            try {
                // Simulate network access.
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                return false;
            }

            searchList.clear();

            int[] CountryBackground = new int[] {R.mipmap.aussie_image, R.mipmap.ctry_austria_image, R.mipmap.switzerland_image,
                    R.mipmap.usa_image, R.mipmap.ctry_canada_image, R.mipmap.ctry_denmark_image, R.mipmap.ctry_finland_image,
                    R.mipmap.ctry_france_image, R.mipmap.ctry_germany_image, R.mipmap.malaysia_image};
            int[] CountryLists = new int[] {R.string.country_australia, R.string.country_austria, R.string.country_australia,
                    R.string.country_brazil, R.string.country_canada, R.string.country_denmark, R.string.country_australia, R.string.country_france,
                    R.string.country_germany, R.string.country_australia};
            int[] MonumentListImg = new int[] {R.mipmap.sydney_building, R.mipmap.austria_building, R.mipmap.austria_building,
                    R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.austria_building, R.mipmap.austria_building, R.mipmap.austria_building,
                    R.mipmap.sydney_building, R.mipmap.sydney_building};
            int[] CAvailableLists = new int[] {R.string.australia_available, R.string.austria_available, R.string.belgium_available,
                    R.string.brazil_available, R.string.canada_available, R.string.denmark_available, R.string.england_available, R.string.france_available,
                    R.string.germany_available, R.string.holland_available};
            int[] CAcceptedLists = new int[] {R.string.australia_accepted, R.string.austria_accepted, R.string.belgium_accepted,
                    R.string.brazil_accepted, R.string.canada_accepted, R.string.denmark_accepted, R.string.england_accepted, R.string.france_accepted,
                    R.string.germany_accepted, R.string.holland_accepted};

            int[] coursesInfo = new int[] {R.mipmap.search_info_icon, R.mipmap.search_info_red_icon, R.mipmap.search_info_icon,
                    R.mipmap.search_info_red_icon,R.mipmap.search_info_icon, R.mipmap.search_info_icon,R.mipmap.search_info_icon,
                    R.mipmap.search_info_icon, R.mipmap.search_info_icon, R.mipmap.search_info_icon};

            for(int i=0; i < 20; i++) {
                map.put(COUNTRY_BACKGROUND, CountryBackground);
                map.put(COUNTRY_IMAGE, MonumentListImg);
                map.put(COUNTRY_NAME, CountryLists);
                map.put(COURSE_AVAILABLE, CAvailableLists);
                map.put(COURSE_ACCEPTED, CAcceptedLists);
                map.put(COURSE_INFO, coursesInfo);

                searchList.add(map);
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if(success) {
                cdpt.notifyDataSetChanged();
            }

            else {
                Toast.makeText(mContext.getApplicationContext(),"All available data are currently displayed! ", Toast.LENGTH_LONG).show();
            }

            mAuthTask = null;
            showProgress(false);

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public void loadData() {
        if (mAuthTask != null) {
            return;
        }

        mAuthTask = new LoadingTask();
        mAuthTask.execute((Void) null);

    }


}
