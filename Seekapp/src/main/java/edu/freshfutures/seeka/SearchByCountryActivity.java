package edu.freshfutures.seeka;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.arlib.floatingsearchview.FloatingSearchView;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Collections;
import edu.freshfutures.seeka.Models.SearchTypeModel;
import edu.freshfutures.seeka.adapters.SearchAdapter;
import edu.freshfutures.seeka.adapters.SearchCoursesAdapter;
import edu.freshfutures.seeka.adapters.SearchResultsListAdapter;


public class SearchByCountryActivity extends AppCompatActivity {

    ImageButton btnBack;
    public String[] allCourses;
    ArrayList<String> coursesList = new ArrayList<>();

    public String[] allCourseCode;
    ArrayList<String> coursesCode = new ArrayList<>();

    ListView lisCoures;
    String ctry;

    Button btnSave;

    private FloatingSearchView mSearchView;
    private final String TAG = "MainSearchActivity";
    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;
    private SearchResultsListAdapter mSearchResultsAdapter;
    private boolean mIsDarkSearchTheme = false;

    private String mLastQuery = "";

    private SearchTypeModel model;
    private static ArrayList<SearchTypeModel> arrDestinationModel;
    SearchCoursesAdapter adapter;

    JSONArray arrDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_country);

        Toolbar parent = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(parent);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        lisCoures = (ListView) findViewById(R.id.lisSearchCourses);
        btnBack = (ImageButton) parent.findViewById(R.id.btnDialogBack);

        btnSave = (Button) findViewById(R.id.btnSearchCountry);

       // btnShowAll = (Button) findViewById(R.id.btnShowAllCourses);
      //  searchInput = (ImageButton) findViewById(R.id.btnSearchCourses);

       // txtSearchBox = (EditText) findViewById(R.id.editTxtSearchCourse);

        //mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);

       // setupFloatingSearch();

        arrDestination = new JSONArray();

        allCourses = getResources().getStringArray(R.array.search_country_name_array);
        Collections.addAll(coursesList, allCourses);

        allCourseCode = getResources().getStringArray(R.array.srch_result_country_code_array);
        Collections.addAll(coursesCode, allCourseCode);

        adapter = new SearchCoursesAdapter(SearchByCountryActivity.this, coursesList, coursesCode);

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

                String destination = adapter.arrModel;
                String destinationCountry = adapter.ctryStringModel;

                SearchAdapter.setSearchDestination(destinationCountry);

                SharedPreferences destinationSelectedPref = getSharedPreferences("PREFDESTSELECTED", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorDest = destinationSelectedPref.edit();
                editorDest.putString("DESTINATION_SELECTED", destination);
                editorDest.apply();

                onBackPressed();

            }
        });

    /*    searchInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchByCountryActivity.this, HomeActivity.class);
                intent.putExtra("SEARCH_RESULT", "searched");
                startActivity(intent);
            }
        });

        txtSearchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(SearchByCountryActivity.this, HomeActivity.class);
                    intent.putExtra("SEARCH_RESULT", "searched");
                    startActivity(intent);

                    return true;
                }

                return false;
            }
        });

*/
    }


  /*  private void setupFloatingSearch() {
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
                    DataHelper.findSuggestions(SearchByCountryActivity.this, newQuery, 5, FIND_SUGGESTION_SIMULATED_DELAY, new DataHelper.OnFindSuggestionsListener() {

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

                Intent intent = new Intent(SearchByCountryActivity.this, HomeActivity.class);
                intent.putExtra("SEARCH_RESULT", "searched");
                startActivity(intent);

                Log.d(TAG, "onSuggestionClicked()");

                mLastQuery = searchSuggestion.getBody();

                ColorSuggestion colorSuggestion = (ColorSuggestion) searchSuggestion;

                DataHelper helper = new DataHelper();
                helper.findColors(SearchByCountryActivity.this, colorSuggestion.getBody(),
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

                helper.findColors(SearchByCountryActivity.this, query,
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
                mSearchView.swapSuggestions(DataHelper.getHistory(SearchByCountryActivity.this, 3));

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
     /*   if (!mSearchView.setSearchFocused(false)) {

        } */
    }

}
