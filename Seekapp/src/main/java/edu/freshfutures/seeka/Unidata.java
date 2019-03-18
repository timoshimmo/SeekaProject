package edu.freshfutures.seeka;

/**
 * Created by tokmang on 7/13/2016.
 */
public class Unidata {

    private String university_name;
    private String course_name;
    private int university_logo;


    public Unidata(String un, String cn, int ul) {
        university_name = un;
        course_name = cn;
        university_logo = ul;

    }

    public void setUniversityName(String name) {

        this.university_name = name;
    }

    public void setCourseName(String cName) {

        this.course_name = cName;
    }

    public void setUniversityLogo(int uLogo) {

        this.university_logo = uLogo;
    }

    public String getUniName() {
        return this.university_name;
    }

    public String getCourseName() {
        return this.course_name;
    }

    public int getUniLogo() {
        return this.university_logo;
    }

}
