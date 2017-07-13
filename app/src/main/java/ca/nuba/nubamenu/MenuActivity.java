package ca.nuba.nubamenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import static ca.nuba.nubamenu.Utility.FILTER_GLUTEN_FREE;
import static ca.nuba.nubamenu.Utility.FILTER_VEGAN;
import static ca.nuba.nubamenu.Utility.FILTER_VEGETARIAN;
import static ca.nuba.nubamenu.Utility.LOCATION_EXTRA;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.TAB_NUMBER_EXTRA;
import static ca.nuba.nubamenu.Utility.TYPE_EXTRA;


public class MenuActivity extends AppCompatActivity {
    public static final String LOG_TAG = MenuActivity.class.getSimpleName();
    String mLocation, mType;
    int mPage,tabPosition;
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


/**        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.v(LOG_TAG,  "Tab number - "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
        //Fragment fr = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + mViewPager.getCurrentItem());
        //Log.v(LOG_TAG, "current page - "+mViewPager.getCurrentItem());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        if (actionBar !=null) {
            //actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0f);
            setTitle(location+" - "+type);
        }




/** Select tab if needed*/

   TabLayout.Tab tab = tabLayout.getTabAt(tabNumber);
        if (tab!=null) {
            tab.select();
        }
/*        String title =  String.valueOf(mTabsAdapter.getPageTitleUncut(tabLayout.getSelectedTabPosition() + 1));
        Log.v(LOG_TAG, "Page title - "+title);


        //Log.v(LOG_TAG, "Query - "+ Utility.sNubaMenuWithLike);
        Cursor mCursor = this.getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                new String[]{NubaContract.NubaMenuEntry.COLUMN_ICON_PATH},
                sNubaMenuWithLike,
                new String[]{title},
                null);

        if (mCursor != null) {
            Log.v(LOG_TAG, "Cursor size - "+mCursor.getCount());
            mCursor.moveToPosition(-1);

            while (mCursor.moveToNext()){
                //Log.v(LOG_TAG, "image "+mCursor.getPosition()+" - "+mCursor.getString(0));
                File img = new File(this.getFilesDir() + "/" + imageNameCutter(mCursor.getString(0)));
                if (!img.exists()) {
                    Log.v(LOG_TAG, "File http://boryst.com/"+mCursor.getString(0)+" does not exist");
                    Utility.imageDownload(this, "http://boryst.com/" + mCursor.getString(0), imageNameCutter(mCursor.getString(0)));
                }
            }
            mCursor.close();
        }*/


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
        switch (item.getItemId()){
            case android.R.id.home:{

            }
            default: return super.onOptionsItemSelected(item);
        }
    }
}
