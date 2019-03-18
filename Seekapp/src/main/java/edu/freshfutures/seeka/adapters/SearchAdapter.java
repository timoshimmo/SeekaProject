package edu.freshfutures.seeka.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.freshfutures.seeka.HomeActivity;
import edu.freshfutures.seeka.LockedFragment;
import edu.freshfutures.seeka.MainSearchActivity;
import edu.freshfutures.seeka.Models.LockedModel;
import edu.freshfutures.seeka.Models.SearchModel;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.SearchByCountryActivity;
import edu.freshfutures.seeka.contentproviders.ColorSuggestion;
import edu.freshfutures.seeka.contentproviders.ColorWrapper;
import edu.freshfutures.seeka.contentproviders.DataHelper;
import edu.freshfutures.seeka.databases.EduQualificationDB;
import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;

/**
 * Created by tokmang on 7/27/2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private ArrayList<SearchModel> mSearchCountries;
    private Context mContext;
    private AlertDialog alertDialog;

    private static final int SEARCH_BOX_TYPE = 0;
    private static final int SEARCH_CTRY_COURSE_TYPE = 1;

    private JSONArray arrLevels;
    private LockedModel lModels;

    protected static String TOKENID = "token_id";
    private static String TAG = HomeActivity.class.getSimpleName();

    private int countData;
    private DecimalFormat myFormatter;

    private EduQualificationDB dbHandler;
    private LockedFragment fragmentLocked;

    private String courseEntered;

    private RecyclerView mRecyclerView;

    private String ctryCode;

    private String[] allCourseCode;
    private ArrayList<String> coursesCode = new ArrayList<>();

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;
    private SearchResultsListAdapter mSearchResultsAdapter;
    private boolean mIsDarkSearchTheme = false;
    private String mLastQuery = "";

    public SearchAdapter(Context ctx, ArrayList<SearchModel> sm) {
        this.mSearchCountries = sm;
        this.mContext = ctx;
        dbHandler = new EduQualificationDB(ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        Context context = parent.getContext();

        mRecyclerView = (RecyclerView) parent;

        switch(viewType) {

            case SEARCH_BOX_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.top_search_box, parent, false);
                return new TopSearchBoxHolder(view);

            case SEARCH_CTRY_COURSE_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.search_list_structure, parent, false);
                return new CtrySearchHolder(view);

        }

        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final SearchModel sModel = mSearchCountries.get(position);
        if(sModel != null) {

            switch(sModel.getSearchType()) {

                case SEARCH_BOX_TYPE:

                    ((TopSearchBoxHolder)holder).btnSelectLevel.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(mContext, MainSearchActivity.class);
                            mContext.startActivity(intent);

                        }

                    });

                    ((TopSearchBoxHolder)holder).btnSelectCountry.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(mContext, SearchByCountryActivity.class);
                            mContext.startActivity(intent);

                        }

                    });


                    ((TopSearchBoxHolder)holder).btnSearchCourses.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            courseEntered = TopSearchBoxHolder.mSearchView.getQuery();
                            makeJsonObjectRequest();

                        }

                    });

                    setupFloatingSearch();

                    break;

                case SEARCH_CTRY_COURSE_TYPE:

                    ((CtrySearchHolder)holder).tvCountry.setText(sModel.getCtryName());
                    ((CtrySearchHolder)holder).tvNumAvailable.setText(sModel.getAvailableValue() + " " + "Courses Available");

                    Glide.with(mContext).load(sModel.getSearchCtryImage()).into(((CtrySearchHolder)holder).imgSearchCourses);
                    Glide.with(mContext).load(sModel.getMonumentIcon()).into(((CtrySearchHolder)holder).img);
                    Glide.with(mContext).load(sModel.getInfoImg()).into(((CtrySearchHolder)holder).btnSearchInfo);


                    ((CtrySearchHolder)holder).searchBody.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String countryName = ((CtrySearchHolder)holder).tvCountry.getText().toString();

                            LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView
                                    .getLayoutManager();
                            layoutManager.scrollToPositionWithOffset(0, 0);

                            setSearchDestination(countryName);

                            allCourseCode = mContext.getResources().getStringArray(R.array.srch_result_country_code_array);
                            Collections.addAll(coursesCode, allCourseCode);

                            ctryCode = coursesCode.get(holder.getAdapterPosition());


                        }
                    });

                    ((CtrySearchHolder)holder).btnSearchInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            LayoutInflater inflaters = ((Activity) mContext).getLayoutInflater();

                            View dialogView = inflaters.inflate(R.layout.search_info_dialog_layout, null);
                            builder.setView(dialogView);

                            Button btnOkInfo = (Button) dialogView.findViewById(R.id.btnOkInfo);
                            TextView tvInfoMessage = (TextView) dialogView.findViewById(R.id.txtSearchInfoMessage);

                            alertDialog = builder.create();
                            tvInfoMessage.setText(sModel.getInfoMessage());

                            btnOkInfo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });

                            alertDialog.show();

                        }
                    });

                    break;

            }

        }

    }

    @Override
    public int getItemCount() {

        if(mSearchCountries == null) {
            return 0;
        }
        else {
            return mSearchCountries.size();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mSearchCountries != null) {
            SearchModel object = mSearchCountries.get(position);
            if (object != null) {
                return object.getSearchType();
            }
        }
        return -1;
    }

    private static class CtrySearchHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvCountry;
        TextView tvNumAvailable;
        ImageButton btnSearchInfo;
        RelativeLayout searchBody;
        ImageView imgSearchCourses;

        CtrySearchHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.imgSearchCityMonuments);
            tvCountry = (TextView) itemView.findViewById(R.id.txtSearchCountry);
            tvNumAvailable = (TextView) itemView.findViewById(R.id.txtNumCoursesAvailable);
            searchBody = (RelativeLayout) itemView.findViewById(R.id.searchBody);
            imgSearchCourses = (ImageView) itemView.findViewById(R.id.img_search_courses);
            btnSearchInfo = (ImageButton) itemView.findViewById(R.id.btnSearchInfo);
        }

    }

    private static class TopSearchBoxHolder extends RecyclerView.ViewHolder {

        RelativeLayout btnSelectCountry;
        RelativeLayout btnSelectLevel;
       // EditText txtSearchCourses;
        Button btnSearchCourses;
        private static FloatingSearchView mSearchView;

        static TextView txtselectedDestination;
        static TextView txtselectedLevel;

        TopSearchBoxHolder(View itemView) {

            super(itemView);

            btnSelectCountry = (RelativeLayout) itemView.findViewById(R.id.selectDestBody);
            btnSelectLevel = (RelativeLayout) itemView.findViewById(R.id.selectLvlBody);
            btnSearchCourses = (Button) itemView.findViewById(R.id.btnSearchCourse);

            txtselectedDestination = (TextView) itemView.findViewById(R.id.selectDestTitle);
            txtselectedLevel = (TextView) itemView.findViewById(R.id.selectIdTitle);

            mSearchView = (FloatingSearchView) itemView.findViewById(R.id.editSearchCourses);

        }

    }

    private void makeJsonObjectRequest() {

        String requestURL = Url.URL_SEARCH_RESULT;
        JSONObject obj = new JSONObject();

        SharedPreferences getSelectedLevels = mContext.getSharedPreferences("PREFLVLSELECTED", Context.MODE_PRIVATE);
        String lvlSelected = getSelectedLevels.getString("LEVEL_SELECTED", "[]");


        SharedPreferences getSelectedDestination = mContext.getSharedPreferences("PREFDESTSELECTED", Context.MODE_PRIVATE);
        String ctrySelected = getSelectedDestination.getString("DESTINATION_SELECTED", "");

        try {
            arrLevels = new JSONArray(lvlSelected);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences getSession = mContext.getSharedPreferences(mContext.getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
        String session = getSession.getString(TOKENID, "");


        try {

            obj.put("searchText", courseEntered);
            obj.put("countryCode", ctrySelected);
            obj.put("sessionId", session);
            obj.put("pageNo", "1");
            obj.put("courseTypes", arrLevels);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    int[] countryImages = new int[]{R.mipmap.search_germany, R.mipmap.search_iceland,
                            R.mipmap.search_czech_republic, R.mipmap.search_denmark};

                    String[] matchValue = new String[]{"75%", "77%", "62%", "79%", "55%"};
                    JSONArray array = response.getJSONArray("data");

                    String resultNo = String.valueOf(array.length()) + " Results";

                    LockedFragment.lockedList = new ArrayList<>();
                    myFormatter = new DecimalFormat("0.00");

                    for(int i=0; i < array.length(); i++) {

                        JSONObject list = array.getJSONObject(i);

                        lModels = new LockedModel();

                        lModels.setInstitution(list.getString("country"));
                        lModels.setRankValue(list.getString("avgWorldRank"));
                        lModels.setRecgnitionValue(list.getString("recognition"));
                        lModels.setRecognitionType(list.getString("recognitionType"));
                        lModels.setDurationTime(list.getString("durationType"));
                        lModels.setDurationType(list.getString("durationTime"));
                        lModels.setCourseTitle(list.getString("courseTitle"));
                        lModels.setRemarks(list.getString("remarks"));
                        lModels.setCourseID(list.getInt("courseId"));

                        String getCurrency = list.getString("currency");

                        SharedPreferences getCurrencyId = mContext.getSharedPreferences("PREFBASECURRENCY", Context.MODE_PRIVATE);
                        String currComb = getCurrencyId.getString("BASECURRENCY", "");

                        String idCode = currComb.trim()+getCurrency.trim();

                        countData = dbHandler.getCurrencyCount();

                        if(countData > 0) {

                            double ratings = dbHandler.getRatings(idCode);

                            if(ratings > 0) {

                                double costRange = Double.valueOf(list.getString("costRange"));
                                double costSaving = Double.valueOf(list.getString("costSaving"));

                                double result = ratings * costRange;
                                double savedResult = ratings * costSaving;

                                String rangeCost = myFormatter.format(result);
                                String savingCost = myFormatter.format(savedResult);

                                lModels.setCostRange(rangeCost);
                                lModels.setCostSavingValue(savingCost);

                                lModels.setCostSavingCurrency(currComb.trim());

                            }

                            else {

                                lModels.setCostRange(list.getString("costRange"));
                                lModels.setCostSavingValue(list.getString("costSaving"));
                                lModels.setCostSavingCurrency(getCurrency);

                            }

                        }

                        else {

                            lModels.setCostRange(list.getString("costRange"));
                            lModels.setCostSavingValue(list.getString("costSaving"));

                            lModels.setCostSavingCurrency(getCurrency);
                        }

                        LockedFragment.lockedList.add(lModels);

                    }

                    fragmentLocked = LockedFragment.newInstance(resultNo);
                    ((HomeActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .add(R.id.frame_container, fragmentLocked, "LOCKED_FRAGMENT").commit();

                } catch (JSONException e) {

                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(mContext.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    public static void setSearchDestination(String destinations) {

        if(!destinations.equals("")) {
            TopSearchBoxHolder.txtselectedDestination.setText(destinations);
        }

    }


    public static void setSearchLevels(String levels) {

        if(!levels.equals("")) {
            TopSearchBoxHolder.txtselectedLevel.setText(levels);
        }

    }

    private void setupFloatingSearch() {

        mRecyclerView.setNestedScrollingEnabled(true);

        TopSearchBoxHolder.mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    TopSearchBoxHolder.mSearchView.clearSuggestions();
                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    TopSearchBoxHolder.mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    DataHelper.findSuggestions(mContext, newQuery, 5, FIND_SUGGESTION_SIMULATED_DELAY, new DataHelper.OnFindSuggestionsListener() {

                        @Override
                        public void onResults(List<ColorSuggestion> results) {

                            //this will swap the data and
                            //render the collapse/expand animations as necessary
                            TopSearchBoxHolder.mSearchView.swapSuggestions(results);

                            //let the users know that the background
                            //process has completed
                            TopSearchBoxHolder.mSearchView.hideProgress();
                        }
                    });
                }

                Log.d(TAG, "onSearchTextChanged()");

            }
        });

        TopSearchBoxHolder.mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                Log.d(TAG, "onHomeClicked()");
            }
        });

        TopSearchBoxHolder.mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                Log.d(TAG, "onSuggestionClicked()");

                mLastQuery = searchSuggestion.getBody();
                ColorSuggestion colorSuggestion = (ColorSuggestion) searchSuggestion;

                DataHelper helper = new DataHelper();
                helper.findColors(mContext, colorSuggestion.getBody(),
                        new DataHelper.OnFindColorsListener() {

                            @Override
                            public void onResults(List<ColorWrapper> results) {
                                // mSearchResultsAdapter.swapData(results);
                            }

                        });

            }

            @Override
            public void onSearchAction(String query) {

                mLastQuery = query;
                DataHelper helper = new DataHelper();

                helper.findColors(mContext, query,
                        new DataHelper.OnFindColorsListener() {

                            @Override
                            public void onResults(List<ColorWrapper> results) {

                                mSearchResultsAdapter.swapData(results);

                            }

                        });
                Log.d(TAG, "onSearchAction()");
            }
        });

        TopSearchBoxHolder.mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

                //show suggestions when search bar gains focus (typically history suggestions)
                TopSearchBoxHolder.mSearchView.swapSuggestions(DataHelper.getHistory(mContext, 3));

                Log.d(TAG, "onFocus()");
            }

            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
                TopSearchBoxHolder.mSearchView.setSearchBarTitle(mLastQuery);

                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns
                //mSearchView.setSearchText(searchSuggestion.getBody());

                Log.d(TAG, "onFocusCleared()");
            }

        });

        TopSearchBoxHolder.mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {

            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {

                ColorSuggestion colorSuggestion = (ColorSuggestion) item;

                String textColor = mIsDarkSearchTheme ? "#ffffff" : "#000000";
                String textLight = mIsDarkSearchTheme ? "#bfbfbf" : "#787878";

                if (colorSuggestion.getIsHistory()) {

                    leftIcon.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(),
                            R.drawable.ic_history_black_24dp, null));

                    Util.setIconColor(leftIcon, Color.parseColor(textColor));
                    leftIcon.setAlpha(.36f);

                }

                else {

                    leftIcon.setAlpha(0.0f);
                    leftIcon.setImageDrawable(null);

                }

                textView.setTextColor(Color.parseColor(textColor));
                String text = colorSuggestion.getBody()
                        .replaceFirst(TopSearchBoxHolder.mSearchView.getQuery(),
                                "<font color=\"" + textLight + "\">" + TopSearchBoxHolder.mSearchView.getQuery() + "</font>");
                textView.setText(Html.fromHtml(text));
            }

        });

    }

}
