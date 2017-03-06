package ca.nuba.nubamenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {

    String mLocation, mType;
    int mPage,tabPosition;
    private static String F_MENU = "f_menu";

    AlertDialog.Builder alert;

    NewTabsAdapter mTabsAdapter;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ActionBar actionBar = getSupportActionBar();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences prefs = this.getSharedPreferences(MainActivityFragment.NUBA_PREFS, MODE_PRIVATE);
        String type = prefs.getString(MenuSelectActivityFragment.TYPE_EXTRA, null);
        String location = prefs.getString(MainActivityFragment.LOCATION_EXTRA, null);

        mTabsAdapter = new NewTabsAdapter(getSupportFragmentManager(), this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mTabsAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        if (actionBar !=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0f);
            setTitle(location+" - "+type);
        }

/**        Intent intent = this.getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            mLocation = extras.getString("EXTRA_LOCATION");
            mType = extras.getString("EXTRA_TYPE");
            mPage = extras.getInt("EXTRA_PAGE",1);
            setTitle(mLocation + " - " + mType);
            if (getSupportActionBar() != null){
                getSupportActionBar().setElevation(0f);
            }
        }




        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), MenuActivity.this, mLocation, mType);
        viewPager.setAdapter(tabsAdapter);


        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabLayout.getTabAt(mPage-1);
        tab.select();*/
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
}
