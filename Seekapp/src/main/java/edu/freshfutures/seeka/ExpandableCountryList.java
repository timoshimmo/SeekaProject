package edu.freshfutures.seeka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tokmang on 4/3/2016.
 */
public class ExpandableCountryList {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> australia = new ArrayList<String>();
        australia.add("Adelaide");
        australia.add("Brisbane");
        australia.add("Melbourne");
        australia.add("Perth");
        australia.add("Sydney");

        List<String> austria = new ArrayList<String>();
        austria.add("Graz");
        austria.add("Slazburg");
        austria.add("Vienna");

        List<String> france = new ArrayList<String>();
        france.add("Bordeux");
        france.add("Lyon");
        france.add("Marseille");
        france.add("Paris");
        france.add("Sochaux");

        List<String> germany = new ArrayList<String>();
        germany.add("Berlin");
        germany.add("Cologne");
        germany.add("Frankfurt");
        germany.add("Hamburg");
        germany.add("Munich");
        germany.add("Stuttgart");

        List<String> malaysia = new ArrayList<String>();
        malaysia.add("Penang");
        malaysia.add("Sarawak");
        malaysia.add("Selangor");

        List<String> newzealand = new ArrayList<String>();
        newzealand.add("Auckland");
        newzealand.add("Wellington");

        List<String> russia = new ArrayList<String>();
        russia.add("Kazan");
        russia.add("Moscow");
        russia.add("St Petersburg");

        List<String> singapore = new ArrayList<String>();
        singapore.add("Jurong");
        singapore.add("Novena");

        List<String> sweden = new ArrayList<String>();
        sweden.add("Malmo");
        sweden.add("Lund");
        sweden.add("Stockholm");

        List<String> switzerland = new ArrayList<String>();
        switzerland.add("Basel");
        switzerland.add("Bern");
        switzerland.add("Davos");
        switzerland.add("Geneva");
        switzerland.add("Lausanne");
        switzerland.add("Montreux");
        switzerland.add("Zurich");

        List<String> unitedkingdom = new ArrayList<String>();
        unitedkingdom.add("Aberdeen");
        unitedkingdom.add("Belfast");
        unitedkingdom.add("Birmingham");
        unitedkingdom.add("Bristol");
        unitedkingdom.add("Cardiff");
        unitedkingdom.add("Liverpool");
        unitedkingdom.add("London");
        unitedkingdom.add("Manchester");
        unitedkingdom.add("Newcastle");
        unitedkingdom.add("Swansea");

        List<String> unitedstates = new ArrayList<String>();
        unitedstates.add("Atlanta");
        unitedstates.add("Boston");
        unitedstates.add("Chicago");
        unitedstates.add("Denver");
        unitedstates.add("Houston");
        unitedstates.add("Los Angeles");
        unitedstates.add("New York");
        unitedstates.add("Philadelphia");
        unitedstates.add("Phoenix");
        unitedstates.add("Pittsburgh");
        unitedstates.add("Worchester");

        expandableListDetail.put("Australia", australia);
        expandableListDetail.put("Austria", austria);
        expandableListDetail.put("France", france);
        expandableListDetail.put("Germany", germany);
        expandableListDetail.put("Malaysia", malaysia);
        expandableListDetail.put("New Zealand", newzealand);
        expandableListDetail.put("Russia", russia);
        expandableListDetail.put("Singapore", singapore);
        expandableListDetail.put("Sweden", sweden);
        expandableListDetail.put("Switzerland", switzerland);
        expandableListDetail.put("United Kingdom", unitedkingdom);
        expandableListDetail.put("United States", unitedstates);

        return expandableListDetail;
    }
}
