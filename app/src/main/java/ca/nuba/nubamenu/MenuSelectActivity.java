package ca.nuba.nubamenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.TAB_NUMBER_EXTRA;

public class MenuSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu_select);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        SharedPreferences.Editor editor = this.getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
        editor.putInt(TAB_NUMBER_EXTRA, 0);
        editor.apply();
        super.onResume();


    }
}
