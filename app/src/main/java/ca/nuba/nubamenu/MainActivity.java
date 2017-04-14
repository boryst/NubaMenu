package ca.nuba.nubamenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import java.io.File;

import static ca.nuba.nubamenu.Utility.FILTER_GLUTEN_FREE;
import static ca.nuba.nubamenu.Utility.FILTER_VEGAN;
import static ca.nuba.nubamenu.Utility.FILTER_VEGETARIAN;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Picasso.with(this).setIndicatorsEnabled(true);

        File dirFiles = getFilesDir();
        int i = 1;
        for (String strFile : dirFiles.list()) {
            //Log.v(LOG_TAG, "Direcroty file "+i+" - "+strFile);
            i+=1;

        }
        setTitle(getString(R.string.main_activity_title));

        if (getSupportActionBar() != null){
            getSupportActionBar().setElevation(0f);
        }
        //getSupportActionBar().setIcon(R.drawable.nuba_logo);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Log.v(LOG_TAG, "Query - "+ sNubaMezzesWithLike)
/** Only first pages of each type of menu*/
/*        Cursor mCursor = this.getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                new String[]{NubaContract.NubaMenuEntry.COLUMN_ICON_PATH},
                sNubaMezzesWithLike,
                new String[]{"lunchMezze","dinnerColdMezze","brunchAll"},
                null);*/
/** Only lunchMezze */
/*        Cursor mCursor = this.getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                new String[]{NubaContract.NubaMenuEntry.COLUMN_ICON_PATH},
                Utility.sNubaMenuWithLike,
                new String[]{"lunchMezze"},
                null);*/
/** All menu */
/**        Cursor mCursor = this.getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                new String[]{NubaContract.NubaMenuEntry.COLUMN_ICON_PATH},
                null,
                null,
                null);

        if (mCursor != null) {
            Log.v(LOG_TAG, "Cursor size - "+mCursor.getCount());
            mCursor.moveToPosition(-1);

            while (mCursor.moveToNext()){
                    //Log.v(LOG_TAG, "image "+mCursor.getPosition()+" - "+mCursor.getString(0));
                    File img = new File(this.getFilesDir() + "/" + Utility.imageNameCutter(mCursor.getString(0)));
                    if (!img.exists()) {
                        Log.v(LOG_TAG, "File "+Utility.imageNameCutter(mCursor.getString(0))+" does not exist");
                        Utility.imageDownload(this, "http://boryst.com/" + mCursor.getString(0), Utility.imageNameCutter(mCursor.getString(0)));
                    }
//                else {
//                    img.delete();
//                }

            }

//            mCursor.moveToFirst();
//            File img = new File(this.getFilesDir() + "/" + Utility.imageNameCutter(mCursor.getString(1)));
//            if (!img.exists()) {
//                Log.v(LOG_TAG, "File "+Utility.imageNameCutter(mCursor.getString(1))+"does not exist");
//                Utility.imageDownload(this, "http://boryst.com/" + mCursor.getString(1), Utility.imageNameCutter(mCursor.getString(1)));
//            }

            mCursor.close();
        }*/


        SharedPreferences.Editor editor;
        editor = getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
        editor.putString(FILTER_VEGETARIAN, null);
        editor.putString(FILTER_VEGAN, null);
        editor.putString(FILTER_GLUTEN_FREE, null);
        editor.apply();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:{

            }
            case R.id.drop_db:{
                Utility.dropDB(this);
            }
        }
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utility.verifyStoragePermissions(this);
    }
}
