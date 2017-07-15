package ca.nuba.nubamenu;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ca.nuba.nubamenu.data.NubaContract;
import ca.nuba.nubamenu.data.NubaContract.NubaMenuEntry;
import ca.nuba.nubamenu.data.NubaDbHelper;


/**
 * Class with a bunch of widely used variables, URIs for menu filter and helper functions
 */

public class Utility {

    public static final String LOG_TAG = Utility.class.getSimpleName();
    public static Target mTarget;
    public static final String TYPE_EXTRA = "TYPE_EXTRA";
    public static final String TYPE_LUNCH = "Lunch";
    public static final String TYPE_BRUNCH = "Brunch";
    public static final String TYPE_DINNER = "Dinner";

    public static final String LOCATION_EXTRA = "LOCATION_EXTRA";
    public static final String LOCATION_GASTOWN = "Gastown";
    public static final String LOCATION_KITSILANO = "Kitsilano";
    public static final String LOCATION_MOUNT = "Mount Pleasant";
    public static final String LOCATION_YALETOWN = "Yaletown";

    public static final String NUBA_PREFS = "NUBA_PREFS";

    public static final String POSITION_EXTRA = "POSITION";
    public static final String TAB_NUMBER_EXTRA = "TAB_NUMBER_EXTRA";
    public static final String ITEM_ID_EXTRA = "ITEM_ID_EXTRA";
    public static final String ITEM_WEB_ID_EXTRA = "ITEM_WEB_ID_EXTRA";

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_PAGE_NUMBER = "ARG_PAGE_NUMBER";

    public static final String WEB_IMAGE_STORAGE = "http://boryst.com/nuba_img/";

    public static final String FILTER_VEGETARIAN = "FILTER_VEGETARIAN";
    public static final String FILTER_VEGAN = "FILTER_VEGAN";
    public static final String FILTER_GLUTEN_FREE = "FILTER_GLUTEN_FREE";
    public static final String FILTER_MEAT = "FILTER_MEAT";

    public static final String sNubaMenuWithLike =
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+ " LIKE ?";

    public static final  String sNubaMezzesWithLike =
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+ " LIKE ? OR "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+" LIKE ? OR "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+" LIKE ?";

    public static final  String sNubaMenuWithFilter =
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+ " LIKE ? AND " +
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_VEGETARIAN + " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_VEGAN +" LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_GLUTEN_FREE +" LIKE ?";
    /** Filters */
    public static final  String sNubaMenuWithGfFilter =
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+ " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_GLUTEN_FREE + " LIKE ?";

    public static final  String sNubaMenuWithVFilter =
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+ " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_VEGETARIAN + " LIKE ?";

    public static final  String sNubaMenuWithVeFilter =
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+ " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_VEGAN + " LIKE ?";

    public static final  String sNubaMenuWithVeGfFilter =
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+ " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_VEGAN + " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_GLUTEN_FREE + " LIKE ?";

    public static final  String sNubaMenuWithVGfFilter =
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+ " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_VEGETARIAN + " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_GLUTEN_FREE + " LIKE ?";

    public static final  String sNubaMenuWithVVeFilter =
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+ " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_VEGETARIAN + " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_VEGAN + " LIKE ?";

    public static final  String sNubaMenuWithVVeGfFilter =
            NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE+ " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_VEGETARIAN + " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_VEGAN + " LIKE ? AND "+
                    NubaContract.NubaMenuEntry.TABLE_NAME+"."+ NubaContract.NubaMenuEntry.COLUMN_GLUTEN_FREE + " LIKE ?";

    public static final  String sNubaMenuUpdateWithWebID =
            NubaMenuEntry.TABLE_NAME+ "." + NubaMenuEntry.COLUMN_WEB_ID + " = ? ";


    public static final String[] NUBA_MENU_PROJECTION = {
            NubaContract.NubaMenuEntry.TABLE_NAME + "." + NubaContract.NubaMenuEntry._ID,
            NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE,
            NubaContract.NubaMenuEntry.COLUMN_NAME,
            NubaContract.NubaMenuEntry.COLUMN_PRICE,
            NubaContract.NubaMenuEntry.COLUMN_VEGETARIAN,
            NubaContract.NubaMenuEntry.COLUMN_VEGAN,
            NubaContract.NubaMenuEntry.COLUMN_GLUTEN_FREE,
            NubaContract.NubaMenuEntry.COLUMN_DESCRIPTION,
            NubaContract.NubaMenuEntry.COLUMN_PIC_PATH,
            NubaContract.NubaMenuEntry.COLUMN_ICON_PATH,
            NubaContract.NubaMenuEntry.COLUMN_WEB_ID,
            NubaMenuEntry.COLUMN_LOCATION
    };

