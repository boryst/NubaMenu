package ca.nuba.nubamenu;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;

import static ca.nuba.nubamenu.Utility.FILTER_GLUTEN_FREE;
import static ca.nuba.nubamenu.Utility.FILTER_VEGAN;
import static ca.nuba.nubamenu.Utility.FILTER_VEGETARIAN;
import static ca.nuba.nubamenu.Utility.LOCATION_EXTRA;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.TAB_NUMBER_EXTRA;
import static ca.nuba.nubamenu.Utility.TYPE_EXTRA;
import static ca.nuba.nubamenu.Utility.slideOutTransition;


public class MenuActivity extends AppCompatActivity {
    public static final String LOG_TAG = MenuActivity.class.getSimpleName();
    String mLocation, mType;
    int mPage, tabPosition;
    private static String F_MENU = "f_menu";
    private FragmentManager fm;

    AlertDialog.Builder alert;

    TabsAdapter mTabsAdapter;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getSupportActionBar();
        fm = getSupportFragmentManager();


        setContentView(R.layout.activity_menu);

        SharedPreferences prefs = this.getSharedPreferences(NUBA_PREFS, MODE_PRIVATE);
        String type = prefs.getString(TYPE_EXTRA, null);
        String location = prefs.getString(LOCATION_EXTRA, null);
        final int tabNumber = prefs.getInt(TAB_NUMBER_EXTRA, 0);


        mTabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mTabsAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0f);
            setTitle(location + " - " + type);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide(Gravity.RIGHT));
        }



/* Select tab if needed*/

        TabLayout.Tab tab = tabLayout.getTabAt(tabNumber);
        if (tab != null) {
            tab.select();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor;
        editor = getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
        editor.putString(FILTER_VEGETARIAN, null);
        editor.putString(FILTER_VEGAN, null);
        editor.putString(FILTER_GLUTEN_FREE, null);
        editor.apply();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        slideOutTransition(this);

    }
}
