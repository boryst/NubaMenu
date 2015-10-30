package ca.nuba.nubamenu;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog.Builder;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TabHost;

public class MenuActivity extends AppCompatActivity {

    String mLocation, mType;
    int mPage,tabPosition;

    AlertDialog.Builder alert;

    private static TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);






        Intent intent = this.getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            mLocation = extras.getString("EXTRA_LOCATION");
            mType = extras.getString("EXTRA_TYPE");
            mPage = extras.getInt("EXTRA_PAGE",1);
            setTitle(mLocation + " - " + mType);
            getSupportActionBar().setElevation(0f);
        }


   /**        */
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        //ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), MenuActivity.this));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), MenuActivity.this, mLocation, mType);
        viewPager.setAdapter(tabsAdapter);


        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabLayout.getTabAt(mPage-1);
        tab.select();



        alert = new AlertDialog.Builder(this);
        alert.setView(R.layout.filter);



        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                // Do something with value!
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.dismiss();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home: {
                //NavUtils.navigateUpFromSameTask(this);
                NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this).putExtra("EXTRA_LOCATION", mLocation));
                //Toast.makeText(this, "UpPressed-1", Toast.LENGTH_LONG).show();
                //Toast.makeText(this, mLocation, Toast.LENGTH_LONG).show();


                return true;
            }
            case R.id.action_filter:{
                alert.show();
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //tabHost.setCurrentTab(3);
    }




}
