package edu.freshfutures.seeka.Models;

/**
 * Created by tokmang on 10/11/2016.
 */

public class SearchTypeModel {

    private String level;
    private String levelFull;
    private String course;

    public SearchTypeModel() {
        super();
    }

    public String getSearchLevel() {
        return level;
    }

    public void setSearchLevel(String sLevel) {
        this.level = sLevel;
    }

    public String getSearchLevelFull() {
        return levelFull;
    }

    public void setSearchLevelFull(String sLvlFull) {
        this.levelFull = sLvlFull;
    }

    public String getSearchCourse() {
        return course;
    }

    public void setSearchCourse(String sCourse) {
        this.course = sCourse;
    }
}
