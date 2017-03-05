package ca.nuba.nubamenu.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;



public class NubaContract {

     public static final String CONTENT_AUTHORITY = "ca.nuba.nubamenu";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);

    public static final String PATH_NUBA_MENU = "nuba_menu";

     public static final class NubaMenuEntry implements BaseColumns {
         public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_NUBA_MENU).build();

         public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +"/"+CONTENT_AUTHORITY+"/"+PATH_NUBA_MENU;
         public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_NUBA_MENU;

         public static final String TABLE_NAME = "nuba_menu";
         public static final String COLUMN_MENU_TYPE = "menu_type";
         public static final String COLUMN_NAME = "name";
         public static final String COLUMN_PRICE = "price";
         public static final String COLUMN_VEGETARIAN = "vegetarian";
         public static final String COLUMN_VEGAN = "vegan";
         public static final String COLUMN_GLUTEN_FREE = "gluten_free";
         public static final String COLUMN_DESCRIPTION = "description";
         public static final String COLUMN_PIC_PATH = "pic_path";
         public static final String COLUMN_ICON_PATH = "icon_path";

         public static Uri buildNubaMenuUriWithID(long id){
            /**  content://cca.nuba.nubamenu/nuba_menu/id  */
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildNubaMenuUri(){
            /**  content://ca.nuba.nubamenu/nuba_menu  */
            return CONTENT_URI;
        }

        static String getIDFromURI(Uri uri){
            return String.valueOf(uri.getPathSegments().get(1));
        }
    }
}
