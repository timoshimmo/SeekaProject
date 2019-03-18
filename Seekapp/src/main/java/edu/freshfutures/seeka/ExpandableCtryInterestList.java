package edu.freshfutures.seeka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tokmang on 8/18/2016.
 */
public class ExpandableCtryInterestList {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListCtryInterests = new HashMap<String, List<String>>();


        List<String> north_america = new ArrayList<>();
        north_america.add("Canada");
        north_america.add("USA");


        List<String> asia = new ArrayList<>();
        asia.add("Bangladesh");
        asia.add("China");
        asia.add("Hong Kong");
        asia.add("India");
        asia.add("Indonesia");
        asia.add("Japan");
        asia.add("Laos");
        asia.add("Malaysia");
        asia.add("Pakistan");
        asia.add("Philipines");
        asia.add("Pakistan");
        asia.add("Singapore");
        asia.add("Thailand");
        asia.add("Taiwan");
        asia.add("Vietnam");

        List<String> europe = new ArrayList<>();
        europe.add("Austria");
        europe.add("Belgium");
        europe.add("Denmark");
        europe.add("France");
        europe.add("Germany");
        europe.add("Italy");
        europe.add("Latvia");
        europe.add("Netherlands");
        europe.add("Russia");
        europe.add("Spain");
        europe.add("UK");


        List<String> africa = new ArrayList<>();
        africa.add("Algeria");
        africa.add("Burkina Faso");
        africa.add("Egypt");
        africa.add("Ghana");
        africa.add("Kenya");
        africa.add("Mali");
        africa.add("Malawi");
        africa.add("Nigeria");
        africa.add("Rwanda");
        africa.add("South Africa");
        africa.add("Tanzania");
        africa.add("Uganda");
        africa.add("Zambia");
        africa.add("Zimbabwe");

        List<String> oceania = new ArrayList<>();
        oceania.add("Australia");
        oceania.add("Fiji");
        oceania.add("New Zealand");

        List<String> south_america = new ArrayList<>();
        south_america.add("Argentina");
        south_america.add("Brazil");
        south_america.add("China");
        south_america.add("Colombia");
        south_america.add("Mexico");
        south_america.add("Venezuela");

        List<String> carribean = new ArrayList<>();
        carribean.add("Jamaica");
        carribean.add("Bahamas");
        carribean.add("Dominican Republic");
        carribean.add("Haiti");


        expandableListCtryInterests.put("Africa", africa);
        expandableListCtryInterests.put("Asia", asia);
        expandableListCtryInterests.put("Carribean", carribean);
        expandableListCtryInterests.put("Europe", europe);
        expandableListCtryInterests.put("North America", north_america);
        expandableListCtryInterests.put("Oceania", oceania);
        expandableListCtryInterests.put("South America", south_america);

        return expandableListCtryInterests;

    }
}
