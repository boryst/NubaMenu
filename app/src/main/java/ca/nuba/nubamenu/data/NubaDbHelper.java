package ca.nuba.nubamenu.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ca.nuba.nubamenu.data.NubaContract.NubaMenuEntry;



public class NubaDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "nuba.db";
    private static final int DATABASE_VERSION = 1;


    NubaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_NUBA_MENU_TABLE = "CREATE TABLE " + NubaMenuEntry.TABLE_NAME + " ("+
                NubaMenuEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                NubaMenuEntry.COLUMN_MENU_TYPE+" TEXT NOT NULL, "+
                NubaMenuEntry.COLUMN_NAME+" TEXT NOT NULL, "+
                NubaMenuEntry.COLUMN_PRICE+" REAL NOT NULL, "+
                NubaMenuEntry.COLUMN_VEGETARIAN+" BOOLEAN NOT NULL, "+
                NubaMenuEntry.COLUMN_VEGAN+" BOOLEAN NOT NULL, "+
                NubaMenuEntry.COLUMN_GLUTEN_FREE+" BOOLEAN NOT NULL, "+
                NubaMenuEntry.COLUMN_DESCRIPTION+" TEXT NOT NULL, "+
                NubaMenuEntry.COLUMN_PIC_PATH+" TEXT NOT NULL, "+
                NubaMenuEntry.COLUMN_ICON_PATH+" TEXT NOT NULL, "+
                NubaMenuEntry.COLUMN_WEB_ID+" INT NOT NULL, "+
                NubaMenuEntry.COLUMN_LOCATION+" TEXT NOT NULL, "+
                NubaMenuEntry.COLUMN_MODIFIER+" TEXT NOT NULL, "+
                NubaMenuEntry.COLUMN_START_DATE+" TEXT NOT NULL, "+
                NubaMenuEntry.COLUMN_END_DATE+" TEXT NOT NULL"+

                ");";

        db.execSQL(SQL_CREATE_NUBA_MENU_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
