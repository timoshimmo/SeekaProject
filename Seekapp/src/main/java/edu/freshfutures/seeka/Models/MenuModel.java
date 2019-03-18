package edu.freshfutures.seeka.Models;

/**
 * Created by freshfuturesmy on 20/10/16.
 */

public class MenuModel {

    private int rowType;
    private int menuIcon;
    private String menuTitle;
    private String menuNotifications;

    public MenuModel() {
        super();
    }

    public int getRowType() {
        return this.rowType;
    }

    public int getMenuIcon() {
        return this.menuIcon;
    }

    public String getMenuTitle() {
        return this.menuTitle;
    }

    public String getMenuNotifications() {
        return this.menuNotifications;
    }

    public void setRowType(int rwType) {
        this.rowType = rwType;
    }

    public void setMenuIcon(int icons) {
        this.menuIcon = icons;
    }

    public void setMenuTitle(String titles) {
        this.menuTitle = titles;
    }

    public void setMenuNotifications(String notifications) {
        this.menuNotifications = notifications;
    }

}