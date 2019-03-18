package edu.freshfutures.seeka;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import edu.freshfutures.seeka.Models.SearchModel;
import edu.freshfutures.seeka.adapters.SearchAdapter;
import edu.freshfutures.seeka.databases.EduQualificationDB;
import edu.freshfutures.seeka.util.ListDividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearch extends Fragment implements Animation.AnimationListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_TITLE = "section_title";

    protected ArrayList<SearchModel> countryList;
    protected SearchModel searchModel;

    private String title;

    private EduQualificationDB databaseHelper;

    ArrayList<HashMap<String, ArrayList<String>>> userEduList;
    ArrayList<String> mainData;

    Context ctx;

    public FragmentSearch() {
        // Required empty public constructor
    }

    public static FragmentSearch newInstance(int page, String title) {
        FragmentSearch fragment = new FragmentSearch();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, page);
        args.putString(ARG_SECTION_TITLE, title);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(ARG_SECTION_TITLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lisSearchCourseList);


        databaseHelper = new EduQualificationDB(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);


        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.course_search_divider);

        RecyclerView.ItemDecoration itemDecoration = new
                ListDividerItemDecoration(dividerDrawable);

       // recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initDataset();

        SearchAdapter sAdpt = new SearchAdapter(getActivity(), countryList);
        recyclerView.setAdapter(sAdpt);

        return view;

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

    private void initDataset() {

        countryList = new ArrayList<>();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("PREFQUAEDULEVEL", Context.MODE_PRIVATE);
        String eduLevel = sharedPref.getString("quaEduLevel", "");

        userEduList = new ArrayList<>();
        mainData = new ArrayList<>();

        int[] CountryBackground = new int[] {0,R.mipmap.search_australia, R.mipmap.search_austria, R.mipmap.search_brazil,
                R.mipmap.search_canada, R.mipmap.search_china, R.mipmap.search_czech_republic, R.mipmap.search_denmark, R.mipmap.search_egypt,
                R.mipmap.search_finland, R.mipmap.search_france, R.mipmap.search_germany, R.mipmap.search_iceland,
                R.mipmap.search_india, R.mipmap.search_ireland, R.mipmap.search_italy, R.mipmap.search_japan,
                R.mipmap.search_malaysia, R.mipmap.search_mexico, R.mipmap.search_netherlands,
                R.mipmap.search_new_zealand, R.mipmap.search_poland, R.mipmap.search_russia,
                R.mipmap.search_singapore, R.mipmap.search_slovenia, R.mipmap.search_south_africa,
                R.mipmap.search_spain, R.mipmap.search_sweden, R.mipmap.search_switzerland,
                R.mipmap.search_u_a_e, R.mipmap.search_u_s_a, R.mipmap.search_united_kingdom};

        String[] CountryLists = getResources().getStringArray(R.array.srch_country_name_array);
        String[] CountryCode = getResources().getStringArray(R.array.srch_country_code_array);
        String[] CountryAvailable = getResources().getStringArray(R.array.srch_country_available_array);
        //String[] CountryCode = getResources().getStringArray(R.array.search_country_code_array);

        int[] MonumentListImg = new int[] {0,R.mipmap.australia_search_icon, R.mipmap.austria_search_icon, R.mipmap.brazil_search_icon,
                R.mipmap.canada_search_icon, R.mipmap.china_search_icon, R.mipmap.czech_search_icon, R.mipmap.denmark_search_icon,
                R.mipmap.egypt_search_icon, R.mipmap.finland_search_icon,
                R.mipmap.france_search_icon, R.mipmap.germany_search_icon, R.mipmap.iceland_search_icon, R.mipmap.india_search_icon,
                R.mipmap.ireland_search_icon, R.mipmap.italy_search_icon, R.mipmap.japan_search_icon, R.mipmap.malaysia_search_icon,
                R.mipmap.mexico_search_icon, R.mipmap.netherlands_search_icon, R.mipmap.newzealand_search_icon,
                R.mipmap.poland_search_icon, R.mipmap.russia_search_icon, R.mipmap.singapore_search_icon,
                R.mipmap.slovenia_search_icon, R.mipmap.southafrica_search_icon, R.mipmap.spain_search_icon, R.mipmap.sweden_search_icon,
                R.mipmap.switzerland_search_icon, R.mipmap.uae_search_icon,
                R.mipmap.usa_search_icon, R.mipmap.uk_search_icon};


            for (int i = 0; i < CountryBackground.length; i++) {

                searchModel = new SearchModel();

                if(i == 0) {
                    searchModel.setSearchType(0);
                }

                else {

                    searchModel.setSearchCtryImage(CountryBackground[i]);
                    searchModel.setCtryName(CountryLists[i]);
                    searchModel.setMonumentIcon(MonumentListImg[i]);
                    searchModel.setAvailableValue(CountryAvailable[i]);
                    searchModel.setCtryCode(CountryCode[i]);

                    searchModel.setSearchType(1);

                    userEduList = databaseHelper.getAllEligibility(CountryCode[i]);
                    mainData = userEduList.get(0).get(CountryCode[i]);

                    if(mainData != null) {

                        for(int j=0; j < mainData.size(); j++) {

                            if(mainData.get(0) != null) {

                                String isEligible = mainData.get(0);
                                String message = mainData.get(1);

                                if(isEligible.equals("1")) {
                                    searchModel.setInfoImg(R.mipmap.search_info_icon);
                                    searchModel.setInfoMessage(message);
                                }
                                else {
                                    searchModel.setInfoImg(R.mipmap.search_info_red_icon);
                                }

                            }
                            else {

                                searchModel.setInfoImg(R.mipmap.search_info_red_icon);
                                searchModel.setInfoMessage("Unfortunetly we haven't collected your countries Grading Requirements , please convert your grades into GPA or A/O-Levels or IB.");

                            }
                        }
                    }
                }

                countryList.add(searchModel);
            }

    }
}