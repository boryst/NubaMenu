package ca.nuba.nubamenu;

import android.database.Cursor;

/**
 * Created by Borys on 2017-03-05.
 */

public class MyListItem{
    private String name;
    private String menuType;
    private String price;
    private Boolean vegetarian;

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
        myListItem.setName(cursor.getString(Utility.COL_NUBA_MENU_NAME));
        return myListItem;
    }
}