    public static final int COL_NUBA_MENU_ID = 0;
    public static final int COL_NUBA_MENU_MENU_TYPE = 1;
    public static final int COL_NUBA_MENU_NAME = 2;
    public static final int COL_NUBA_MENU_PRICE = 3;
    public static final int COL_NUBA_MENU_VEGETARIAN = 4;
    public static final int COL_NUBA_MENU_VEGAN = 5;
    public static final int COL_NUBA_MENU_GLUTEN_FREE = 6;
    public static final int COL_NUBA_MENU_DESCRIPTION = 7;
    public static final int COL_NUBA_MENU_PIC_PATH = 8;
    public static final int COL_NUBA_MENU_ICON_PATH = 9;
    public static final int COL_NUBA_WEB_ID = 10;
    public static final int COL_NUBA_LOCATION = 11;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    /**
     * Add ripple effect as second layer to ImageButtons
     * @param button button to which ripple effect will be added
     * @param picture drawable resource that will be used as src
     * @param context context
     */
    public static void imageButtonPressEffect(ImageButton button, int picture, Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable firstLayer = ContextCompat.getDrawable(context, picture);

            GradientDrawable secondLayer = new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR,
                    new int[]{
                            ContextCompat.getColor(context, R.color.gradientAccent),
                            ContextCompat.getColor(context, R.color.gradientAccent),
                            ContextCompat.getColor(context, R.color.gradientPrimary)
                    }
            );

            Drawable[] layers = new Drawable[]{firstLayer, secondLayer};

            LayerDrawable ld = new LayerDrawable(layers);
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[] {android.R.attr.state_pressed}, ld);
            states.addState(new int[] {android.R.attr.state_focused}, ld);
            states.addState(new int[]{}, firstLayer);

            button.setImageDrawable(states);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList csl = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary));
            RippleDrawable d = new RippleDrawable(csl, ContextCompat.getDrawable(context, picture), null);
            button.setImageDrawable(d);
        }
    }

    /**
     * Format cursor menu type to string used for tabs names
     * @param menuType menuType from cursor
     * @return returns formatted string of menu type
     */
    public static String formatMenuType(String menuType){
        switch (menuType){
            case "lunchMezze": return "Mezze";
            case "lunchPlates": return "Plates";
            case "lunchPitas": return "Pitas";
            case "lunchSalads": return "Salads";
            case "lunchSoups": return "Soups";
            case "lunchToShare": return "To Share";
            case "lunchBeverages": return "Beverages";
            case "dinnerColdMezze": return "Cold Mezze";
            case "dinnerHotMezze": return "Hot Mezze";
            case "dinnerToShare": return "To Share";
            case "dinnerSoupsSalads": return "Soups & Salads";
            case "dinnerMains": return "Mains";
            case "brunchAll": return "Mains";
            case "brunchBevs": return "Beverages";
            default: return "Something Wrong";
        }
    }

    /**
     * Drops database for testing purposes
     * @param context context
     */
    public static void dropDB(Context context){
        context.deleteDatabase(NubaDbHelper.DATABASE_NAME);
    }

/*    public static void imageDownload(Context context, String fromUrl, String toUrl){
        //Log.v(LOG_TAG, "URL - "+fromUrl);
        //Log.v(LOG_TAG, "URL - "+toUrl);
        Picasso.with(context).load(fromUrl).into(getTarget(toUrl, context));
    }


    private static Target getTarget(final String url, final Context context){

        Target target = new Target() {
        //mTarget = new Target() {

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.v(LOG_TAG, "onBitmapLoaded called");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(context.getFilesDir(), url);
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.v(LOG_TAG, "onBitmapFailed called");


            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.v(LOG_TAG, "onPrepareLoad called");

            }
        };

        return target;
        //return mTarget;
    }*/
    /**
     * Download images
     * @param context context
     * @param fromUrl web URL from where image will be downloaded
     * @param toUrl path to where image will be save on device
     */
    public static void imageDownload(final Context context, final String fromUrl, final String toUrl){

        Log.v("Utility", "imageDownload called");

    final Target target = new Target() {

        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            Log.v("Utility", "onBitmapLoaded called for "+fromUrl);

            new Thread(new Runnable() {

                int porgress = 0;
                @Override
                public void run() {
                    File file = new File(context.getFilesDir(), toUrl);
                    try {
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                        ostream.flush();
                        ostream.close();
                    } catch (IOException e) {
                        Log.e("IOException", e.getLocalizedMessage());
                    }


//                    handler.post(new Runnable() {
//                        public void run() {
//                            progressBar.setProgress(porgress);
//                            i++;
//                        }
//                    });

//                    ObjectAnimator animation = ObjectAnimator.ofInt (progressBar, "progress", 0, 500); // see this max value coming back here, we animale towards that value
//                    animation.setDuration (5000); //in milliseconds
//                    animation.setInterpolator (new DecelerateInterpolator());
//                    animation.start ();


                }
            }).start();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Log.v("Utility", "onBitmapFailed called");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };
    Log.v(LOG_TAG, "fromUrl - "+fromUrl+", target - "+String.valueOf(target)+", toUrl - "+toUrl);
    Picasso.with(context).load(fromUrl).into(target);
}

    /*private static Target getTarget(final String url, final Context context){
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.v("Utility", "onBitmapLoaded called");

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        File file = new File(context.getFilesDir(), url);
                        Log.v("Utility","URL - "+url);
                        try {
                            Log.v("Utility", "run called");

                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);

                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.v("Utility", "onBitmapFailed called");


            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }*/

    
    /**
     * Verifying external storage permissions
     * @param activity activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }

    }

    /**
     * Cuts nuba_img/ from image name
     * @param fileName image file name
     * @return image file name without nuba_img/
     */
    public static String imageNameCutter(String fileName){
        return fileName.replace("nuba_img/","");
    }

    public static String imageExtentionCutter(String fileName){
        return imageNameCutter(fileName).replace(".png","");
    }

    public static void slideInTransition(FragmentActivity activity){
        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public static void slideOutTransition(AppCompatActivity activity){
        activity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


}
