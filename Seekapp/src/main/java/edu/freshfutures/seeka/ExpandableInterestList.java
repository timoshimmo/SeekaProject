package edu.freshfutures.seeka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tokmang on 7/12/2016.
 */
public class ExpandableInterestList {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListInterests = new HashMap<String, List<String>>();


        List<String> arts = new ArrayList<>();
        arts.add("Canvas Painting");
        arts.add("Poster Painting");
        arts.add("Sculpture");
        arts.add("Drama");
        arts.add("Musical");
        arts.add("Musical Instruments");
        arts.add("Sketching");
        arts.add("Design");


        List<String> engineering = new ArrayList<>();
        engineering.add("Mechanical Engineering");
        engineering.add("Chemical Engineering");
        engineering.add("Electrical Engineering");
        engineering.add("Electrical Electronics");
        engineering.add("Aeronautic Engineering");
        engineering.add("Civil Engineering");
        engineering.add("Structural Engineering");


        List<String> law = new ArrayList<>();
        law.add("Family Law");
        law.add("Corporate Law");
        law.add("Criminal Law");
        law.add("Rights Law");
        law.add("Defence Law");



        List<String> medicine = new ArrayList<>();
        medicine.add("General Medicine");
        medicine.add("Optical Medicine");
        medicine.add("Paedatrical Medicine");
        medicine.add("Surgical Medicine");
        medicine.add("Psychiatrist");
        medicine.add("Dental Medicine");
        medicine.add("Anasthetics");

        List<String> sports = new ArrayList<>();
        sports.add("Football");
        sports.add("Basketball");
        sports.add("Badmington");
        sports.add("Handball");
        sports.add("Hockey");
        sports.add("Baseball");
        sports.add("Horse Racing");
        sports.add("F1 Racing");
        sports.add("Tennis");
        sports.add("Gymnastics");


        List<String> infotech = new ArrayList<>();
        infotech.add("Computer Science");
        infotech.add("Network Security");
        infotech.add("Database Administration");
        infotech.add("Information Technology");
        infotech.add("Artificial Intelligence");
        infotech.add("Management Information Systems");
        infotech.add("Mobile Development");

        List<String> business = new ArrayList<>();
        business.add("International Business");
        business.add("SME Businesses");
        business.add("Corporate Business");
        business.add("Processed Goods Business");
        business.add("Raw Materials Business");


        expandableListInterests.put("Arts", arts);
        expandableListInterests.put("Engineering", engineering);
        expandableListInterests.put("Law", law);
        expandableListInterests.put("Medicine", medicine);
        expandableListInterests.put("Sports", sports);
        expandableListInterests.put("Information Technology", infotech);
        expandableListInterests.put("Business", business);

        return expandableListInterests;

    }
}
