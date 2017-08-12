package ca.nuba.nubamenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ca.nuba.nubamenu.data.NubaContract;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import static ca.nuba.nubamenu.Utility.ARG_PAGE;
import static ca.nuba.nubamenu.Utility.ARG_PAGE_NUMBER;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.TYPE_EXTRA;


public class TabsAdapter extends FragmentStatePagerAdapter {
    static final String LOG_TAG = TabsAdapter.class.getSimpleName();
    Context mContext;
    Cursor mCursor;
    String type;

    public TabsAdapter(FragmentManager fm){
        super(fm);
    }

    public TabsAdapter(FragmentManager fm, Context context){
        super(fm);
        mContext = context;
        SharedPreferences prefs = mContext.getSharedPreferences(NUBA_PREFS, MODE_PRIVATE);
        type = prefs.getString(TYPE_EXTRA, null);
        //Log.v(LOG_TAG, "type - " + type);
//        mCursor = context.getContentResolver().query(
//                NubaContract.NubaMenuEntry.CONTENT_URI,
//                new String[]{"DISTINCT " + NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE},
//                Utility.sNubaMenuWithLike,
//                new String[]{type+"%"},
//                null);
        if (type != null) {
            switch (type) {
                case "Lunch": {
                    Timber.v("MenuType - "+type);
                    mCursor = context.getContentResolver().query(
                            NubaContract.NubaMenuEntry.CONTENT_URI,
                            new String[]{"DISTINCT " + NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE},
                            Utility.sNubaMenuWithLike,
                            new String[]{type + "%"},
                            "CASE WHEN nuba_menu.menu_type = \"lunchMezze\" THEN 1 " +
                                    "WHEN nuba_menu.menu_type = \"lunchPlates\" THEN 2 " +
                                    "WHEN nuba_menu.menu_type = \"lunchPitas\" THEN 3 " +
                                    "WHEN nuba_menu.menu_type = \"lunchSalads\" THEN 4 " +
                                    "WHEN nuba_menu.menu_type = \"lunchSoups\" THEN 5 " +
                                    "WHEN nuba_menu.menu_type = \"lunchToShare\" THEN 6 " +
                                    "WHEN nuba_menu.menu_type = \"lunchBeverages\" THEN 7 " +
                                    "ELSE 8 END, nuba_menu.menu_type"
                    );
//                            String.valueOf(NubaContract.NubaMenuEntry.COLUMN_WEB_ID)


                break;
                }
                case "Dinner": {
                    Timber.v("MenuType - "+type);
                    mCursor = context.getContentResolver().query(
                            NubaContract.NubaMenuEntry.CONTENT_URI,
                            new String[]{"DISTINCT " + NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE},
                            Utility.sNubaMenuWithLike,
                            new String[]{type + "%"},
                            "CASE WHEN nuba_menu.menu_type = \"dinnerColdMezze\" THEN 1 " +
                                    "WHEN nuba_menu.menu_type = \"dinnerHotMezze\" THEN 2 " +
                                    "WHEN nuba_menu.menu_type = \"dinnerToShare\" THEN 3 " +
                                    "WHEN nuba_menu.menu_type = \"dinnerSoupsSalads\" THEN 4 " +
                                    "WHEN nuba_menu.menu_type = \"dinnerMains\" THEN 5 " +
                                    "ELSE 6 END, nuba_menu.menu_type");
                    break;
                }
                case "Brunch": {
                    Timber.v("MenuType - "+type);
                    mCursor = context.getContentResolver().query(
                            NubaContract.NubaMenuEntry.CONTENT_URI,
                            new String[]{"DISTINCT " + NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE},
                            Utility.sNubaMenuWithLike,
                            new String[]{type + "%"},
                            "CASE WHEN nuba_menu.menu_type = \"brunchAll\" THEN 1 " +
                                    "WHEN nuba_menu.menu_type = \"brunchBevs\" THEN 2 " +
                                    "ELSE 3 END, nuba_menu.menu_type");
                    break;

                }
                default: Timber.e("Wrong type");
            }
        }

//        if (type != null && type.equals("Lunch")) {
//
//        } else {
//            Timber.v("MenuType - "+type);
//            mCursor = context.getContentResolver().query(
//                NubaContract.NubaMenuEntry.CONTENT_URI,
//                new String[]{"DISTINCT " + NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE},
//                Utility.sNubaMenuWithLike,
//                new String[]{type+"%"},
//                null);
//        }

        //ORDER BY CASE WHEN nuba_menu.modifier = "feature" THEN 1 WHEN nuba_menu.modifier = "new" THEN 2 ELSE 3 END, nuba_menu.modifier
        while (mCursor != null && mCursor.moveToNext()){
            Timber.v("Tab title - "+mCursor.getString(0));
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new MenuActivityFragment();

        Bundle args = new Bundle();
        mCursor.moveToPosition(position);

        args.putString(ARG_PAGE, mCursor.getString(0));
        args.putInt(ARG_PAGE_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
        //return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return "OBJECT "+ (position +1);
        mCursor.moveToPosition(position);
        return Utility.formatMenuType(mCursor.getString(0));
    }

    public String getPageTitleUncut(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(0);
    }
}
