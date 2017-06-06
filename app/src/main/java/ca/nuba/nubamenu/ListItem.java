package ca.nuba.nubamenu;

import android.database.Cursor;


public class ListItem {
    private String name, menuType, description, picPath, iconPath;
    private double price;
    private Boolean vegetarian;
    private Boolean vegan;
    private int id;

    public int getWebId() {
        return webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }

    private int webId;

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Boolean getVegan() {
        return vegan;
    }

    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    public Boolean getGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(Boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    private Boolean glutenFree;



    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public static ListItem fromCursor(Cursor cursor) {
        ListItem listItem = new ListItem();
        listItem.setId(cursor.getInt(Utility.COL_NUBA_MENU_ID));
        listItem.setName(cursor.getString(Utility.COL_NUBA_MENU_NAME));
        listItem.setPrice(cursor.getDouble(Utility.COL_NUBA_MENU_PRICE));
        listItem.setVegetarian(Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGETARIAN)));
        listItem.setVegan(Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGAN)));
        listItem.setGlutenFree(Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_GLUTEN_FREE)));
        listItem.setDescription(cursor.getString(Utility.COL_NUBA_MENU_DESCRIPTION));
        listItem.setPicPath(cursor.getString(Utility.COL_NUBA_MENU_PIC_PATH));
        listItem.setIconPath(cursor.getString(Utility.COL_NUBA_MENU_ICON_PATH));
        listItem.setWebId(cursor.getInt(Utility.COL_NUBA_WEB_ID));


        return listItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
