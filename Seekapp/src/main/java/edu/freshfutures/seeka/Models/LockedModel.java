package edu.freshfutures.seeka.Models;

import com.google.api.services.youtube.YouTube;

/**
 * Created by tokmang on 7/25/2016.
 */
public class LockedModel {

    String institution;
    String worldRank;
    String matchValue;
    String recognitionValue;
    String recognitionType;
    String costSavingValue;
    String costSavingCurrency;
    int ctryImage;
    String country;
    String durationTime;
    String durationType;
    String costRange;

    String courseTitle;
    int sponsorOption;

    int adType;
    String adLink;
    String sponsName;

    String sponsURL;
    String remarks;

    int courseId;


    public LockedModel() {
        super();
    }

    public String getRecognitionType() {
        return recognitionType;
    }

    public String getCountryName() {
        return country;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public String getDurationType() {
        return durationType;
    }

    public String getCostRange() {
        return costRange;
    }

    public int getSponsOption() {
        return sponsorOption;
    }

    public String getRemarks() {
        return remarks;
    }


    public String getInstitution() {
        return institution;
    }

    public String getRankValue() {
        return worldRank;
    }

    public String getMatchValue() {
        return matchValue;
    }

    public String getRecgnitionValue() {
        return recognitionValue;
    }

    public String getCostSavingCurrency() {
        return costSavingCurrency;
    }

    public String getCostSavingValue() {
        return costSavingValue;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCountryImage() {
        return ctryImage;
    }

    public String getAdLink() {
        return adLink;
    }

    public String getSponsName() {
        return sponsName;
    }

    public String getSponsLogoUrl() {
        return sponsURL;
    }

    public int getAdType() {
        return adType;
    }

    public int getCourseID() {
        return courseId;
    }


    public void setInstitution(String in) {
        this.institution = in;
    }

    public void setRankValue(String rv) {
        this.worldRank = rv;
    }

    public void setMatchValue(String mv) {
        this.matchValue = mv;
    }

    public void setRecgnitionValue(String rcv) {
        this.recognitionValue = rcv;
    }

    public void setCostSavingValue(String csv) {
        this.costSavingValue = csv;
    }

    public void setCostSavingCurrency(String csc) {
        this.costSavingCurrency = csc;
    }

    public void setCountryImage(int ctryImg) {
        this.ctryImage = ctryImg;
    }


    public void setRecognitionType(String rType) {
        this.recognitionType = rType;
    }

    public void setCountry(String ctry) {
        this.country = ctry;
    }

    public void setDurationTime(String durTime) {
        this.durationTime = durTime;
    }

    public void setDurationType(String durType) {
        this.durationType = durType;
    }

    public void setCostRange(String cRange) {
        this.costRange = cRange;
    }

    public void setSponsorOption(int sponsOption) {
        this.sponsorOption = sponsOption;
    }

    public void setCourseTitle(String cTitle) {
        this.courseTitle = cTitle;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setAdLink(String adt) {
        this.adLink = adt;
    }

    public void setSponsName(String sn) {
        this.sponsName = sn;
    }

    public void setSponsLogoUrl(String su) {
        this.sponsURL = su;
    }

    public void setAdType(int adty) {
        this.adType = adty;
    }

    public void setCourseID(int cID) {
        this.courseId = cID;
    }


}
