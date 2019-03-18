package edu.freshfutures.seeka.Models;

import org.json.JSONArray;

/**
 * Created by tokmang on 8/21/2016.
 */
public class ProfileMainInterestModel {

    JSONArray userHobbies;
    JSONArray userCtryInterest;
    JSONArray userCareerInterest;

    public ProfileMainInterestModel() {
        super();
    }

    public JSONArray getUserHobbies() {
        return userHobbies;
    }

    public JSONArray getUserCtryInterest() {
        return userCtryInterest;
    }

    public JSONArray getUserCareerInterest() {
        return userCareerInterest;
    }


    public void setUserHobbies(JSONArray uh) {
        this.userHobbies = uh;
    }

    public void setUserCtryInterest(JSONArray uc) {
        this.userCtryInterest = uc;
    }

    public void setUserCareerInterest(JSONArray ci) {
        this.userCareerInterest = ci;
    }
}
