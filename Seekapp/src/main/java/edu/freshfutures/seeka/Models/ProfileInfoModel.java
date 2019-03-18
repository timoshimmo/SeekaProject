package edu.freshfutures.seeka.Models;

/**
 * Created by tokmang on 8/21/2016.
 */
public class ProfileInfoModel {

    String userEmail;
    String userFName;
    String userLName;
    String userGender;
    String userDOB;
    String userOrigin;
    String userCitizenship;
    String userSkypeId;
    String userPhone;


    public ProfileInfoModel() {
        super();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserFName() {
        return userFName;
    }

    public String getUserLName() {
        return userLName;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public String getUserOrigin() {
        return userOrigin;
    }

    public String getUserCitizenship() {
        return userCitizenship;
    }

    public String getUserSkypeId() {
        return userSkypeId;
    }

    public String getUserPhone() {
        return userPhone;
    }





    public void setUserEmail(String em) {
        this.userEmail = em;
    }

    public void setUserFName(String fn) {
        this.userFName = fn;
    }

    public void setUserLName(String ln) {
        this.userLName = ln;
    }

    public void setUserGender(String gdr) {
        this.userGender = gdr;
    }

    public void setUserDOB(String dob) {
        this.userDOB = dob;
    }

    public void setUserOrigin(String or) {
        this.userOrigin = or;
    }

    public void setUserCitizenship(String cz) {
        this.userCitizenship = cz;
    }

    public void setUserSkypeId(String sid) {
        this.userSkypeId = sid;
    }

    public void setUserPhone(String ph) {
        this.userPhone = ph;
    }
}
