package edu.freshfutures.seeka.Models;

/**
 * Created by tokmang on 9/2/2016.
 */
public class TransactionHistoryModel {

    String purchaseType;
    String uniName;
    String courseName;
    int transValue;

    public TransactionHistoryModel () {
        super();
    }

    public void setPurchaseType(String purType) {
        this.purchaseType = purType;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTransactionValue(int transValue) {
        this.transValue = transValue;
    }

    public String getPurchaseType() {
        return this.purchaseType;
    }

    public String getUniName() {
        return this.uniName;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public int getTransactionValue() {
        return this.transValue;
    }
}
