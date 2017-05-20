package ca.nuba.nubamenu;

import android.database.Cursor;


public class MyListItem{
    private String name, menuType, description, picPath, iconPath;
    private double price;
    private Boolean vegetarian;
    private Boolean vegan;
    private int id;

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

    public static MyListItem fromCursor(Cursor cursor) {
        //TODO return your MyListItem from cursor.
//        Log.v("MyListItem", cursor.getString(Utility.COL_NUBA_MENU_NAME) +
//        " - "+cursor.getString(Utility.COL_NUBA_MENU_MENU_TYPE) +
//                " - "+cursor.getString(Utility.COL_NUBA_MENU_DESCRIPTION)
//        );


        MyListItem myListItem = new MyListItem();
        myListItem.setId(cursor.getInt(Utility.COL_NUBA_MENU_ID));
        myListItem.setName(cursor.getString(Utility.COL_NUBA_MENU_NAME));
        myListItem.setPrice(cursor.getDouble(Utility.COL_NUBA_MENU_PRICE));
        myListItem.setVegetarian(Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGETARIAN)));
        //Log.v("MyListItem","cursor.getString(Utility.COL_NUBA_MENU_VEGETARIAN) - "+cursor.getString(Utility.COL_NUBA_MENU_VEGETARIAN));
        //Log.v("MyListItem","Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGETARIAN)) - "+String.valueOf(Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGETARIAN))));
        myListItem.setVegan(Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGAN)));
        myListItem.setGlutenFree(Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_GLUTEN_FREE)));
        myListItem.setDescription(cursor.getString(Utility.COL_NUBA_MENU_DESCRIPTION));
        myListItem.setPicPath(cursor.getString(Utility.COL_NUBA_MENU_PIC_PATH));
        myListItem.setIconPath(cursor.getString(Utility.COL_NUBA_MENU_ICON_PATH));


        return myListItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
