package ca.nuba.nubamenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.main_activity_title));

        if (getSupportActionBar() != null){
            getSupportActionBar().setElevation(0f);
        }
        //getSupportActionBar().setIcon(R.drawable.nuba_logo);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
/*        Cursor mCursor = this.getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                new String[]{"DISTINCT " + NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE},
                Utility.sNubaMenuWithLike,
                new String[]{type+"%"},
                null);

        if (mCursor != null) {
            mCursor.moveToPosition(2);
            Log.v(LOG_TAG, "type - "+type);
            Log.v(LOG_TAG, "Cursor - " + mCursor.getString(0));
        }*/
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
}
