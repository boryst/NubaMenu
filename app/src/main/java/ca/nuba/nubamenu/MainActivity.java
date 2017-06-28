package ca.nuba.nubamenu;

import android.content.SharedPreferences;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Picasso.with(this).setIndicatorsEnabled(true);
        Timber.plant(new Timber.DebugTree());

        setTitle(getString(R.string.main_activity_title));

        if (getSupportActionBar() != null){
            getSupportActionBar().setElevation(0f);
        }
        //getSupportActionBar().setIcon(R.drawable.nuba_logo);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
