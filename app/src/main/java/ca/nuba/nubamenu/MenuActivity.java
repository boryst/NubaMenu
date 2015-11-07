package ca.nuba.nubamenu;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.LinearLayout;
import android.widget.TabHost;

import bolts.Bolts;


public class MenuActivity extends AppCompatActivity {

    String mLocation, mType;
    int mPage,tabPosition;
    private static String F_MENU = "f_menu";

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




        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), MenuActivity.this, mLocation, mType);
        viewPager.setAdapter(tabsAdapter);


        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabLayout.getTabAt(mPage-1);
        tab.select();
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home: {
//                //NavUtils.navigateUpFromSameTask(this);
//                NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this).putExtra("EXTRA_LOCATION", mLocation));
//                //Toast.makeText(this, "UpPressed-1", Toast.LENGTH_LONG).show();
//                //Toast.makeText(this, mLocation, Toast.LENGTH_LONG).show();
//                return true;
//            }
//            case R.id.action_filter:{
//                //filter();
//                return true;
//
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

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


//    public void filter(){
//        alert = new AlertDialog.Builder(this);
//        //alert.setView(R.layout.filter);
//        final View container = getLayoutInflater().inflate(R.layout.filter, null);
//
//        alert.setView(container);
//
//
//        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                CheckBox vCheckBox = (CheckBox) container.findViewById(R.id.filterVCheckBox);
//                CheckBox veCheckBox = (CheckBox) container.findViewById(R.id.filterVeCheckBox);
//                CheckBox gfCheckBox = (CheckBox) container.findViewById(R.id.filterGfCheckBox);
//                CheckBox mCheckBox = (CheckBox) container.findViewById(R.id.filterMCheckBox);
//
//                Boolean v = false;
//                Boolean ve = false;
//                Boolean gf = false;
//                Boolean m = false;
//                if (vCheckBox.isChecked()) {
//                    v = true;
//                }
//                if (veCheckBox.isChecked()) {
//                    ve = true;
//                }
//                if (gfCheckBox.isChecked()) {
//                    gf = true;
//                }
//                if (mCheckBox.isChecked()) {
//                    m = true;
//                }
//
//
////                FragmentManager fm = getSupportFragmentManager();
////                MenuActivityFragment fragment = (MenuActivityFragment)fm.findFragmentByTag(F_MENU);
////                fragment.menuFilter(v,ve,gf,m);
//
//                dialog.dismiss();
//            }
//        });
//
//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                dialog.dismiss();
//            }
//        });
//
//        alert.show();
//    }

}
