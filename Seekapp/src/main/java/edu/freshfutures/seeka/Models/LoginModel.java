package edu.freshfutures.seeka.Models;

/**
 * Created by tokmang on 7/31/2016.
 */
public class LoginModel {

    String statusCode;
    String sessionToken;

    public LoginModel() {
        super();
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }

    public void setStatusCode(String status) {
        this.statusCode = status;
    }

    public void setSessionToken(String session) {
        this.sessionToken = session;
    }
}
