package edu.freshfutures.seeka.Models;

/**
 * Created by tokmang on 7/27/2016.
 */
public class SearchModel {

    private int SearchCtryImage;
    private String ctryName;
    private int monumentsIcon;
    private String availableValue;
    private String acceptedValue;
    private int infoIcon;

    private String ctryCode;
    private String infoMessage;
    private int searchType;


    public SearchModel() {
        super();
    }

    public int getSearchCtryImage() {
        return SearchCtryImage;
    }

    public String getCtryName() {
        return ctryName;
    }

    public int getMonumentIcon() {
        return monumentsIcon;
    }

    public String getAvailableValue() {
        return availableValue;
    }

    public String getAcceptedValue() {
        return acceptedValue;
    }

    public String getCtryCode() {
        return ctryCode;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public int getInfoImg() {
        return infoIcon;
    }

    public int getSearchType() {
        return searchType;
    }


    public void setSearchCtryImage(int ctryImage) {
        this.SearchCtryImage = ctryImage;
    }

    public void setCtryName(String ctryName) {
        this.ctryName = ctryName;
    }

    public void setMonumentIcon(int monuIcon) {
        this.monumentsIcon = monuIcon;
    }

    public void setAvailableValue(String available) {
        this.availableValue = available;
    }

    public void setAcceptedValue(String accepted) {
        this.acceptedValue = accepted;
    }

    public void setInfoImg(int infoIcon) {
        this.infoIcon = infoIcon;
    }

    public void setCtryCode(String ctryCode) {
        this.ctryCode = ctryCode;
    }

    public void setInfoMessage(String infoMsg) {
        this.infoMessage = infoMsg;
    }

    public void setSearchType(int sType) {
        this.searchType = sType;
    }

}
