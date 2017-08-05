package ca.nuba.nubamenu;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import timber.log.Timber;

import static ca.nuba.nubamenu.Utility.FILTER_GLUTEN_FREE;
import static ca.nuba.nubamenu.Utility.FILTER_VEGAN;
import static ca.nuba.nubamenu.Utility.FILTER_VEGETARIAN;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private SharedPreferences prefs;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Picasso.with(this).setIndicatorsEnabled(false);
        Timber.plant(new Timber.DebugTree());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0f);
        }
//List of files
        for (String file : getFilesDir().list()){
//            Timber.v("File - "+ file+"\n");
        }

        //        fm = getSupportFragmentManager();

        prefs = getSharedPreferences(NUBA_PREFS, MODE_PRIVATE);

//        MainActivityFragment mainActivityFragment = new MainActivityFragment();
//        fm.beginTransaction()
//                .add(R.id.fragment_container, mainActivityFragment)
//                .addToBackStack("MainFragment")
//                .commit();

        SharedPreferences.Editor editor;
        editor = getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
        editor.putString(FILTER_VEGETARIAN, null);
        editor.putString(FILTER_VEGAN, null);
        editor.putString(FILTER_GLUTEN_FREE, null);
        editor.apply();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

//            getWindow().setExitTransition(new Slide().setDuration(10000));
//            getWindow().setEnterTransition(new Slide().setDuration(10000));
//            getWindow().setReenterTransition(new Slide().setDuration(10000));
//            getWindow().setReturnTransition(new Slide().setDuration(10000));


        }
//
//        Timber.v("Original SQL - "+sNubaMenuWithLike);
//        Timber.v("Formated SQL - "+addLocationToSql(sNubaMenuWithLike));
//        Timber.v("Original SQL - "+sNubaMenuWithGfFilter);
////
//        Timber.v("Formated SQL - "+addLocationToSql(sNubaMenuWithGfFilter));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings: {

            }
            case R.id.drop_db: {
                Utility.dropDB(this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utility.verifyStoragePermissions(this);
    }
}
