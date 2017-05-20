package ca.nuba.nubamenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ca.nuba.nubamenu.data.NubaContract;

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
        mCursor = context.getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                new String[]{"DISTINCT " + NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE},
                Utility.sNubaMenuWithLike,
                new String[]{type+"%"},
                null);
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
