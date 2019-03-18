package edu.freshfutures.seeka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tokmang on 5/20/2016.
 */
public class ExpandableAboutCountryList {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableCtryListDetail = new HashMap<>();

        List<String> visa = new ArrayList<String>();
        visa.add("Country Visa Details");
        visa.add("Country Visa Details");

        List<String> costLiving = new ArrayList<String>();
        costLiving.add("Country Cost of Living Details");
        costLiving.add("Country Cost of Living Details");

        List<String> climate = new ArrayList<String>();
        climate.add("Country Climate Details");
        climate.add("Country Climate Details");

        List<String> jobProspect = new ArrayList<String>();
        jobProspect.add("Country Job Prospect / Part-Time Jobs Details");
        jobProspect.add("Country Job Prospect / Part-Time Jobs Details");

        List<String> internationalHealthCover = new ArrayList<String>();
        internationalHealthCover.add("Country Health Cover Details");
        internationalHealthCover.add("Country Health Cover Details");

        List<String> safety = new ArrayList<String>();
        safety.add("Country Safety / Emergency Cover Details");
        safety.add("Country Safety / Emergency Cover Details");

        List<String> media = new ArrayList<String>();
        media.add("Country Media Details");
        media.add("Country Media Details");

        expandableCtryListDetail.put("Visa", visa);
        expandableCtryListDetail.put("Cost of Living", costLiving);
        expandableCtryListDetail.put("Job prospect / Part-time Jobs", jobProspect);
        expandableCtryListDetail.put("International health cover", internationalHealthCover);
        expandableCtryListDetail.put("Safety / Emergency Contact", safety);
        expandableCtryListDetail.put("Media", media);

        return expandableCtryListDetail;
    }
}
