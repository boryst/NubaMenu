package ca.nuba.nubamenu;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.TAB_NUMBER_EXTRA;
import static ca.nuba.nubamenu.Utility.slideOutTransition;

public class MenuSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu_select);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setEnterTransition(new Slide(Gravity.RIGHT));
//            getWindow().setExitTransition(new Slide(Gravity.LEFT));
        }
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:{

            }
            case android.R.id.home:{
                onBackPressed();
            }
            default: return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected void onResume() {
        SharedPreferences.Editor editor = this.getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
        editor.putInt(TAB_NUMBER_EXTRA, 0);
        editor.apply();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        slideOutTransition(this);
    }
}
