package edu.freshfutures.seeka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.arlib.floatingsearchview.FloatingSearchView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

import edu.freshfutures.seeka.Models.SearchTypeModel;
import edu.freshfutures.seeka.adapters.MainSearchAdapter;
import edu.freshfutures.seeka.adapters.SearchAdapter;
import edu.freshfutures.seeka.adapters.SearchResultsListAdapter;


public class MainSearchActivity extends AppCompatActivity {

    ImageButton btnBack;
    ListView lisCoures;
    Button btnSave;
    ArrayList<String> courseLevels = new ArrayList<>();
    ArrayList<String> courseLevelsCode = new ArrayList<>();

    public String[] levels;
    public String[] levelsCode;

    private FloatingSearchView mSearchView;
    private final String TAG = "MainSearchActivity";
    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;
    private SearchResultsListAdapter mSearchResultsAdapter;
    private boolean mIsDarkSearchTheme = false;
    private String mLastQuery = "";

    JSONArray lvlList;
    JSONArray lvlListString;

    private SearchTypeModel model;
    private static ArrayList<SearchTypeModel> arrLevelModel;
    MainSearchAdapter adapter;

    JSONArray arrLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        Toolbar parent = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(parent);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        lisCoures = (ListView) findViewById(R.id.lisSearchEduLevel);
        btnBack = (ImageButton) parent.findViewById(R.id.btnDialogBack);
        btnSave = (Button) findViewById(R.id.btnSearchLevel);

        //searchInput = (ImageButton) findViewById(R.id.btnSearchCourses);
        // txtSearch = (EditText) findViewById(R.id.editTxtSearchCourse);
       // mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
       // setupFloatingSearch();

        lvlList = new JSONArray();

        levels = getResources().getStringArray(R.array.search_level_list);
        Collections.addAll(courseLevels, levels);

        levelsCode = getResources().getStringArray(R.array.search_level_code);
        Collections.addAll(courseLevelsCode, levelsCode);

        adapter = new MainSearchAdapter(MainSearchActivity.this, courseLevels, courseLevelsCode);
        lisCoures.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               onBackPressed();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrLevelModel = new ArrayList<>();
                arrLevelModel = adapter.arrModel;

                arrLevel = new JSONArray();
                lvlListString = new JSONArray();

                StringBuilder sb = new StringBuilder();

                for(int i = 0; i < arrLevelModel.size(); i++) {

                    model = arrLevelModel.get(i);

                    String levels = model.getSearchLevel();
                    String levelsFull = model.getSearchLevelFull();

                    arrLevel.put(levels);
                    lvlListString.put(levelsFull);

                }

                for(int i=0; i < lvlListString.length(); i++) {

                    try {

                        String data  = lvlListString.get(i).toString();
                        sb.append(data).append(" ");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                String lvlResult = sb.toString();

                SearchAdapter.setSearchLevels(lvlResult);

                SharedPreferences lvlSelectedPref = getSharedPreferences("PREFLVLSELECTED", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorLvl = lvlSelectedPref.edit();
                editorLvl.putString("LEVEL_SELECTED", arrLevel.toString());
                editorLvl.apply();

                onBackPressed();

            }
        });

    /*    searchInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSearchActivity.this, HomeActivity.class);
                intent.putExtra("SEARCH_RESULT", "searched");
                startActivity(intent);
            }
        });

        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(MainSearchActivity.this, HomeActivity.class);
                    intent.putExtra("SEARCH_RESULT", "searched");
                    startActivity(intent);

                    return true;
                }

                return false;
            }
        });
*/
    }


 /*   private void setupFloatingSearch() {
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    DataHelper.findSuggestions(MainSearchActivity.this, newQuery, 5, FIND_SUGGESTION_SIMULATED_DELAY, new DataHelper.OnFindSuggestionsListener() {

                        @Override
                        public void onResults(List<ColorSuggestion> results) {

                            //this will swap the data and
                            //render the collapse/expand animations as necessary
                            mSearchView.swapSuggestions(results);

                            //let the users know that the background
                            //process has completed
                            mSearchView.hideProgress();
                        }
                    });
                }

                Log.d(TAG, "onSearchTextChanged()");
            }
        });

        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                Log.d(TAG, "onHomeClicked()");
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                Intent intent = new Intent(MainSearchActivity.this, HomeActivity.class);
                intent.putExtra("SEARCH_RESULT", "searched");
                startActivity(intent);

                Log.d(TAG, "onSuggestionClicked()");

                mLastQuery = searchSuggestion.getBody();
            }

            @Override
            public void onSearchAction(String query) {

                DataHelper helper = new DataHelper();
                mLastQuery = query;

                helper.findColors(MainSearchActivity.this, query,
                        new DataHelper.OnFindColorsListener() {

                            @Override
                            public void onResults(List<ColorWrapper> results) {
                                mSearchResultsAdapter.swapData(results);
                            }

                        });
                Log.d(TAG, "onSearchAction()");
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

                //show suggestions when search bar gains focus (typically history suggestions)
                mSearchView.swapSuggestions(DataHelper.getHistory(MainSearchActivity.this, 3));

                Log.d(TAG, "onFocus()");
            }

            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
                mSearchView.setSearchBarTitle(mLastQuery);

                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns
                //mSearchView.setSearchText(searchSuggestion.getBody());

                Log.d(TAG, "onFocusCleared()");
            }
        });



        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {
                ColorSuggestion colorSuggestion = (ColorSuggestion) item;

                String textColor = mIsDarkSearchTheme ? "#ffffff" : "#000000";
                String textLight = mIsDarkSearchTheme ? "#bfbfbf" : "#787878";

                if (colorSuggestion.getIsHistory()) {
                    leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_history_black_24dp, null));

                    Util.setIconColor(leftIcon, Color.parseColor(textColor));
                    leftIcon.setAlpha(.36f);
                } else {
                    leftIcon.setAlpha(0.0f);
                    leftIcon.setImageDrawable(null);
                }

                textView.setTextColor(Color.parseColor(textColor));
                String text = colorSuggestion.getBody()
                        .replaceFirst(mSearchView.getQuery(),
                                "<font color=\"" + textLight + "\">" + mSearchView.getQuery() + "</font>");
                textView.setText(Html.fromHtml(text));
            }

        });

    }

    */

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        //if mSearchView.setSearchFocused(false) causes the focused search
        //to close, then we don't want to close the activity. if mSearchView.setSearchFocused(false)
        //returns false, we know that the search was already closed so the call didn't change the focus
        //state and it makes sense to call supper onBackPressed() and close the activity
       /* if (!mSearchView.setSearchFocused(false)) {
            super.onBackPressed();
        } */
    }

}