package edu.freshfutures.seeka.Models;

/**
 * Created by tokmang on 9/2/2016.
 */
public class PurchaseHistoryModel {

    String purchaseType;
    String PaymentMethod;
    int balancePoints;
    int purchasedPoints;

    public PurchaseHistoryModel () {
        super();
    }

    public void setPurchaseType(String purType) {
        this.purchaseType = purType;
    }

    public void setPaymentMethod(String pyMethod) {
        this.PaymentMethod = pyMethod;
    }

    public void setBalncePoints(int balPoints) {
        this.balancePoints = balPoints;
    }

    public void setPurchasedPoints(int purPoints) {
        this.purchasedPoints = purPoints;
    }

    public String getPurchaseType() {
        return this.purchaseType;
    }

    public String getPaymentMethod() {
        return this.PaymentMethod;
    }

    public int getBalancePoints() {
        return this.balancePoints;
    }

    public int getPurchasedPoints() {
        return this.purchasedPoints;
    }

}
